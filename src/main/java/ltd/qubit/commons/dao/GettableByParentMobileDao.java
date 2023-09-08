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

import ltd.qubit.commons.dao.mapper.GettableByParentMobileMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.WithMobile;
import ltd.qubit.commons.util.pair.KeyValuePair;
import ltd.qubit.model.contact.Phone;

/**
 * 此接口表示对拥有所属父属性下唯一手机号码的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>对象的手机号码在其所属父对象下必须唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #existMobile(String, Phone)}</li>
 * <li>{@link #getByMobile(String, Phone)}</li>
 * <li>{@link #getByMobileOrNull(String, Phone)}</li>
 * <li>{@link #getIdByMobile(String, Phone)}</li>
 * <li>{@link #getIdByMobileOrNull(String, Phone)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface GettableByParentMobileDao<T extends WithMobile> extends Dao<T> {

  @Override
  GettableByParentMobileMapper<T> getMapper();

  /**
   * 判定拥有指定编码的对象是否存在。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param mobile
   *     指定的编码。
   * @return
   *     若拥有指定编码的对象存在，则返回{@code true}；否则返回{@code false}。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  default boolean existMobile(final String parentCode, final Phone mobile)
      throws DataAccessException {
    return DaoImplHelper.existKeyImpl(this, () -> getMapper().existMobile(parentCode, mobile),
        "mobile", mobile, new KeyValuePair("parentCode", parentCode));
  }

  /**
   * 根据编码获取指定的对象。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param mobile
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default T getByMobile(final String parentCode, final Phone mobile)
      throws DataAccessException {
    return DaoImplHelper.getByKeyImpl(this, () -> getMapper().getByMobile(parentCode, mobile),
        "mobile", mobile, new KeyValuePair("parentCode", parentCode));
  }

  /**
   * 根据编码获取指定的对象。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param mobile
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default T getByMobileOrNull(final String parentCode, final Phone mobile)
      throws DataAccessException {
    return DaoImplHelper.getByKeyOrNullImpl(this,
        () -> getMapper().getByMobile(parentCode, mobile),
        "mobile", mobile, new KeyValuePair("parentCode", parentCode));
  }

  /**
   * 根据编码获取指定的对象的ID。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param mobile
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的ID。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default Long getIdByMobile(final String parentCode, final Phone mobile)
      throws DataAccessException {
    return DaoImplHelper.getPropertyByKeyImpl(this,
        () -> getMapper().getIdByMobile(parentCode, mobile),
        "id", "mobile", mobile, new KeyValuePair("parentCode", parentCode));
  }

  /**
   * 根据编码获取指定的对象的ID。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param mobile
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的ID，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Long getIdByMobileOrNull(final String parentCode, final Phone mobile)
      throws DataAccessException {
    return DaoImplHelper.getPropertyByKeyOrNullImpl(this,
        () -> getMapper().getIdByMobile(parentCode, mobile),
        "id", "mobile", mobile, new KeyValuePair("parentCode", parentCode));
  }
}
