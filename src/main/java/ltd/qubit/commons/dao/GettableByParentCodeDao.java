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

import ltd.qubit.commons.dao.mapper.GettableByParentCodeMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.WithCode;
import ltd.qubit.commons.util.pair.KeyValuePair;

import static ltd.qubit.commons.dao.DaoImplHelper.existKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyOrNullImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有所属父属性下唯一编码的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>对象的编码在其所属父对象下必须唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #existCode(String, String)}</li>
 * <li>{@link #getByCode(String, String)}</li>
 * <li>{@link #getByCodeOrNull(String, String)}</li>
 * <li>{@link #getIdByCode(String, String)}</li>
 * <li>{@link #getIdByCodeOrNull(String, String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface GettableByParentCodeDao<T extends WithCode> extends Dao<T> {

  @Override
  GettableByParentCodeMapper<T> getMapper();

  /**
   * 判定拥有指定编码的对象是否存在。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param code
   *     指定的编码。
   * @return
   *     若拥有指定编码的对象存在，则返回{@code true}；否则返回{@code false}。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  default boolean existCode(final String parentCode, final String code)
      throws DataAccessException {
    return existKeyImpl(this, () -> getMapper().existCode(parentCode, code),
        "code", code, new KeyValuePair("parentCode", parentCode));
  }

  /**
   * 根据编码获取指定的对象。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param code
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default T getByCode(final String parentCode, final String code)
      throws DataAccessException {
    return getByKeyImpl(this, () -> getMapper().getByCode(parentCode, code),
        "code", code, new KeyValuePair("parentCode", parentCode));
  }

  /**
   * 根据编码获取指定的对象。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param code
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default T getByCodeOrNull(final String parentCode, final String code)
      throws DataAccessException {
    return getByKeyOrNullImpl(this,
        () -> getMapper().getByCode(parentCode, code),
        "code", code, new KeyValuePair("parentCode", parentCode));
  }

  /**
   * 根据编码获取指定的对象的ID。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param code
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的ID。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default Long getIdByCode(final String parentCode, final String code)
      throws DataAccessException {
    return getPropertyByKeyImpl(this,
        () -> getMapper().getIdByCode(parentCode, code),
        "id", "code", code, new KeyValuePair("parentCode", parentCode));
  }

  /**
   * 根据编码获取指定的对象的ID。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param code
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的ID，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Long getIdByCodeOrNull(final String parentCode, final String code)
      throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this,
        () -> getMapper().getIdByCode(parentCode, code),
        "id", "code", code, new KeyValuePair("parentCode", parentCode));
  }
}
