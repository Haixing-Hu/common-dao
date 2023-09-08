////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;

import javax.annotation.Nullable;

import jakarta.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.mapper.ListableMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.error.DataUpdateFailException;
import ltd.qubit.commons.error.DuplicateKeyException;
import ltd.qubit.commons.error.FieldTooLongException;
import ltd.qubit.commons.error.FieldValueOutOfRangeException;
import ltd.qubit.commons.error.ForeignKeyConstraintFailException;
import ltd.qubit.commons.error.InvalidFieldFormatException;
import ltd.qubit.commons.error.NullFieldException;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.model.Creatable;
import ltd.qubit.commons.model.Deletable;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.Modifiable;
import ltd.qubit.commons.model.Normalizable;
import ltd.qubit.commons.model.Owner;
import ltd.qubit.commons.model.WithCode;
import ltd.qubit.commons.model.WithCredential;
import ltd.qubit.commons.model.WithEntity;
import ltd.qubit.commons.model.WithName;
import ltd.qubit.commons.model.WithOwner;
import ltd.qubit.commons.reflect.FieldUtils;
import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.commons.util.pair.KeyValuePair;
import ltd.qubit.model.commons.CredentialInfo;
import ltd.qubit.model.commons.CredentialInfoCodec;

import static ltd.qubit.commons.lang.StringUtils.join;

/**
 * 提供实现DAO功能的工具函数。
 *
 * <p><b>注意：</b>实现所有SELECT相关的方法时（get, list, listFirst等），对于SELECT
 * 查询得到的结果对象，必须clone一份再返回给调用者。这是因为MyBatis的cache机制存在实现上的
 * bug：虽然文档说，默认的cache是read/write的，即cache返回的对象可以被外部修改；但实际上
 * 发现直到 MyBatis v3.5.7 为止，其 cache 返回的对象始终是同一个对象的引用。换句话说，如果
 * 外部调用程序修改了 MyBatis 通过 SELECT 查询出来的对象的属性，MyBatis 的内部 cache 中
 * 同一个对象的属性也会被修改（因为其cache始终返回的是同一个对象的引用）。这就会造成一系列的奇怪
 * bug。</p>
 *
 * <p>一个具体的复现例子如下：</p>
 * <pre><code>
 *   UserDao dao = ....
 *   Long id = ...
 *   User user1 = dao.get(id);
 *   user1.setUsername("xxx");
 *   User user2 = dao.get(id);
 *   assertEquals("xxx", user2.getUsername());
 *   // 注意：user2是MyBatis从cache中取出的，但其username却被外部修改了
 * </code></pre>
 *
 * <p>此问题目前没有好的解决办法，除非全局禁用MyBatis的cache，但这样会极大影响性能。</p>
 *
 * <p>目前我们的解决方案是，在 DAO 层，对于所有通过SELECT语句查询出来的对象，将其 clone
 * 一份后再做处理和返回。</p>
 *
 * <p>注意，因为某种未知的原因，没办法通过类型参数限制实体类型{@code T}必须扩展
 * {@code CloneableEx<T>}，编译器会报错：</p>
 * <pre>
 * java.lang.Object中的clone()无法实现ltd.qubit.commons.lang.CloneableEx中的clone()
 *   正在尝试分配更低的访问权限; 以前为public
 * </pre>
 *
 * <p>所以目前只能在代码中做强制类型转换。如果实体忘记实现{@code Cloneable}接口，应该会抛出
 * 运行时错误。</p>
 *
 * @author 胡海星
 */
public class DaoImplHelper {

  /**
   * Correct the return result of the SELECT query and desensitize the data as
   * necessary.
   * <p>
   * <b>Note: </b>When implementing all SELECT-related methods (get, list,
   * listFirst, etc.), the result obtained by the SELECT query must be cloned
   * and returned to the caller. Because there is an implementation bug in the
   * cache mechanism of MyBatis: Although the document says that the default
   * cache is read/write, that is, the object returned by the cache can be
   * modified externally; but it is actually found that until MyBatis v3.5.7,
   * its cache returns The object is always a reference to the same object.
   * In other words, if the external caller modifies the properties of the
   * object queried by MyBatis through SELECT, the properties of the same object
   * in the internal cache of MyBatis will also be modified (because the cache
   * always returns a reference to the same object). This will cause a series
   * of strange bugs.
   * <p>
   * A specific recurring example is as follows:
   * <pre><code>
   *   UserMapper mapper = ....
   *   Long id = ...
   *   User user1 = mapper.get(id);
   *   user1.setUsername("xxx");
   *   User user2 = mapper.get(id);
   *   assertEquals("xxx", user2.getUsername());
   *   // Note: user2 is taken out from the cache by MyBatis, but its username
   *   //       has been modified externally.
   * </code></pre>
   * <p>
   * There is currently no good solution to this problem, unless the MyBatis
   * cache is disabled globally, which will greatly affect performance.
   * <p>
   * Currently, our solution is: At the DAO layer, all objects queried through
   * the SELECT statement must be cloned before processing and returning.
   * <p>
   * Note that for some UNKNOWN reason, there is no way to restrict entity types
   * via type parameters {@code T} must extend {@code CloneableEx<T> }, the
   * compiler will report an error:
   * <pre>
   * clone() in java.lang.Object does not implement clone() in
   * ltd.qubit.commons.lang.CloneableEx. Attempting to assign lower access
   * rights; formerly public.
   * </pre>
   * <p>
   * So at present, only mandatory type conversion can be done in the code. If
   * an entity forgets to implement the {@code CloneableEx} interface, a runtime
   * error will be thrown.
   *
   * @param <T>
   *     The type of entities, which must implements the {@link CloneableEx}
   *     interface.
   * @param obj
   *     The return result of a SELECT query.
   * @return
   *     The corrected return result.
   */
  @SuppressWarnings("unchecked")
  public static <T> T fixSelectedResult(@Nullable final T obj) {
    if (obj == null) {
      return null;
    } else {
      // clone the entity
      final T result = (obj instanceof CloneableEx
                        ? ((CloneableEx<T>) obj).clone()
                        : obj);
      // normalize the entity if necessary
      if (result instanceof Normalizable) {
        ((Normalizable) result).normalize();
      }
      return result;
    }
  }

