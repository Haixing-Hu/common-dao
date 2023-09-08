////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import javax.annotation.Nullable;

import jakarta.validation.constraints.NotNull;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.mapper.GettableByEntityNameMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.WithEntity;
import ltd.qubit.commons.model.WithName;
import ltd.qubit.commons.util.pair.KeyValuePair;

import static ltd.qubit.commons.dao.DaoImplHelper.existKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyOrNullImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有{@code entity}和{@code name}属性的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>对象的{@code name}在同一个{@code entity}下必须唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #existName(String, String)}</li>
 * <li>{@link #getByName(String, String)}</li>
 * <li>{@link #getByNameOrNull(String, String)}</li>
 * <li>{@link #getIdByName(String, String)}</li>
 * <li>{@link #getIdByNameOrNull(String, String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface GettableByEntityNameDao<T extends WithEntity & WithName>
    extends Dao<T> {

  @Override
  GettableByEntityNameMapper<T> getMapper();

  /**
   * 判定拥有指定实体类和名称的对象是否存在。
   *
   * @param entity
   *     指定的实体类。
   * @param name
   *     指定的名称。
   * @return
   *     若拥有指定实体类和指定名称的对象存在，则返回{@code true}；否则返回{@code false}。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  default boolean existName(final String entity, final String name)
      throws DataAccessException {
    return existKeyImpl(this, () -> getMapper().existName(entity, name),
        "name", name, new KeyValuePair("entity", entity));
  }

  /**
   * 根据实体类和名称获取指定的对象。
   *
   * @param entity
   *     指定对象的实体类。
   * @param name
   *     指定对象的名称。
   * @return
   *     具有指定实体类和名称的对象。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default T getByName(final String entity, final String name)
      throws DataAccessException {
    return getByKeyImpl(this, () -> getMapper().getByName(entity, name),
        "name", name, new KeyValuePair("entity", entity));
  }

  /**
   * 根据实体类和名称获取指定的对象。
   *
   * @param entity
   *     指定对象的实体类。
   * @param name
   *     指定对象的名称。
   * @return
   *     具有指定实体类和名称的对象，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default T getByNameOrNull(final String entity, final String name)
      throws DataAccessException {
    return getByKeyOrNullImpl(this, () -> getMapper().getByName(entity, name),
        "name", name, new KeyValuePair("entity", entity));
  }

  /**
   * 根据实体类和名称获取指定的对象的ID。
   *
   * @param entity
   *     指定对象的实体类。
   * @param name
   *     指定对象的名称。
   * @return
   *     具有指定实体类和名称的对象的ID。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default Long getIdByName(final String entity, final String name) throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> getMapper().getIdByName(entity, name),
        "id", "name", name, new KeyValuePair("entity", entity));
  }

  /**
   * 根据实体类和名称获取指定的对象的ID。
   *
   * @param entity
   *     指定对象的实体类。
   * @param name
   *     指定对象的名称。
   * @return
   *     具有指定实体类和名称的对象的ID，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Long getIdByNameOrNull(final String entity, final String name)
      throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getIdByName(entity, name),
        "id", "name", name, new KeyValuePair("entity", entity));
  }
}
