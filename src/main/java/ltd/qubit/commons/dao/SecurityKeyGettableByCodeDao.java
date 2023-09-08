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

import ltd.qubit.commons.dao.mapper.SecurityKeyGettableByCodeMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.WithCode;
import ltd.qubit.commons.model.WithSecurityKey;

import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有安全密钥属性的实体类实现了查询操作的DAO。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #getSecurityKeyByCode(String)}</li>
 * <li>{@link #getSecurityKeyByCodeOrNull(String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface SecurityKeyGettableByCodeDao<T extends WithCode & WithSecurityKey>
    extends Dao<T> {

  @Override
  SecurityKeyGettableByCodeMapper<T> getMapper();

  /**
   * 根据编码获取指定的对象的令牌。
   *
   * @param code
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的安全密钥。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default String getSecurityKeyByCode(final String code)
      throws DataAccessException {
    return getPropertyByKeyImpl(this,
        () -> getMapper().getSecurityKeyByCode(code),
        "securityKey", "code", code);
  }

  /**
   * 根据编码获取指定的对象的令牌。
   *
   * @param code
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的安全密钥，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default String getSecurityKeyByCodeOrNull(final String code)
      throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this,
        () -> getMapper().getSecurityKeyByCode(code),
        "securityKey", "code", code);
  }
}
