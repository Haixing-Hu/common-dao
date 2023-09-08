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

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.mapper.ClearableMapper;
import ltd.qubit.commons.dao.mapper.DeletableByNameMapper;
import ltd.qubit.commons.dao.mapper.DeletableMapper;
import ltd.qubit.commons.dao.mapper.ErasableByEntityNameMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithEntity;
import ltd.qubit.commons.model.WithName;
import ltd.qubit.commons.util.pair.KeyValuePair;

import static ltd.qubit.commons.dao.DaoImplHelper.eraseByKeyImpl;

/**
 * 此接口表示实现删除实体操作的DAO。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #eraseByName(String, String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface ErasableByEntityNameDao<T extends Identifiable & WithEntity & WithName>
    extends Dao<T> {

  @Override
  ErasableByEntityNameMapper<T> getMapper();

  /**
   * 彻底删除指定的实体对象。
   *
   * <p><b>注意：</b>此操作将真正从数据库中删除指定的对象，被删除的对象不可恢复。
   *
   * @param entity
   *     待彻底清除的实体对象所属的实体类名称。
   * @param name
   *     待彻底清除的体对象的名称，如果为{@code null}则不做限制。
   * @throws DataNotExistException
   *     若指定的待删除对象不存在。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see ClearableMapper#clear()
   * @see DeletableMapper#delete(Long, Instant)
   * @see DeletableByNameMapper#deleteByName(String, Instant)
   * @see DeletableMapper#restore(Long)
   * @see DeletableByNameMapper#restoreByName(String)
   * @see DeletableMapper#purge(Long)
   * @see DeletableByNameMapper#purgeByName(String)
   * @see DeletableMapper#purgeAll()
   */
  default void eraseByName(final String entity, final String name)
      throws DataAccessException {
    eraseByKeyImpl(this, t -> getMapper().eraseByName(entity, name),
        "name", name, new KeyValuePair("entity", entity));
  }
}
