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

import ltd.qubit.commons.dao.mapper.GettableByParentEmailMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.WithEmail;
import ltd.qubit.commons.util.pair.KeyValuePair;

import static ltd.qubit.commons.dao.DaoImplHelper.existKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyOrNullImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有所属父属性下唯一电子邮件地址的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>对象的电子邮件地址在其所属父对象下必须唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #existEmail(String, String)}</li>
 * <li>{@link #getByEmail(String, String)}</li>
 * <li>{@link #getByEmailOrNull(String, String)}</li>
 * <li>{@link #getIdByEmail(String, String)}</li>
 * <li>{@link #getIdByEmailOrNull(String, String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface GettableByParentEmailDao<T extends WithEmail> extends Dao<T> {

  @Override
  GettableByParentEmailMapper<T> getMapper();

  /**
   * 判定拥有指定编码的对象是否存在。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param email
   *     指定的编码。
   * @return
   *     若拥有指定编码的对象存在，则返回{@code true}；否则返回{@code false}。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  default boolean existEmail(final String parentCode, final String email)
      throws DataAccessException {
    return existKeyImpl(this, () -> getMapper().existEmail(parentCode, email),
        "email", email, new KeyValuePair("parentCode", parentCode));
  }

  /**
   * 根据编码获取指定的对象。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param email
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default T getByEmail(final String parentCode, final String email)
      throws DataAccessException {
    return getByKeyImpl(this, () -> getMapper().getByEmail(parentCode, email),
        "email", email, new KeyValuePair("parentCode", parentCode));
  }

  /**
   * 根据编码获取指定的对象。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param email
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default T getByEmailOrNull(final String parentCode, final String email)
      throws DataAccessException {
    return getByKeyOrNullImpl(this,
        () -> getMapper().getByEmail(parentCode, email),
        "email", email, new KeyValuePair("parentCode", parentCode));
  }

  /**
   * 根据编码获取指定的对象的ID。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param email
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的ID。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default Long getIdByEmail(final String parentCode, final String email)
      throws DataAccessException {
    return getPropertyByKeyImpl(this,
        () -> getMapper().getIdByEmail(parentCode, email),
        "id", "email", email, new KeyValuePair("parentCode", parentCode));
  }

  /**
   * 根据编码获取指定的对象的ID。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param email
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的ID，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Long getIdByEmailOrNull(final String parentCode, final String email)
      throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this,
        () -> getMapper().getIdByEmail(parentCode, email),
        "id", "email", email, new KeyValuePair("parentCode", parentCode));
  }
}