  /**
   * Correct the return result of the SELECT query and desensitize the data as
   * necessary.
   * <p>
   * <b>Note: </b>When implementing all SELECT-related methods (get, list,
   * listFirst, etc.), the result obtained by the SELECT query must be cloned
   * and returned to the caller. Because there is an implementation bug in the
   * cache mechanism of MyBatis: Although the document says that the default
   * cache is read/write, that is, the object returned by the cache can be
   * modified externally; but it is actually found that until MyBatis v3.5.7,
   * its cache returns The object is always a reference to the same object.
   * In other words, if the external caller modifies the properties of the
   * object queried by MyBatis through SELECT, the properties of the same object
   * in the internal cache of MyBatis will also be modified (because the cache
   * always returns a reference to the same object). This will cause a series
   * of strange bugs.
   * <p>
   * A specific recurring example is as follows:
   * <pre><code>
   *   UserMapper mapper = ....
   *   Long id = ...
   *   User user1 = mapper.get(id);
   *   user1.setUsername("xxx");
   *   User user2 = mapper.get(id);
   *   assertEquals("xxx", user2.getUsername());
   *   // Note: user2 is taken out from the cache by MyBatis, but its username
   *   //       has been modified externally.
   * </code></pre>
   * <p>
   * There is currently no good solution to this problem, unless the MyBatis
   * cache is disabled globally, which will greatly affect performance.
   * <p>
   * Currently, our solution is: At the DAO layer, all objects queried through
   * the SELECT statement must be cloned before processing and returning.
   * <p>
   * Note that for some UNKNOWN reason, there is no way to restrict entity types
   * via type parameters {@code T} must extend {@code CloneableEx<T> }, the
   * compiler will report an error:
   * <pre>
   * clone() in java.lang.Object does not implement clone() in
   * ltd.qubit.commons.lang.CloneableEx. Attempting to assign lower access
   * rights; formerly public.
   * </pre>
   * <p>
   * So at present, only mandatory type conversion can be done in the code. If
   * an entity forgets to implement the {@code CloneableEx} interface, a runtime
   * error will be thrown.
   *
   * @param <T>
   *     The type of entities, which must implements the {@link CloneableEx}
   *     interface.
   * @param list
   *     The return list of results of a SELECT query.
   * @return
   *     The corrected list of results.
   */
  @NotNull
  public static <T> List<T> fixSelectedResultList(@Nullable final List<T> list) {
    if (list == null || list.isEmpty()) {
      return Collections.emptyList();
    }
    final List<T> result = new ArrayList<>();
    for (final T obj : list) {
      final T fixed = fixSelectedResult(obj);
      result.add(fixed);
    }
    return result;
  }

