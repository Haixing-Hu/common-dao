////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.mapper.GettableForOwnerMapper;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithOwner;
import ltd.qubit.model.commons.Owner;

import static ltd.qubit.commons.dao.DaoImplHelper.countForOwnerImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getForOwnerImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getIdForOwnerImpl;

/**
 * 此接口表示对具有ID属性的实体类实现了查询操作的DAO。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #countForOwner(Owner)}</li>
 * <li>{@link #getForOwner(Owner)} </li>
 * <li>{@link #getIdForOwner(Owner)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface GettableForOwnerDao<T extends Identifiable & WithOwner>
    extends Dao<T> {

  @Override
  GettableForOwnerMapper<T> getMapper();

  /**
   * 获取指定所有者所属的所有实体对象的数目。
   *
   * @param owner
   *     指定实体对象的所有者。
   * @return
   *     指定所有者所属的所有实体对象的数目。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  default long countForOwner(final Owner owner) throws DataAccessException {
    return countForOwnerImpl(this, owner);
  }

  /**
   * 获取指定所有者所属的所有实体对象。
   *
   * @param owner
   *     指定实体对象的所有者。
   * @return
   *     指定所有者所属的所有实体对象的列表。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  default List<T> getForOwner(final Owner owner) throws DataAccessException {
    return getForOwnerImpl(this, owner);
  }

  /**
   * 获取指定所有者所属的所有实体对象的ID。
   *
   * @param owner
   *     指定实体对象的所有者。
   * @return
   *     指定所有者所属的所有实体对象的ID列表。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  default List<Long> getIdForOwner(final Owner owner) throws DataAccessException {
    return getIdForOwnerImpl(this, owner);
  }
}
