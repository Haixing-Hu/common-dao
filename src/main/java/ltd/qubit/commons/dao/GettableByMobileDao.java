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

import ltd.qubit.commons.dao.mapper.GettableByMobileMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.WithMobile;
import ltd.qubit.model.contact.Phone;

import static ltd.qubit.commons.dao.DaoImplHelper.existKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyOrNullImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有{@code mobile}属性的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>对象的手机号码必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #existMobile(Phone)}</li>
 * <li>{@link #getByMobile(Phone)}</li>
 * <li>{@link #getByMobileOrNull(Phone)}</li>
 * <li>{@link #getIdByMobile(Phone)}</li>
 * <li>{@link #getIdByMobileOrNull(Phone)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface GettableByMobileDao<T extends WithMobile> extends Dao<T> {

  @Override
  GettableByMobileMapper<T> getMapper();

  /**
   * 判定拥有指定手机号码的对象是否存在。
   *
   * @param mobile
   *     指定的手机号码。
   * @return
   *     若拥有指定手机号码的对象存在，则返回{@code true}；否则返回{@code false}。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  default boolean existMobile(final Phone mobile) throws DataAccessException {
    return existKeyImpl(this, () -> getMapper().existMobile(mobile),
        "mobile", mobile);
  }

  /**
   * 根据手机号码获取指定的对象。
   *
   * @param mobile
   *     指定的对象的手机号码。
   * @return
   *     具有指定手机号码的对象。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default T getByMobile(final Phone mobile) throws DataAccessException {
    return getByKeyImpl(this, () -> getMapper().getByMobile(mobile),
        "mobile", mobile);
  }

  /**
   * 根据手机号码获取指定的对象。
   *
   * @param mobile
   *     指定的对象的手机号码。
   * @return
   *     具有指定手机号码的对象，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default T getByMobileOrNull(final Phone mobile) throws DataAccessException {
    return getByKeyOrNullImpl(this, () -> getMapper().getByMobile(mobile),
         "mobile", mobile);
  }

  /**
   * 根据手机号码获取指定的对象的ID。
   *
   * @param mobile
   *     指定的对象的手机号码。
   * @return
   *     具有指定手机号码的对象的ID。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default Long getIdByMobile(final Phone mobile) throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> getMapper().getIdByMobile(mobile),
        "id", "mobile", mobile);
  }

  /**
   * 根据手机号码获取指定的对象的ID。
   *
   * @param mobile
   *     指定的对象的手机号码。
   * @return
   *     具有指定手机号码的对象的ID，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Long getIdByMobileOrNull(final Phone mobile) throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getIdByMobile(mobile),
        "id", "mobile", mobile);
  }
}
