////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithOwner;
import ltd.qubit.model.commons.Owner;

/**
 * 此接口表示对具有ID属性的实体类实现了查询操作的MyBatis Mapper。
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #countForOwner(Owner)}</li>
 * <li>{@link #getForOwner(Owner)} </li>
 * <li>{@link #getIdForOwner(Owner)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型，该类型必须实现{@link Identifiable}和{@link WithOwner}接口。
 * @author 胡海星
 */
public interface GettableForOwnerMapper<T extends Identifiable & WithOwner>
    extends Mapper<T> {

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
  long countForOwner(@Param("owner") Owner owner)
      throws DataAccessException;

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
  List<T> getForOwner(@Param("owner") Owner owner)
      throws DataAccessException;

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
  List<Long> getIdForOwner(@Param("owner") Owner owner)
      throws DataAccessException;
}
