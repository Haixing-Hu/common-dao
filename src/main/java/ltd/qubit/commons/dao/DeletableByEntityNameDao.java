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

import javax.annotation.Nullable;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.annotation.Modified;
import ltd.qubit.commons.dao.mapper.DeletableByEntityNameMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.Deletable;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithEntity;
import ltd.qubit.commons.model.WithName;
import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * 此接口表示实现根据名称标记删除实体操作的DAO。
 *
 * <p><b>注意：</b>此实体对象的名称必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #deleteByName(String, String)}</li>
 * <li>{@link #restoreByName(String, String)}</li>
 * <li>{@link #purgeByName(String, String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface DeletableByEntityNameDao
    <T extends Identifiable & WithEntity & WithName & Deletable> extends Dao<T> {

  @Override
  DeletableByEntityNameMapper<T> getMapper();

  /**
   * 标记删除指定的实体对象。
   *
   * <p><b>注意：</b>此操作并不真正从数据库中删除指定的对象，只是将其标记删除，并记录被
   * 标记删除时的时间戳。
   *
   * @param entity
   *     待标记删除的实体对象所属的实体类名称。
   * @param name
   *     待标记删除的实体对象的名称，如果为{@code null}则不做限制。
   * @return
   *     数据被标记删除时的时间戳。
   * @throws DataNotExistException
   *     若指定的待删除对象不存在或已被标记删除。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #purgeByName(String, String)
   * @see #restoreByName(String, String)
   */
  @Modified("deleteTime")
  default Instant deleteByName(final String entity, @Nullable final String name)
      throws DataAccessException {
    return DaoImplHelper.deleteByKeyImpl(this, t -> getMapper().deleteByName(entity, name, t),
        "name", name, new KeyValuePair("entity", entity));
  }

  /**
   * 恢复被标记删除的实体对象。
   *
   * <p><b>注意：</b>此操作只能恢复被标记删除的对象，若该对象已经被彻底清除，则无法再被恢复。
   *
   * @param entity
   *     待恢复标记删除的实体对象所属的实体类名称。
   * @param name
   *     待恢复标记删除的实体对象的名称，如果为{@code null}则不做限制。
   * @throws DataNotExistException
   *     若指定的待恢复对象不存在，或该对象未被标记删除。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #deleteByName(String, String)
   * @see #purgeByName(String, String)
   */
  @Modified("deleteTime")
  default void restoreByName(final String entity, @Nullable final String name)
      throws DataAccessException {
    DaoImplHelper.restoreByKeyImpl(this, t -> getMapper().restoreByName(entity, name),
        "name", name, new KeyValuePair("entity", entity));
  }

  /**
   * 彻底清除被标记删除的实体对象。
   *
   * <p><b>注意：</b>此操作将从数据库中彻底清除被标记删除的对象，被彻底清除的对象，无法再被恢
   * 复。
   *
   * @param entity
   *     待彻底清除的已被标记删除的实体对象所属的实体类名称。
   * @param name
   *     待彻底清除的已被标记删除的实体对象的名称，如果为{@code null}则不做限制。
   * @throws DataNotExistException
   *     若指定的待清除对象不存在，或该对象未被标记删除。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #deleteByName(String, String)
   * @see #restoreByName(String, String)
   */
  default void purgeByName(final String entity, @Nullable final String name)
      throws DataAccessException {
    DaoImplHelper.purgeByKeyImpl(this, t -> getMapper().purgeByName(entity, name),
        "name", name, new KeyValuePair("entity", entity));
  }
}
