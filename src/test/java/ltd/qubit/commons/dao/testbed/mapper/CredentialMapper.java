////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed.mapper;

import java.time.Instant;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.mapper.BasicWithOwnerMapper;
import ltd.qubit.commons.dao.testbed.CredentialDao;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithOwner;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.commons.Credential;
import ltd.qubit.model.commons.CredentialInfo;
import ltd.qubit.model.commons.Owner;

/**
 * 实现{@link CredentialDao}的MyBatis Mapper。
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #countForOwner(Owner)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #getForOwner(Owner)}</li>
 * <li>{@link #getIdForOwner(Owner)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getInfo(Long)}，此函数返回一个{@link CredentialInfo}对象</li>
 * <li>{@link #add(Identifiable)}</li>
 * <li>{@link #update(Identifiable)}</li>
 * <li>{@link #updateByOwner(WithOwner)}</li>
 * <li>{@link #delete(Long, Instant)}</li>
 * <li>{@link #deleteForOwner(Owner, Instant)}</li>
 * <li>{@link #restore(Long)}</li>
 * <li>{@link #restoreForOwner(Owner)} </li>
 * <li>{@link #purge(Long)}</li>
 * <li>{@link #purgeForOwner(Owner)} </li>
 * <li>{@link #purgeAll()}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #eraseForOwner(Owner)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @author 胡海星
 */
public interface CredentialMapper extends BasicWithOwnerMapper<Credential>,
    UpdatableByOwnerMapper<Credential>, ModifyTimeUpdatableMapper<Credential> {

  @Nullable
  Credential getByIndex(@Param("owner") Owner owner, @Param("index") int index)
      throws DataAccessException;

  /**
   * 根据ID获取指定的{@link Credential}对象的基本信息。
   *
   * @param id
   *    指定的{@link Credential}对象的ID。
   * @return
   *    具有指定ID的{@link Credential}对象的基本信息，或{@code null}若指定的对象不
   *    存在。
   * @throws DataAccessException
   *    若出现其他数据存取错误。
   */
  @Nullable
  CredentialInfo getInfo(@Param("id") Long id) throws DataAccessException;

  /**
   * 获取指定所有者的全部{@link Credential}对象的基本信息。
   *
   * @param owner
   *    指定的{@link Credential}对象的所有者。
   * @return
   *    指定所有者所拥有的所有{@link Credential}对象的基本信息列表。
   * @throws DataAccessException
   *    若出现其他数据存取错误。
   */
  List<CredentialInfo> getInfoForOwner(@Param("owner") Owner owner)
      throws DataAccessException;
}
