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

import jakarta.validation.constraints.NotNull;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.mapper.GettableByCredentialMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.WithCredential;
import ltd.qubit.model.commons.CredentialInfo;

import static ltd.qubit.commons.dao.DaoImplHelper.existKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyOrNullImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有{@code credential}属性的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>对象的证件必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #existCredential(CredentialInfo)}</li>
 * <li>{@link #getByCredential(CredentialInfo)}</li>
 * <li>{@link #getByCredentialOrNull(CredentialInfo)}</li>
 * <li>{@link #getIdByCredential(CredentialInfo)}</li>
 * <li>{@link #getIdByCredentialOrNull(CredentialInfo)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface GettableByCredentialDao<T extends WithCredential>
    extends Dao<T> {

  @Override
  GettableByCredentialMapper<T> getMapper();

  /**
   * 判定拥有指定证件的对象是否存在。
   *
   * @param credential
   *     指定的证件。
   * @return
   *     若拥有指定证件的对象存在，则返回{@code true}；否则返回{@code false}。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  default boolean existCredential(final CredentialInfo credential)
      throws DataAccessException {
    return existKeyImpl(this, () -> getMapper().existCredential(credential),
        "credential", credential);
  }

  /**
   * 根据证件获取指定的对象。
   *
   * @param credential
   *     指定的对象的证件。
   * @return
   *     具有指定证件的对象。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default T getByCredential(final CredentialInfo credential)
      throws DataAccessException {
    return getByKeyImpl(this, () -> getMapper().getByCredential(credential),
        "credential", credential);
  }

  /**
   * 根据证件获取指定的对象。
   *
   * @param credential
   *     指定的对象的证件。
   * @return
   *     具有指定证件的对象，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default T getByCredentialOrNull(final CredentialInfo credential)
      throws DataAccessException {
    return getByKeyOrNullImpl(this, () -> getMapper().getByCredential(credential),
         "credential", credential);
  }

  /**
   * 根据证件获取指定的对象的ID。
   *
   * @param credential
   *     指定的对象的证件。
   * @return
   *     具有指定证件的对象的ID。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default Long getIdByCredential(final CredentialInfo credential)
      throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> getMapper().getIdByCredential(credential),
        "id", "credential", credential);
  }

  /**
   * 根据证件获取指定的对象的ID。
   *
   * @param credential
   *     指定的对象的证件。
   * @return
   *     具有指定证件的对象的ID，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Long getIdByCredentialOrNull(final CredentialInfo credential)
      throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getIdByCredential(credential),
        "id", "credential", credential);
  }
}
