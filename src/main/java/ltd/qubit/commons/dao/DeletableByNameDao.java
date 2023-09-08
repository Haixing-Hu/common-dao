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

import ltd.qubit.commons.annotation.Modified;
import ltd.qubit.commons.dao.mapper.DeletableByNameMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.Deletable;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithName;

import static ltd.qubit.commons.dao.DaoImplHelper.deleteByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.purgeByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.restoreByKeyImpl;

/**
 * 此接口表示实现根据名称标记删除实体操作的DAO。
 *
 * <p><b>注意：</b>此实体对象的名称必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #deleteByName(String)}</li>
 * <li>{@link #restoreByName(String)}</li>
 * <li>{@link #purgeByName(String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface DeletableByNameDao<T extends Identifiable & WithName & Deletable>
    extends Dao<T> {

  @Override
  DeletableByNameMapper<T> getMapper();

  /**
   * 标记删除指定的实体对象。
   *
   * <p><b>注意：</b>此操作并不真正从数据库中删除指定的对象，只是将其标记删除，并记录被
   * 标记删除时的时间戳。
   *
   * @param name
   *     待删除的实体对象的名称。
   * @return
   *     数据被标记删除时的时间戳。
   * @throws DataNotExistException
   *     若指定的待删除对象不存在或已被标记删除。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #purgeByName(String)
   * @see #restoreByName(String)
   */
  @Modified("deleteTime")
  default Instant deleteByName(final String name) throws DataAccessException {
    return deleteByKeyImpl(this, t -> getMapper().deleteByName(name, t), "name", name);
  }

  /**
   * 恢复被标记删除的实体对象。
   *
   * <p><b>注意：</b>此操作只能恢复被标记删除的对象，若该对象已经被彻底清除，则无法再被恢复。
   *
   * @param name
   *     待恢复的已被标记删除的实体对象的名称。
   * @throws DataNotExistException
   *     若指定的待恢复对象不存在，或该对象未被标记删除。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #deleteByName(String)
   * @see #purgeByName(String)
   */
  @Modified("deleteTime")
  default void restoreByName(final String name) throws DataAccessException {
    restoreByKeyImpl(this, t -> getMapper().restoreByName(name), "name", name);
  }

  /**
   * 彻底清除被标记删除的实体对象。
   *
   * <p><b>注意：</b>此操作将从数据库中彻底清除被标记删除的对象，被彻底清除的对象，无法再被恢
   * 复。
   *
   * @param name
   *     待彻底清除的已被标记删除的实体对象的名称。
   * @throws DataNotExistException
   *     若指定的待清除对象不存在，或该对象未被标记删除。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #deleteByName(String)
   * @see #restoreByName(String)
   */
  default void purgeByName(final String name) throws DataAccessException {
    purgeByKeyImpl(this, t -> getMapper().purgeByName(name), "name", name);
  }
}
