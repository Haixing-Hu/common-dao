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

import ltd.qubit.commons.dao.mapper.InfoWithEntityGettableByCodeMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.HasInfoWithEntity;
import ltd.qubit.commons.model.InfoWithEntity;

import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有{@code entity}和{@code info}属性的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>对象的编码必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #getInfoByCode(String)}，此函数返回一个{@link InfoWithEntity}对象</li>
 * <li>{@link #getInfoByCodeOrNull(String)}，此函数返回一个{@link InfoWithEntity}对象或{@code null}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface InfoWithEntityGettableByCodeDao<T extends HasInfoWithEntity>
    extends InfoGettableByCodeDao<T> {

  @Override
  InfoWithEntityGettableByCodeMapper<T> getMapper();

  /**
   * 根据编码获取指定的对象的基本信息。
   *
   * @param code
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的基本信息。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default InfoWithEntity getInfoByCode(final String code) throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> getMapper().getInfoByCode(code),
        "info", "code", code);
  }

  /**
   * 根据编码获取指定的对象的基本信息。
   *
   * @param code
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的基本信息，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default InfoWithEntity getInfoByCodeOrNull(final String code) throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getInfoByCode(code),
        "info", "code", code);
  }
}
