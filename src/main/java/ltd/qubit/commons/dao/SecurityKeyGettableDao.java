////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import javax.annotation.Nullable;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.mapper.SecurityKeyGettableMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithSecurityKey;

import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有安全密钥属性的实体类实现了查询操作的DAO。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #getSecurityKey(Long)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface SecurityKeyGettableDao<T extends Identifiable & WithSecurityKey>
    extends Dao<T> {

  @Override
  SecurityKeyGettableMapper<T> getMapper();

  /**
   * 根据ID获取指定的对象的安全密钥。
   *
   * @param id
   *     指定的对象的ID。
   * @return
   *     具有指定ID的对象的安全密钥。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default String getSecurityKey(final Long id) throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> getMapper().getSecurityKey(id),
        "securityKey", "id", id);
  }

  /**
   * 根据ID获取指定的对象的安全密钥。
   *
   * @param id
   *     指定的对象的ID。
   * @return
   *     具有指定ID的对象的安全密钥，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default String getSecurityKeyOrNull(final Long id) throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getSecurityKey(id),
        "securityKey", "id", id);
  }
}