  /**
   * Get the number of entities that match the specified criteria.
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param filter
   *     The criteria used to filter entities. A {@code null} value indicates
   *     no restriction.
   * @return
   *     The number of all entities matching the filtering criteria.
   * @throws DataAccessException
   *     If any data access error occurs.
   */
  public static <T> long countImpl(final ListableDao<T> dao,
      @Nullable final Criterion<T> filter) throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Count {}: filter = {}", dao.getEntityName(), filter);
    // FIXME: Should check the validity of the filter
    return dao.getMapper().count(filter);
  }

  /**
   * Lists the specified subsequence of entities that match the specified
   * criteria.
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param filter
   *     The criteria used to filter entities. A {@code null} value indicates
   *     no restriction.
   * @param sortRequest
   *     Specify the sorting field and sorting method. If it is {@code null},
   *     the default sorting will be used.
   * @param limit
   *     Specifies the maximum length of the subsequence to be returned. A
   *     {@code null} value indicates no limit.
   * @param offset
   *     Specifies the index (starting from 0) of the first element of the
   *     subsequence to be returned in the sequence of all eligible entities.
   *     A {@code null} value indicates the default offset 0.
   * @return
   *     The specified subsequence of eligible entities, sorted by the
   *     specified sorting order. If no entity meets the criteria, an empty
   *     list is returned.
   * @throws DataAccessException
   *     If any data access error occurs.
   */
  @NotNull
  public static <T> List<T> listImpl(final ListableDao<T> dao,
      @Nullable final Criterion<T> filter,
      @Nullable final SortRequest<T> sortRequest, @Nullable final Integer limit,
      @Nullable final Long offset) throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("List {}: filter = {}, sortRequest = {}, limit = {}, offset = {}",
        dao.getEntityName(), filter, sortRequest, limit, offset);
    // FIXME: Should check the validity of the filter
    final ListableMapper<T> mapper = dao.getMapper();
    // Note: The result of select must be cloned, because the cache mechanism of
    //  MyBatis will always return the same object
    final List<T> r = mapper.list(filter, sortRequest, limit, offset);
    final List<T> result = fixSelectedResultList(r);
    logger.debug("List {}: filter = {}, sortRequest = {}, limit = {}, offset = {}, "
        + "result = {}", dao.getEntityName(), filter, sortRequest, limit,
        offset, result);
    return result;
  }

  /**
   * Tests whether the specified entity exists.
   *
   * @param <T>
   *     The type of the entity.
   * @param dao
   *     The DAO object.
   * @param tester
   *     A functor used to test the specified entity, usually a lambda expression.
   * @param keyName
   *     The name of the key used to determine the specified entity.
   * @param keyValue
   *     The value of the key used to determine the specified entity.
   * @param otherKeys
   *     Additional key-value pairs used to determine the specified entity.
   * @return
   *     Returns {@code true} if the specified entity exists; {@code false}
   *     otherwise.
   * @throws DataAccessException
   *     If any data access error occurs.
   */
  public static <T> boolean existKeyImpl(final Dao<T> dao,
      final BooleanSupplier tester, final String keyName, final Object keyValue,
      final KeyValuePair ... otherKeys) throws DataAccessException {
    final Logger logger = dao.getLogger();
    final String args = (otherKeys.length == 0 ? "" : ", " + join(", ", otherKeys));
    logger.debug("Test the existence of {} by its {}: {} = {}{}",
        dao.getEntityName(), keyName, keyName, keyValue, args);
    final boolean result = tester.getAsBoolean();
    logger.debug("Test the existence of {} by its {}: {} = {}{}, result = {}",
        dao.getEntityName(), keyName, keyName, keyValue, args, result);
    return result;
  }

  /**
   * Tests whether there exists a specified entity that has not been marked as
   * deleted.
   *
   * @param <T>
   *     The type of entity.
   * @param dao
   *     The DAO object.
   * @param tester
   *     A functor used to test the specified entity, usually a lambda expression.
   * @param keyName
   *     The name of the key used to determine the specified entity.
   * @param keyValue
   *     The value of the key used to determine the specified entity.
   * @param otherKeys
   *     Additional key-value pairs used to determine the specified entity.
   * @return
   *     Returns {@code true} if there exists the entity with the specified ID
   *     that has not been marked as deleted; returns {@code false} otherwise.
   * @throws DataAccessException
   *     If other data access errors occur.
   */
  public static <T extends Deletable> boolean existNonDeletedKeyImpl(
      final Dao<T> dao, final BooleanSupplier tester, final String keyName,
      final Object keyValue, final KeyValuePair ... otherKeys)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    final String args = (otherKeys.length == 0 ? "" : ", " + join(", ", otherKeys));
    logger.debug("Test the existence of non-deleted {} by its {}: {} = {}{}",
        dao.getEntityName(), keyName, keyName, keyValue, args);
    final boolean result = tester.getAsBoolean();
    logger.debug("Test the existence of non-deleted {} by its {}: {} = {}{}, result = {}",
        dao.getEntityName(), keyName, keyName, keyValue, args, result);
    return result;
  }

  /**
   * 获取指定所有者所属的所有实体对象的数目。
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param owner
   *     指定实体对象的所有者。
   * @return
   *     指定所有者所属的所有实体对象的数目。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  public static <T extends Identifiable & WithOwner> long countForOwnerImpl(
      final GettableForOwnerDao<T> dao, final Owner owner)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Get the total count of {} for the owner: {}",
        dao.getEntityName(), owner);
    // FIXME: 检查filter的合法性
    return dao.getMapper().countForOwner(owner);
  }

  /**
   * Get the specified entity.
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param getter
   *     A functor that specifically performs an acquisition operation, usually
   *     implemented as a lambda expression.
   * @param keyName
   *     The name of the key used to determine the specified entity.
   * @param keyValue
   *     The value of the key used to determine the specified entity.
   * @param otherKeys
   *     Additional key-value pairs used to determine the specified entity.
   * @return
   *     The specified entity.
   * @throws DataNotExistException
   *     If the specified entity does not exist.
   * @throws DataAccessException
   *     If other data access errors occur.
   */
  public static <T> T getByKeyImpl(final Dao<T> dao, final Supplier<T> getter,
      final String keyName, final Object keyValue, final KeyValuePair ... otherKeys)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    final String args = (otherKeys.length == 0 ? "" : ", " + join(", ", otherKeys));
    logger.debug("Get {} by {}: {} = {}{}", dao.getEntityName(), keyName,
        keyName, keyValue, args);
    // FIXME: The result of select must be cloned, because the cache mechanism of
    //  MyBatis will always return the same object
    final T result = fixSelectedResult(getter.get());
    if (result == null) {
      throw new DataNotExistException(dao.getEntityClass(), keyName, keyValue, otherKeys);
    }
    logger.debug("Get {} by {}: {} = {}{}, result = {}", dao.getEntityName(),
        keyName, keyName, keyValue, args, result);
    return result;
  }

  /**
   * Get the specified entity.
   *
   * @param <T>
   *     The type of entity to fetch.
   * @param dao
   *     The DAO object.
   * @param getter
   *     A functor that specifically performs an acquisition operation, usually
   *     implemented as a lambda expression.
   * @param keyName
   *     The name of the key used to determine the specified entity.
   * @param keyValue
   *     The value of the key used to determine the specified entity.
   * @param otherKeys
   *     Additional key-value pairs used to determine the specified entity.
   * @return
   *     The specified entity, or {@code null} if it does not exist.
   * @throws DataAccessException
   *     If other data access errors occur.
   */
  public static <T> T getByKeyOrNullImpl(final Dao<T> dao, final Supplier<T> getter,
      final String keyName, final Object keyValue, final KeyValuePair ... otherKeys)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    final String args = (otherKeys.length == 0 ? "" : ", " + join(", ", otherKeys));
    logger.debug("Get {} by {}: {} = {}{}", dao.getEntityName(), keyName,
        keyName, keyValue, args);
    // FIXME: The result of select must be cloned, because the cache mechanism of
    //  MyBatis will always return the same object
    final T obj = fixSelectedResult(getter.get());
    logger.debug("Get {} by {}: {} = {}{}, result = {}", dao.getEntityName(),
        keyName, keyName, keyValue, args, obj);
    return obj;
  }

  /**
   * 获取指定的对象的指定属性。
   *
   * @param <T>
   *     The type of entities.
   * @param <P>
   *     待获取的实体的指定属性的类型。
   * @param dao
   *     The DAO object.
   * @param getter
   *     具体进行获取操作的函子，通常用lambda表达式实现。
   * @param propertyName
   *     待获取的指定属性的名称。
   * @param keyName
   *     用于确定待获取实体对象的首要主键的名称。
   * @param keyValue
   *     用于确定待获取实体对象的首要主键值。
   * @param otherKeys
   *     用于确定待获取实体对象的其他键名和键值对。
   * @return
   *     指定的对象。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  public static <T, P> P getPropertyByKeyImpl(final Dao<T> dao,
      final Supplier<P> getter, final String propertyName, final String keyName,
      final Object keyValue, final KeyValuePair ... otherKeys)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    final String args = (otherKeys.length == 0 ? "" : ", " + join(", ", otherKeys));
    logger.debug("Get the {} of {} by {}: {} = {}{}", propertyName,
        dao.getEntityName(), keyName, keyName, keyValue, args);
    // 注意：必须把select的结果clone一份，因为MyBatis的cache机制会始终返回同一个对象
    final P result = fixSelectedResult(getter.get());
    if (result == null) {
      throw new DataNotExistException(dao.getEntityClass(), keyName, keyValue, otherKeys);
    }
    logger.debug("Get the {} of {} by {}: {} = {}{}, result = {}", propertyName,
        dao.getEntityName(), keyName, keyName, keyValue, args, result);
    return result;
  }

  /**
   * 获取指定的对象的指定属性。
   *
   * @param <T>
   *     The type of entities.
   * @param <P>
   *     待获取的实体的指定属性的类型。
   * @param dao
   *     The DAO object.
   * @param getter
   *     具体进行获取操作的函子，通常用lambda表达式实现。
   * @param propertyName
   *     待获取的指定属性的名称。
   * @param keyName
   *     用于确定待获取实体对象的首要主键的名称。
   * @param keyValue
   *     用于确定待获取实体对象的首要主键值。
   * @param otherKeys
   *     用于确定待获取实体对象的其他键名和键值对。
   * @return
   *     指定的对象。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  public static <T, P> P getPropertyByKeyOrNullImpl(final Dao<T> dao,
      final Supplier<P> getter, final String propertyName, final String keyName,
      final Object keyValue, final KeyValuePair ... otherKeys)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    final String args = (otherKeys.length == 0 ? "" : ", " + join(", ", otherKeys));
    logger.debug("Get the {} of {} by {} or returns null: {} = {}{}", propertyName,
        dao.getEntityName(), keyName, keyName, keyValue, args);
    // 注意：必须把select的结果clone一份，因为MyBatis的cache机制会始终返回同一个对象
    final P result = fixSelectedResult(getter.get());
    logger.debug("Get the {} of {} by {} or returns null: {} = {}{}, result = {}",
        propertyName, dao.getEntityName(), keyName, keyName, keyValue, args, result);
    return result;
  }

  /**
   * 根据实体类和名称获取指定的对象。
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param owner
   *     指定实体对象的所有者。
   * @return
   *     指定所有者所属的所有实体对象的列表。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  public static <T extends Identifiable & WithOwner> List<T> getForOwnerImpl(
      final GettableForOwnerDao<T> dao, final Owner owner)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Get all {} for the owner: owner = {}",
        dao.getEntityName(), owner);
    // 注意：必须把select的结果clone一份，因为MyBatis的cache机制会始终返回同一个对象
    final List<T> result = fixSelectedResultList(dao.getMapper().getForOwner(owner));
    logger.debug("Get all {} for the owner: owner = {}, result = {}",
        dao.getEntityName(), owner, result);
    return result;
  }

  /**
   * 根据实体类和名称获取指定的对象的ID。
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param owner
   *     指定实体对象的所有者。
   * @return
   *     指定所有者所属的所有实体对象的ID列表。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  public static <T extends Identifiable & WithOwner> List<Long> getIdForOwnerImpl(
      final GettableForOwnerDao<T> dao, final Owner owner)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Get all IDs of {} for the owner: owner = {}",
        dao.getEntityName(), owner);
    final List<Long> result = dao.getMapper().getIdForOwner(owner);
    logger.debug("Get all IDs of {} for the owner: owner = {}, result = {}",
        dao.getEntityName(), owner, result);
    return result;
  }

  /**
   * Add a new entity.
   *
   * @param <T>
   *     The type of entity.
   * @param dao
   *     The DAO object.
   * @param obj
   *     The entity to be added.
   * @return
   *     The timestamp when the data was added.
   * @throws NullFieldException
   *     If the database field corresponding to an attribute of the entity
   *     should not be empty but its attribute value is empty.
   * @throws InvalidFieldFormatException
   *     If an attribute value of the entity does not conform to the format
   *     required by its corresponding database field.
   * @throws FieldTooLongException
   *     If the length of an attribute value of the entity exceeds the length
   *     allowed by its corresponding database field.
   * @throws FieldValueOutOfRangeException
   *     If the range of an attribute value of the entity exceeds the range
   *     allowed by its corresponding database field.
   * @throws DuplicateKeyException
   *     If the database field corresponding to an attribute value of the entity
   *     is required to be unique, but the attribute value is the same as an
   *     existing object in the database.
   * @throws ForeignKeyConstraintFailException
   *     If the database field corresponding to an attribute is associated with
   *     an associated field of another associated table, but the attribute
   *     value does not exist in the associated field of the associated table.
   * @throws DataAccessException
   *     If other uncategorizable database operation errors occur.
   */
  public static <T> Instant addImpl(final AddableDao<T> dao, final T obj)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    final Instant createTime = dao.now();
    if (obj instanceof Identifiable) {
      final long id = dao.generateId();
      logger.debug("Generate a ID for {}: {}", dao.getEntityName(), id);
      ((Identifiable) obj).setId(id);
    }
    if (obj instanceof Creatable) {
      ((Creatable) obj).setCreateTime(createTime);
    }
    if (obj instanceof Modifiable) {
      ((Modifiable) obj).setModifyTime(null);
    }
    if (obj instanceof Deletable) {
      ((Deletable) obj).setDeleteTime(null);
    }
    if (obj instanceof Normalizable) {
      ((Normalizable) obj).normalize();
    }
    logger.debug("Add {}: {}", dao.getEntityName(), obj);
    final long count = dao.getMapper().add(obj);
    if (count == 0) {
      throw new DataUpdateFailException(dao.getEntityClass());
    }
    return createTime;
  }

  /**
   * 批量添加新的实体。
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param objects
   *     待添加的实体对象列表。
   * @return
   *     数据被被添加时的时间戳。
   * @throws NullFieldException
   *     如果该实体的某个属性对应的字段非空但其属性值为空。
   * @throws InvalidFieldFormatException
   *     如果该实体的某个属性值不符合其对应的字段所需的格式。
   * @throws FieldTooLongException
   *     如果该实体的某个属性值长度超过了其对应字段允许的长度。
   * @throws FieldValueOutOfRangeException
   *     如果该实体的某个属性值的范围超出了其对应字段允许的范围。
   * @throws DuplicateKeyException
   *     如果该实体的某个属性值对应的字段要求唯一，但该属性值和数据库中已存在的对象重复。
   * @throws ForeignKeyConstraintFailException
   *     如果某个属性对应的字段关联了另一张关联表的某个关联字段，而该属性值在关联表的关联字段
   *     中不存在。
   * @throws DataAccessException
   *     如果发生其他无法归类的数据库操作错误。
   */
  public static <T> Instant batchAddImpl(final AddableDao<T> dao, final List<T> objects)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    final Instant createTime = dao.now();
    for (final T obj : objects) {
      if (obj instanceof Identifiable) {
        final long id = dao.generateId();
        logger.debug("Generate a ID for {}: {}", dao.getEntityName(), id);
        ((Identifiable) obj).setId(id);
      }
      if (obj instanceof Creatable) {
        ((Creatable) obj).setCreateTime(createTime);
      }
      if (obj instanceof Modifiable) {
        ((Modifiable) obj).setModifyTime(null);
      }
      if (obj instanceof Deletable) {
        ((Deletable) obj).setDeleteTime(null);
      }
      if (obj instanceof Normalizable) {
        ((Normalizable) obj).normalize();
      }
      logger.debug("Add {}: {}", dao.getEntityName(), obj);
      final long count = dao.getMapper().add(obj);
      if (count == 0) {
        throw new DataUpdateFailException(dao.getEntityClass());
      }
    }
    return createTime;
  }

  /**
   * 增加一个新的实体或根据名称更新已有的实体。
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param obj
   *     待增加或更新的实体，更新时使用其<b>名称</b>确定该对象；若该对象不存在，则添加一个新对
   *     象；否则用此参数更新存在的对象。
   * @return 数据被创建或被修改时的时间戳。
   * @throws NullFieldException
   *     若新增或更新的对象的任意必须字段为空。
   * @throws InvalidFieldFormatException
   *     若新增或更新的对象的某个字段格式不正确。
   * @throws FieldTooLongException
   *     若新增或更新的对象的某个字段值的长度超过了数据库表中该字段允许的长度。
   * @throws FieldValueOutOfRangeException
   *     若新增或更新的对象的某个字段值超过了数据库表中该字段允许的取值范围。
   * @throws DuplicateKeyException
   *     若新增或更新的对象的某个字段的取值与数据库中已有数据该字段的取值相同，且该字段的取值不
   *     可重复。
   * @throws ForeignKeyConstraintFailException
   *     若新增或更新的对象的某个字段值应该是对另一张表的某个字段的引用，但所引用的对象却不存在。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   */
  public static <T extends WithName> Instant addOrUpdateByNameImpl(
      final AddOrUpdatableByNameDao<T> dao, final T obj)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Add or update {} by its name: {}", dao.getEntityName(), obj);
    final boolean exist = dao.getMapper().existName(obj.getName());
    if (!exist) {
      return dao.add(obj);
    } else {
      return dao.updateByName(obj);
    }
  }

  /**
   * 增加一个新的实体或根据名称更新已有的实体。
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param obj
   *     待增加或更新的实体，更新时使用其所属父对象编码和其<b>名称</b>确定该对象；若该对象不
   *     存在，则添加一个新对象；否则用此参数更新存在的对象。
   * @param parentGetter
   *     待增加或更新的实体所属的父对象的getter。
   * @return 数据被创建或被修改时的时间戳。
   * @throws NullFieldException
   *     若新增或更新的对象的任意必须字段为空。
   * @throws InvalidFieldFormatException
   *     若新增或更新的对象的某个字段格式不正确。
   * @throws FieldTooLongException
   *     若新增或更新的对象的某个字段值的长度超过了数据库表中该字段允许的长度。
   * @throws FieldValueOutOfRangeException
   *     若新增或更新的对象的某个字段值超过了数据库表中该字段允许的取值范围。
   * @throws DuplicateKeyException
   *     若新增或更新的对象的某个字段的取值与数据库中已有数据该字段的取值相同，且该字段的取值不
   *     可重复。
   * @throws ForeignKeyConstraintFailException
   *     若新增或更新的对象的某个字段值应该是对另一张表的某个字段的引用，但所引用的对象却不存在。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   */
  public static <T extends WithName, P extends WithCode> Instant addOrUpdateByParentNameImpl(
      final AddOrUpdatableByParentNameDao<T> dao, final T obj,
      final GetterMethod<T, P> parentGetter) throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Add or update {} by its parent and its parent code and its name: {}",
        dao.getEntityName(), obj);
    final Class<T> entityClass = dao.getEntityClass();
    final String parentFieldName = FieldUtils.getFieldName(entityClass, parentGetter);
    final P parent = parentGetter.invoke(obj);
    if (parent == null) {
      throw new NullFieldException(parentFieldName + "Id");
    }
    final boolean exist = dao.getMapper().existName(parent.getCode(), obj.getName());
    if (!exist) {
      return dao.add(obj);
    } else {
      return dao.updateByName(obj);
    }
  }

  /**
   * 增加一个新的实体或根据名称更新已有的实体。
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param obj
   *     待增加或更新的实体，更新时使用其<b>名称</b>确定该对象；若该对象不存在，则添加一个新对
   *     象；否则用此参数更新存在的对象。
   * @return 数据被创建或被修改时的时间戳。
   * @throws NullFieldException
   *     若新增或更新的对象的任意必须字段为空。
   * @throws InvalidFieldFormatException
   *     若新增或更新的对象的某个字段格式不正确。
   * @throws FieldTooLongException
   *     若新增或更新的对象的某个字段值的长度超过了数据库表中该字段允许的长度。
   * @throws FieldValueOutOfRangeException
   *     若新增或更新的对象的某个字段值超过了数据库表中该字段允许的取值范围。
   * @throws DuplicateKeyException
   *     若新增或更新的对象的某个字段的取值与数据库中已有数据该字段的取值相同，且该字段的取值不
   *     可重复。
   * @throws ForeignKeyConstraintFailException
   *     若新增或更新的对象的某个字段值应该是对另一张表的某个字段的引用，但所引用的对象却不存在。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   */
  public static <T extends Identifiable & WithEntity & WithName>
      Instant addOrUpdateByEntityNameImpl(final AddOrUpdatableByEntityNameDao<T> dao,
      final T obj) throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Add or update {} by its entity and name: {}",
        dao.getEntityName(), obj);
    final boolean exist = dao.getMapper().existName(obj.getEntity(), obj.getName());
    if (!exist) {
      return dao.add(obj);
    } else {
      return dao.updateByName(obj);
    }
  }

  /**
   * 增加一个新的实体或根据编码更新已有的实体。
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param obj
   *     待增加或更新的实体，更新时使用其<b>编码</b>确定该对象；若该对象不存在，则添加一个新对
   *     象；否则用此参数更新存在的对象。
   * @return 数据被创建或被修改时的时间戳。
   * @throws NullFieldException
   *     若新增或更新的对象的任意必须字段为空。
   * @throws InvalidFieldFormatException
   *     若新增或更新的对象的某个字段格式不正确。
   * @throws FieldTooLongException
   *     若新增或更新的对象的某个字段值的长度超过了数据库表中该字段允许的长度。
   * @throws FieldValueOutOfRangeException
   *     若新增或更新的对象的某个字段值超过了数据库表中该字段允许的取值范围。
   * @throws DuplicateKeyException
   *     若新增或更新的对象的某个字段的取值与数据库中已有数据该字段的取值相同，且该字段的取值不
   *     可重复。
   * @throws ForeignKeyConstraintFailException
   *     若新增或更新的对象的某个字段值应该是对另一张表的某个字段的引用，但所引用的对象却不存在。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   */
  public static <T extends WithCode> Instant addOrUpdateByCodeImpl(
      final AddOrUpdatableByCodeDao<T> dao, final T obj) throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Add or update {} by its code: {}", dao.getEntityName(), obj);
    final boolean exist = dao.getMapper().existCode(obj.getCode());
    if (!exist) {
      return dao.add(obj);
    } else {
      return dao.updateByCode(obj);
    }
  }

  /**
   * 增加一个新的实体或根据编码更新已有的实体。
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param obj
   *     待增加或更新的实体，更新时使用其所属父对象的ID和其<b>编码</b>确定该对象；若该对象不
   *     存在，则添加一个新对象；否则用此参数更新存在的对象。
   * @return 数据被创建或被修改时的时间戳。
   * @throws NullFieldException
   *     若新增或更新的对象的任意必须字段为空。
   * @throws InvalidFieldFormatException
   *     若新增或更新的对象的某个字段格式不正确。
   * @throws FieldTooLongException
   *     若新增或更新的对象的某个字段值的长度超过了数据库表中该字段允许的长度。
   * @throws FieldValueOutOfRangeException
   *     若新增或更新的对象的某个字段值超过了数据库表中该字段允许的取值范围。
   * @throws DuplicateKeyException
   *     若新增或更新的对象的某个字段的取值与数据库中已有数据该字段的取值相同，且该字段的取值不
   *     可重复。
   * @throws ForeignKeyConstraintFailException
   *     若新增或更新的对象的某个字段值应该是对另一张表的某个字段的引用，但所引用的对象却不存在。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   */
  public static <T extends WithCode, P extends WithCode>
      Instant addOrUpdateByParentCodeImpl(final AddOrUpdatableByParentCodeDao<T> dao,
      final T obj, final GetterMethod<T, P> parentGetter)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Add or update {} by its parent code and its code: {}",
        dao.getEntityName(), obj);
    final Class<T> entityClass = dao.getEntityClass();
    final String parentFieldName = FieldUtils.getFieldName(entityClass, parentGetter);
    final P parent = parentGetter.invoke(obj);
    if (parent == null) {
      throw new NullFieldException(parentFieldName + "Id");
    }
    final boolean exist = dao.getMapper().existCode(parent.getCode(), obj.getCode());
    if (!exist) {
      return dao.add(obj);
    } else {
      return dao.updateByCode(obj);
    }
  }

  /**
   * 增加一个新的实体或根据证件更新已有的实体。
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param obj
   *     待增加或更新的实体，更新时使用其<b>证件</b>确定该对象；若该对象不存在，则添加一个新对
   *     象；否则用此参数更新存在的对象。
   * @return 数据被创建或被修改时的时间戳。
   * @throws NullFieldException
   *     若新增或更新的对象的任意必须字段为空。
   * @throws InvalidFieldFormatException
   *     若新增或更新的对象的某个字段格式不正确。
   * @throws FieldTooLongException
   *     若新增或更新的对象的某个字段值的长度超过了数据库表中该字段允许的长度。
   * @throws FieldValueOutOfRangeException
   *     若新增或更新的对象的某个字段值超过了数据库表中该字段允许的取值范围。
   * @throws DuplicateKeyException
   *     若新增或更新的对象的某个字段的取值与数据库中已有数据该字段的取值相同，且该字段的取值不
   *     可重复。
   * @throws ForeignKeyConstraintFailException
   *     若新增或更新的对象的某个字段值应该是对另一张表的某个字段的引用，但所引用的对象却不存在。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   */
  public static <T extends Identifiable & WithCredential>
      Instant addOrUpdateByCredentialImpl(final AddOrUpdatableByCredentialDao<T> dao,
      final T obj) throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Add or update {} by its credential: {}", dao.getEntityName(), obj);
    final boolean exist = dao.getMapper().existCredential(obj.getCredential());
    if (!exist) {
      return dao.add(obj);
    } else {
      return dao.updateByCredential(obj);
    }
  }

  /**
   * Updates an existing entity.
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param obj
   *     The new data of the existing entity to be updated, identified by its ID.
   * @return
   *     The timestamp when the entity was modified.
   * @throws DataNotExistException
   *     If the specified entity does not exist.
   * @throws NullFieldException
   *     If the database field corresponding to an attribute of the entity
   *     should not be empty but its attribute value is empty.
   * @throws InvalidFieldFormatException
   *     If an attribute value of the entity does not conform to the format
   *     required by its corresponding database field.
   * @throws FieldTooLongException
   *     If the length of an attribute value of the entity exceeds the length
   *     allowed by its corresponding database field.
   * @throws FieldValueOutOfRangeException
   *     If the range of an attribute value of the entity exceeds the range
   *     allowed by its corresponding database field.
   * @throws DuplicateKeyException
   *     If the database field corresponding to an attribute value of the entity
   *     is required to be unique, but the attribute value is the same as an
   *     existing object in the database.
   * @throws ForeignKeyConstraintFailException
   *     If the database field corresponding to an attribute is associated with
   *     an associated field of another associated table, but the attribute
   *     value does not exist in the associated field of the associated table.
   * @throws DataAccessException
   *     If other uncategorizable database operation errors occur.
   */
  public static <T extends Identifiable> Instant updateImpl(
      final UpdatableDao<T> dao, final T obj) throws DataAccessException {
    // normalize the entity if necessary
    if (obj instanceof Normalizable) {
      ((Normalizable) obj).normalize();
    }
    // set the last modification time of the entity if necessary
    final Instant modifyTime = dao.now();
    if (obj instanceof Modifiable) {
      ((Modifiable) obj).setModifyTime(modifyTime);
    }
    final Logger logger = dao.getLogger();
    logger.debug("Update {}: {}", dao.getEntityName(), obj);
    // perform the updating via the mapper
    final long count = dao.getMapper().update(obj);
    if (count == 0) {
      throw new DataNotExistException(dao.getEntityClass(), "id", obj.getId());
    }
    return modifyTime;
  }

  /**
   * 更新一个已存在的实体的最后一次修改时间。
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param id
   *     待更新的已存在的实体对象的ID。
   * @param modifyTime
   *     待更新的已存在实体对象的新的最后一次修改时间戳。
   * @return
   *     为保持接口标准一致性，返回数据被修改时的时间戳，即参数{@code modifyTime}。
   * @throws DataNotExistException
   *     若指定的实体对象不存在。
   * @throws NullFieldException
   *     如果该实体的某个属性对应的字段非空但其属性值为空。
   * @throws InvalidFieldFormatException
   *     如果该实体的某个属性值不符合其对应的字段所需的格式。
   * @throws FieldTooLongException
   *     如果该实体的某个属性值长度超过了其对应字段允许的长度。
   * @throws FieldValueOutOfRangeException
   *     如果该实体的某个属性值的范围超出了其对应字段允许的范围。
   * @throws DuplicateKeyException
   *     如果该实体的某个属性值对应的字段要求唯一，但该属性值和数据库中已存在的对象重复。
   * @throws ForeignKeyConstraintFailException
   *     如果某个属性对应的字段关联了另一张关联表的某个关联字段，而该属性值在关联表的关联字段
   *     中不存在。
   * @throws DataAccessException
   *     如果发生其他无法归类的数据库操作错误。
   */
  public static <T extends Identifiable & Modifiable> Instant updateModifyTimeImpl(
      final ModifyTimeUpdatableDao<T> dao, final Long id, final Instant modifyTime)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Update the modify time of {}: id = {}, modifyTime = {}",
        dao.getEntityName(), id, modifyTime);
    final long count = dao.getMapper().updateModifyTime(id, modifyTime);
    if (count == 0) {
      throw new DataNotExistException(dao.getEntityClass(), "id", id);
    }
    return modifyTime;
  }

  /**
   * 根据一个或多个唯一键值更新一个已存在的实体。
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param updater
   *     具体进行更新操作的函子，通常用lambda表达式实现。
   * @param obj
   *     待更新的已存在的实体对象的新数据，更新时使用其指定的主键的值确定该对象。
   * @param keyName
   *     用于确定指定实体的首要键的名称。
   * @param keyValue
   *     用于确定指定实体对象的首要键的值。
   * @param otherKeys
   *     用于确定指定实体对象的其他键名和键值对。
   * @return
   *     数据被修改时的时间戳。
   * @throws DataNotExistException
   *     若指定的实体对象不存在。
   * @throws NullFieldException
   *     如果该实体的某个属性对应的字段非空但其属性值为空。
   * @throws InvalidFieldFormatException
   *     如果该实体的某个属性值不符合其对应的字段所需的格式。
   * @throws FieldTooLongException
   *     如果该实体的某个属性值长度超过了其对应字段允许的长度。
   * @throws FieldValueOutOfRangeException
   *     如果该实体的某个属性值的范围超出了其对应字段允许的范围。
   * @throws DuplicateKeyException
   *     如果该实体的某个属性值对应的字段要求唯一，但该属性值和数据库中已存在的对象重复。
   * @throws ForeignKeyConstraintFailException
   *     如果某个属性对应的字段关联了另一张关联表的某个关联字段，而该属性值在关联表的关联字段
   *     中不存在。
   * @throws DataAccessException
   *     如果发生其他无法归类的数据库操作错误。
   */
  public static <T> Instant updateByKeyImpl(final Dao<T> dao,
      final ToLongFunction<Instant> updater, final T obj, final String keyName,
      final Object keyValue, final KeyValuePair ... otherKeys)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    final String args = (otherKeys.length == 0 ? "" : ", " + join(", ", otherKeys));
    logger.debug("Update {} by its {}{}: {}", dao.getEntityName(), keyName,
        args, obj);
    if (obj instanceof Normalizable) {
      ((Normalizable) obj).normalize();
    }
    final Instant modifyTime = dao.now();
    if (obj instanceof Modifiable) {
      ((Modifiable) obj).setModifyTime(modifyTime);
    }
    final long count = updater.applyAsLong(modifyTime);
    if (count == 0) {
      throw new DataNotExistException(dao.getEntityClass(), keyName,
          keyValue, otherKeys);
    }
    return modifyTime;
  }

  /**
   * 根据指定的唯一键，更新一个已存在的实体的指定属性。
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param updater
   *     具体进行更新操作的函子，通常用lambda表达式实现。
   * @param propertyName
   *     待更新的属性的名称。
   * @param propertyValue
   *     待更新的属性的值。
   * @param keyName
   *     用于确定待更新实体对象的唯一键的名称。
   * @param keyValue
   *     用于确定待更新实体对象的唯一键的值。
   * @return
   *     完成此更新操作时的时间戳。
   * @throws DataNotExistException
   *     若指定的实体对象不存在。
   * @throws NullFieldException
   *     如果该实体的某个属性对应的字段非空但其属性值为空。
   * @throws InvalidFieldFormatException
   *     如果该实体的某个属性值不符合其对应的字段所需的格式。
   * @throws FieldTooLongException
   *     如果该实体的某个属性值长度超过了其对应字段允许的长度。
   * @throws FieldValueOutOfRangeException
   *     如果该实体的某个属性值的范围超出了其对应字段允许的范围。
   * @throws DuplicateKeyException
   *     如果该实体的某个属性值对应的字段要求唯一，但该属性值和数据库中已存在的对象重复。
   * @throws ForeignKeyConstraintFailException
   *     如果某个属性对应的字段关联了另一张关联表的某个关联字段，而该属性值在关联表的关联字段
   *     中不存在。
   * @throws DataAccessException
   *     如果发生其他无法归类的数据库操作错误。
   */
  public static <T> Instant updatePropertyByKeyImpl(final Dao<T> dao,
      final ToLongFunction<Instant> updater,
      final String propertyName, final Object propertyValue,
      final String keyName, final Object keyValue,
      final KeyValuePair ... otherKeys)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    final String args = (otherKeys.length == 0 ? "" : ", " + join(", ", otherKeys));
    logger.debug("Update the {} of {} by {}: {} = {}{}, {} = {}", propertyName,
        dao.getEntityName(), keyName, keyName, keyValue, args, propertyName, propertyValue);
    if (propertyValue instanceof Normalizable) {
      ((Normalizable) propertyValue).normalize();
    }
    final Instant modifyTime = dao.now();
    final long count = updater.applyAsLong(modifyTime);
    if (count == 0) {
      throw new DataNotExistException(dao.getEntityClass(), keyName, keyValue, otherKeys);
    }
    return modifyTime;
  }

  /**
   * Restores the specified mark deleted entity.
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param restorer
   *     A functor that performs the actual restoring operation, usually
   *     implemented as a lambda expression.
   * @param keyName
   *     The name of the key used to determine the specified entity.
   * @param keyValue
   *     The value of the key used to determine the specified entity.
   * @param otherKeys
   *     Additional key-value pairs used to determine the specified entity.
   * @return
   *     The timestamp when the entity was restored.
   * @throws DataNotExistException
   *     If the specified entity does not exist or has not been marked as
   *     deleted.
   * @throws DataAccessException
   *     If any other data access error occurs.
   */
  public static <T extends Deletable> Instant restoreByKeyImpl(final Dao<T> dao,
      final ToLongFunction<Instant> restorer, final String keyName,
      final Object keyValue, final KeyValuePair ... otherKeys)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    final String args = (otherKeys.length == 0 ? "" : ", " + join(", ", otherKeys));
    logger.debug("Restore mark deleted {} by its {}{}.", dao.getEntityName(),
        keyName, args);
    final Instant restoreTime = dao.now();
    final long count = restorer.applyAsLong(restoreTime);
    if (count == 0) {
      throw new DataNotExistException(dao.getEntityClass(), keyName, keyValue, otherKeys);
    }
    return restoreTime;
  }

  /**
   * Completely removes the specified mark deleted entity from the database.
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param purger
   *     A functor that performs the actual purging operation, usually
   *     implemented as a lambda expression.
   * @param keyName
   *     The name of the key used to determine the specified entity.
   * @param keyValue
   *     The value of the key used to determine the specified entity.
   * @param otherKeys
   *     Additional key-value pairs used to determine the specified entity.
   * @return
   *     The timestamp when the entity was restored.
   * @throws DataNotExistException
   *     If the specified entity does not exist or has not been marked as
   *     deleted.
   * @throws DataAccessException
   *     If any other data access error occurs.
   */
  public static <T extends Deletable> Instant purgeByKeyImpl(final Dao<T> dao,
      final ToLongFunction<Instant> purger, final String keyName,
      final Object keyValue, final KeyValuePair ... otherKeys)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    final String args = (otherKeys.length == 0 ? "" : ", " + join(", ", otherKeys));
    logger.debug("Purge mark deleted {} by its {}{}.", dao.getEntityName(),
        keyName, args);
    final Instant purgeTime = dao.now();
    final long count = purger.applyAsLong(purgeTime);
    if (count == 0) {
      throw new DataNotExistException(dao.getEntityClass(), keyName, keyValue, otherKeys);
    }
    return purgeTime;
  }

  /**
   * Completely removes <b>all</b> mark deleted entities from the database.
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @return
   *     The number of entities removed by this operation.
   * @throws DataAccessException
   *     If any other data access error occurs.
   */
  public static <T extends Identifiable & Deletable> long purgeAllImpl(
      final DeletableDao<T> dao) throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Purge all {}.", dao.getEntityName());
    return dao.getMapper().purgeAll();
  }

  /**
   * Mark deletes the specified entity.
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param deleter
   *     A functor that performs the actual marking operation, usually
   *     implemented as a lambda expression.
   * @param keyName
   *     The name of the key used to determine the specified entity.
   * @param keyValue
   *     The value of the key used to determine the specified entity.
   * @param otherKeys
   *     Additional key-value pairs used to determine the specified entity.
   * @return
   *     The timestamp when the entity was marked.
   * @throws DataNotExistException
   *     If the specified entity does not exist or has already been marked as
   *     deleted.
   * @throws DataAccessException
   *     If any other data access error occurs.
   */
  public static <T extends Deletable> Instant deleteByKeyImpl(final Dao<T> dao,
      final ToLongFunction<Instant> deleter, final String keyName,
      final Object keyValue, final KeyValuePair ... otherKeys)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    final String args = (otherKeys.length == 0 ? "" : ", " + join(", ", otherKeys));
    logger.debug("Mark delete {} by its {}: {} = {}{}.", dao.getEntityName(),
        keyName, keyName, keyValue, args);
    final Instant deleteTime = dao.now();
    final long count = deleter.applyAsLong(deleteTime);
    if (count == 0) {
      throw new DataNotExistException(dao.getEntityClass(), keyName, keyValue, otherKeys);
    }
    return deleteTime;
  }

  /**
   * Completely erases the specified entity from the database.
   * <p>
   * <b>Note: </b>This operation will actually remove the specified entity
   * from the database, and the deleted entity cannot be recovered.
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @param eraser
   *     A functor that performs the actual erasing operation, usually
   *     implemented as a lambda expression.
   * @param keyName
   *     The name of the key used to determine the specified entity.
   * @param keyValue
   *     The value of the key used to determine the specified entity.
   * @param otherKeys
   *     Additional key-value pairs used to determine the specified entity.
   * @return
   *     The timestamp when the entity was erased.
   * @throws DataNotExistException
   *     If the specified entity does not exist.
   * @throws DataAccessException
   *     If any other data access error occurs.
   */
  public static <T> Instant eraseByKeyImpl(final Dao<T> dao,
      final ToLongFunction<Instant> eraser, final String keyName,
      final Object keyValue, final KeyValuePair ... otherKeys)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    final String args = (otherKeys.length == 0 ? "" : ", " + join(", ", otherKeys));
    logger.debug("Erase {} by its {}{}.", dao.getEntityName(), keyName, args);
    final Instant eraseTime = dao.now();
    final long count = eraser.applyAsLong(eraseTime);
    if (count == 0) {
      throw new DataNotExistException(dao.getEntityClass(), keyName, keyValue, otherKeys);
    }
    return eraseTime;
  }

  /**
   * 彻底清除<b>指定</b>的实体对象。
   *
   * <p><b>注意：</b>此操作将从数据库中彻底清除<b>所有</b>的对象，此操作不可逆。
   *
   * @param <T>
   *     The type of entities.
   * @param owner
   *     待彻底清除的实体对象的所有者。
   * @return
   *     此操作所彻底清除的对象的数目。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #deleteForOwnerImpl(DeletableForOwnerDao, Owner)
   * @see #restoreForOwnerImpl(DeletableForOwnerDao, Owner)
   * @see #purgeForOwnerImpl(DeletableForOwnerDao, Owner)
   */
  public static <T extends Identifiable & WithOwner> long eraseForOwnerImpl(
      final ErasableByOwnerDao<T> dao, final Owner owner)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Erase {}: owner = {}", dao.getEntityName(), owner);
    return dao.getMapper().eraseForOwner(owner);
  }

  /**
   * Completely removes <b>all</b> entities from the database.
   * <p>
   * <b>Note: </b>This operation will completely remove <b>all</b> entities from
   * the database, that is, empty the entire database table. This operation is
   * irreversible.
   *
   * @param <T>
   *     The type of entities.
   * @param dao
   *     The DAO object.
   * @return
   *     The number of entities removed by this operation.
   * @throws DataAccessException
   *     if any other data access error occurs.
   */
  public static <T> long clearImpl(final ClearableDao<T> dao)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Clear all {}.", dao.getEntityName());
    return dao.getMapper().clear();
  }

  /**
   * 标记删除指定的实体对象。
   *
   * <p><b>注意：</b>此操作并不真正从数据库中删除指定的对象，只是将其标记删除，并记录被
   * 标记删除时的时间戳。若要真正从数据库中删除对象，请使用
   * {@link #purgeForOwnerImpl(DeletableForOwnerDao, Owner)}函数。
   *
   * @param <T>
   *     The type of entities.
   * @param owner
   *     待删除的实体对象的所有者。
   * @return
   *     此操作所标记删除的对象数，若指定的对象不存在或已被标记删除则返回0。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #restoreForOwnerImpl(DeletableForOwnerDao, Owner)
   * @see #purgeForOwnerImpl(DeletableForOwnerDao, Owner)
   * @see #eraseForOwnerImpl(ErasableByOwnerDao, Owner)
   */
  public static <T extends Identifiable & Deletable & WithOwner>
      long deleteForOwnerImpl(final DeletableForOwnerDao<T> dao, final Owner owner)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Delete {}: owner = {}", dao.getEntityName(), owner);
    final Instant deleteTime = dao.now();
    return dao.getMapper().deleteForOwner(owner, deleteTime);
  }

  /**
   * 恢复被标记删除的实体对象。
   *
   * <p><b>注意：</b>此操作只能恢复被标记删除的对象，即通过调用
   * {@link #deleteForOwnerImpl(DeletableForOwnerDao, Owner)}被标记删除的对象。
   * 若该对象已经被彻底清除，则无法再被恢复。
   *
   * @param <T>
   *     The type of entities.
   * @param owner
   *     待恢复的已被标记删除的实体对象的所有者。
   * @return
   *     此操作所恢复的对象数，若指定的待恢复对象不存在或未被标记删除则返回0。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #deleteForOwnerImpl(DeletableForOwnerDao, Owner)
   * @see #purgeForOwnerImpl(DeletableForOwnerDao, Owner)
   * @see #eraseForOwnerImpl(ErasableByOwnerDao, Owner)
   */
  public static <T extends Identifiable & Deletable & WithOwner>
      long restoreForOwnerImpl(final DeletableForOwnerDao<T> dao, final Owner owner)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Restore {}: owner = {}", dao.getEntityName(), owner);
    return dao.getMapper().restoreForOwner(owner);
  }

  /**
   * 彻底清除被标记删除的实体对象。
   *
   * <p><b>注意：</b>此操作将从数据库中彻底清除被标记删除的对象，即通过调用
   * {@link #deleteForOwnerImpl(DeletableForOwnerDao, Owner)} 被标记删除的对象。
   * 被彻底清除的对象，无法通过调用
   * {@link #restoreForOwnerImpl(DeletableForOwnerDao, Owner)} 再被恢复。
   *
   * @param <T>
   *     The type of entities.
   * @param owner
   *     待彻底清除的已被标记删除的实体对象的所有者。
   * @return
   *     此操作所彻底清除的对象数，若指定的待彻底清除的对象不存在或未被标记删除则返回0。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #deleteForOwnerImpl(DeletableForOwnerDao, Owner)
   * @see #restoreForOwnerImpl(DeletableForOwnerDao, Owner)
   * @see #eraseForOwnerImpl(ErasableByOwnerDao, Owner)
   */
  public static <T extends Identifiable & Deletable & WithOwner>
      long purgeForOwnerImpl(final DeletableForOwnerDao<T> dao, final Owner owner)
      throws DataAccessException {
    final Logger logger = dao.getLogger();
    logger.debug("Purge {}: owner = {}", dao.getEntityName(), owner);
    return dao.getMapper().purgeForOwner(owner);
  }

  /**
   * 在添加新实体对象之前检查其证件是否已存在于数据库中其他同类实体对象中。
   *
   * @param <T>
   *     指定的实体对象的类型。
   * @param dao
   *     指定的数据库DAO。
   * @param obj
   *     待添加的新的实体对象。
   * @throws DuplicateKeyException
   *     若待添加的新的实体对象的证件已经存在于数据库中其他同类实体对象中。
   * @throws DataAccessException
   *     若发生其他数据存取错误。
   */
  public static <T extends Identifiable & WithCredential>
      void checkDuplicatedCredentialBeforeAdd(final GettableByCredentialDao<T> dao,
      final T obj) throws DataAccessException {
    final CredentialInfo credential = obj.getCredential();
    if (credential != null) {
      if (dao.getMapper().existCredential(credential)) {
        final CredentialInfoCodec codec = new CredentialInfoCodec();
        throw new DuplicateKeyException("credential", codec.encode(credential));
      }
    }
  }

  /**
   * 在更新实体对象之前检查其证件是否已存在于数据库中其他同类实体对象中。
   *
   * @param <T>
   *     指定的实体对象的类型。
   * @param dao
   *     指定的数据库DAO。
   * @param obj
   *     待更新的实体对象，通过其ID确定其所对应的已存在实体对象。
   * @throws DuplicateKeyException
   *     若待更新的实体对象的新的证件已经存在于数据库中其他同类实体对象中。
   * @throws DataAccessException
   *     若发生其他数据存取错误。
   */
  public static <T extends Identifiable & WithCredential>
      void checkDuplicatedCredentialBeforeUpdate(final GettableByCredentialDao<T> dao,
      final T obj) throws DataAccessException {
    final CredentialInfo credential = obj.getCredential();
    if (credential != null) {
      final Long existingId = dao.getMapper().getIdByCredential(credential);
      if ((existingId != null) && (!existingId.equals(obj.getId()))) {
        final CredentialInfoCodec codec = new CredentialInfoCodec();
        throw new DuplicateKeyException("credential", codec.encode(credential));
      }
    }
  }
}
