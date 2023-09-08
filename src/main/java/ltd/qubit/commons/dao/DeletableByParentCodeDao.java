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
import ltd.qubit.commons.dao.mapper.DeletableByParentCodeMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.Deletable;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithCode;
import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * 此接口表示实现根据编码标记删除实体操作的DAO。
 *
 * <p><b>注意：</b>此实体对象的编码在其所属App下必须唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #deleteByCode(Long, String)}</li>
 * <li>{@link #restoreByCode(Long, String)}</li>
 * <li>{@link #purgeByCode(Long, String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface DeletableByParentCodeDao<T extends Identifiable & WithCode & Deletable>
    extends Dao<T> {

  @Override
  DeletableByParentCodeMapper<T> getMapper();

  /**
   * 标记删除指定的实体对象。
   *
   * <p><b>注意：</b>此操作并不真正从数据库中删除指定的对象，只是将其标记删除，并记录被
   * 标记删除时的时间戳。
   *
   * @param parentId
   *     指定的对象所属父对象的ID。
   * @param code
   *     待删除的实体对象的编码。
   * @return
   *     数据被标记删除时的时间戳。
   * @throws DataNotExistException
   *     若指定的待删除对象不存在或已被标记删除。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #purgeByCode(Long, String)
   * @see #restoreByCode(Long, String)
   */
  @Modified("deleteTime")
  default Instant deleteByCode(final Long parentId, final String code)
      throws DataAccessException {
    return DaoImplHelper.deleteByKeyImpl(this, t -> getMapper().deleteByCode(parentId, code, t),
        "code", code, new KeyValuePair("parentId", parentId));
  }

  /**
   * 恢复被标记删除的实体对象。
   *
   * <p><b>注意：</b>此操作只能恢复被标记删除的对象，若该对象已经被彻底清除，则无法再被恢复。
   *
   * @param parentId
   *     指定的对象所属父对象的ID。
   * @param code
   *     待恢复的已被标记删除的实体对象的编码。
   * @throws DataNotExistException
   *     若指定的待恢复对象不存在，或该对象未被标记删除。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #deleteByCode(Long, String)
   * @see #purgeByCode(Long, String)
   */
  @Modified("deleteTime")
  default void restoreByCode(final Long parentId, final String code)
      throws DataAccessException {
    DaoImplHelper.restoreByKeyImpl(this, t -> getMapper().restoreByCode(parentId, code),
        "code", code, new KeyValuePair("parentId", parentId));
  }

  /**
   * 彻底清除被标记删除的实体对象。
   *
   * <p><b>注意：</b>此操作将从数据库中彻底清除被标记删除的对象，被彻底清除的对象，无法再被恢
   * 复。
   *
   * @param parentId
   *     指定的对象所属父对象的ID。
   * @param code
   *     待彻底清除的已被标记删除的实体对象的编码。
   * @throws DataNotExistException
   *     若指定的待清除对象不存在，或该对象未被标记删除。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #deleteByCode(Long, String)
   * @see #restoreByCode(Long, String)
   */
  default void purgeByCode(final Long parentId, final String code)
      throws DataAccessException {
    DaoImplHelper.purgeByKeyImpl(this, t -> getMapper().purgeByCode(parentId, code),
        "code", code, new KeyValuePair("parentId", parentId));
  }
}
