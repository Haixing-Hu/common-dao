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
import ltd.qubit.commons.dao.mapper.DeletableByCodeMapper;
import ltd.qubit.commons.dao.mapper.DeletableMapper;
import ltd.qubit.commons.dao.mapper.ErasableForOwnerMapper;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithOwner;
import ltd.qubit.model.commons.Owner;

import static ltd.qubit.commons.dao.DaoImplHelper.eraseForOwnerImpl;

/**
 * 此接口表示实现删除实体操作的DAO。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #eraseForOwner(Owner)} </li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface ErasableByOwnerDao<T extends Identifiable & WithOwner>
    extends Dao<T> {

  @Override
  ErasableForOwnerMapper<T> getMapper();

  /**
   * 彻底删除指定的实体对象。
   *
   * <p><b>注意：</b>此操作将真正从数据库中删除指定的对象，被删除的对象不可恢复。
   *
   * @param owner
   *     待彻底删除的实体对象的所有者。注意如果{@code owner.id}为{@code null}，则会彻底
   *     删除所有属于{@code owner.type}类型所有者的实体对象。
   * @return
   *     此操作实际删除的对象的数目。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see ClearableMapper#clear()
   * @see DeletableMapper#delete(Long, Instant)
   * @see DeletableByCodeMapper#deleteByCode(String, Instant)
   * @see DeletableMapper#restore(Long)
   * @see DeletableByCodeMapper#restoreByCode(String)
   * @see DeletableMapper#purge(Long)
   * @see DeletableByCodeMapper#purgeByCode(String)
   * @see DeletableMapper#purgeAll()
   */
  default long eraseForOwner(final Owner owner) throws DataAccessException {
    return eraseForOwnerImpl(this, owner);
  }
}
