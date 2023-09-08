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
import ltd.qubit.commons.dao.mapper.DeletableByParentNameMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.Deletable;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithName;
import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * 此接口表示实现根据名称标记删除实体操作的DAO。
 *
 * <p><b>注意：</b>对象的名称在其所属父对象下必须唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #deleteByName(Long, String)}</li>
 * <li>{@link #restoreByName(Long, String)}</li>
 * <li>{@link #purgeByName(Long, String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface DeletableByParentNameDao<T extends Identifiable & WithName & Deletable>
    extends Dao<T> {

  @Override
  DeletableByParentNameMapper<T> getMapper();

  /**
   * 标记删除指定的实体对象。
   *
   * <p><b>注意：</b>此操作并不真正从数据库中删除指定的对象，只是将其标记删除，并记录被
   * 标记删除时的时间戳。
   *
   * @param parentId
   *     指定的对象所属的父对象的ID。
   * @param name
   *     待删除的实体对象的名称。
   * @return
   *     数据被标记删除时的时间戳。
   * @throws DataNotExistException
   *     若指定的待删除对象不存在或已被标记删除。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #purgeByName(Long, String)
   * @see #restoreByName(Long, String)
   */
  @Modified("deleteTime")
  default Instant deleteByName(final Long parentId, final String name)
      throws DataAccessException {
    return DaoImplHelper.deleteByKeyImpl(this, t -> getMapper().deleteByName(parentId, name, t),
        "name", name, new KeyValuePair("parentId", parentId));
  }

  /**
   * 恢复被标记删除的实体对象。
   *
   * <p><b>注意：</b>此操作只能恢复被标记删除的对象，若该对象已经被彻底清除，则无法再被恢复。
   *
   * @param parentId
   *     指定的对象所属的父对象的ID。
   * @param name
   *     待恢复的已被标记删除的实体对象的名称。
   * @throws DataNotExistException
   *     若指定的待恢复对象不存在，或该对象未被标记删除。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #deleteByName(Long, String)
   * @see #purgeByName(Long, String)
   */
  @Modified("deleteTime")
  default void restoreByName(final Long parentId, final String name)
      throws DataAccessException {
    DaoImplHelper.restoreByKeyImpl(this, t -> getMapper().restoreByName(parentId, name),
        "name", name, new KeyValuePair("parentId", parentId));
  }

  /**
   * 彻底清除被标记删除的实体对象。
   *
   * <p><b>注意：</b>此操作将从数据库中彻底清除被标记删除的对象，被彻底清除的对象，无法再被恢
   * 复。
   *
   * @param parentId
   *     指定的对象所属的父对象的ID。
   * @param name
   *     待彻底清除的已被标记删除的实体对象的名称。
   * @throws DataNotExistException
   *     若指定的待清除对象不存在，或该对象未被标记删除。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #deleteByName(Long, String)
   * @see #restoreByName(Long, String)
   */
  default void purgeByName(final Long parentId, final String name)
      throws DataAccessException {
    DaoImplHelper.purgeByKeyImpl(this, t -> getMapper().purgeByName(parentId, name),
        "name", name, new KeyValuePair("parentId", parentId));
  }
}
