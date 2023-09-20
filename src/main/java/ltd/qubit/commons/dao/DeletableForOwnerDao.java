////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.annotation.Modified;
import ltd.qubit.commons.model.util.Deletable;
import ltd.qubit.commons.model.util.Identifiable;
import ltd.qubit.commons.model.util.Owner;
import ltd.qubit.commons.model.util.WithOwner;

import static ltd.qubit.commons.dao.DaoImplHelper.deleteForOwnerImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.purgeForOwnerImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.restoreForOwnerImpl;

/**
 * 此接口表示实现删除实体操作的DAO。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #deleteForOwner(Owner)}</li>
 * <li>{@link #restoreForOwner(Owner)}</li>
 * <li>{@link #purgeForOwner(Owner)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface DeletableForOwnerDao<T extends Identifiable & Deletable & WithOwner>
    extends Dao<T> {

  @Override
  DeletableForOwnerMapper<T> getMapper();

  /**
   * 标记删除指定的实体对象。
   *
   * <p><b>注意：</b>此操作并不真正从数据库中删除指定的对象，只是将其标记删除，并记录被
   * 标记删除时的时间戳。若要真正从数据库中删除对象，请使用{@link #purgeForOwner(Owner)}
   * 函数。
   *
   * @param owner
   *     待删除的实体对象的所有者。
   * @return
   *     此操作所标记删除的对象数，若指定的对象不存在或已被标记删除则返回0。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #restoreForOwner(Owner)
   * @see #purgeForOwner(Owner)
   */
  @Modified("deleteTime")
  default long deleteForOwner(final Owner owner) throws DataAccessException {
    return deleteForOwnerImpl(this, owner);
  }

  /**
   * 恢复被标记删除的实体对象。
   *
   * <p><b>注意：</b>此操作只能恢复被标记删除的对象，即通过调用
   * {@link #deleteForOwner(Owner)}被标记删除的对象。若该对象已经被彻底
   * 清除，则无法再被恢复。
   *
   * @param owner
   *     待恢复的已被标记删除的实体对象的所有者。
   * @return
   *     此操作所恢复的对象数，若指定的待恢复对象不存在或未被标记删除则返回0。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #deleteForOwner(Owner)
   * @see #purgeForOwner(Owner)
   */
  @Modified("deleteTime")
  default long restoreForOwner(final Owner owner) throws DataAccessException {
    return restoreForOwnerImpl(this, owner);
  }

  /**
   * 彻底清除被标记删除的实体对象。
   *
   * <p><b>注意：</b>此操作将从数据库中彻底清除被标记删除的对象，即通过调用
   * {@link #deleteForOwner(Owner)}被标记删除的对象。被彻底清除的对象，无
   * 法通过调用{@link #restoreForOwner(Owner)}再被恢复。
   *
   * @param owner
   *     待彻底清除的已被标记删除的实体对象的所有者。
   * @return
   *     此操作所彻底清除的对象数，若指定的待彻底清除的对象不存在或未被标记删除则返回0。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #deleteForOwner(Owner)
   * @see #restoreForOwner(Owner)
   */
  default long purgeForOwner(final Owner owner) throws DataAccessException {
    return purgeForOwnerImpl(this, owner);
  }
}
