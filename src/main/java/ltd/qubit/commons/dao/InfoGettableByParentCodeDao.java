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

import ltd.qubit.commons.dao.mapper.InfoGettableByParentCodeMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.HasInfo;
import ltd.qubit.commons.model.Info;
import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * 此接口表示对拥有{@code info}属性和全局唯一编码的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>此实体对象的编码在其所属父对象下必须唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #getInfoByCode(String, String)}</li>
 * <li>{@link #getInfoByCodeOrNull(String, String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface InfoGettableByParentCodeDao<T extends HasInfo> extends Dao<T> {

  @Override
  InfoGettableByParentCodeMapper<T> getMapper();

  /**
   * 根据编码获取指定的对象的基本信息。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
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
  default Info getInfoByCode(final String parentCode, final String code)
      throws DataAccessException {
    return DaoImplHelper.getPropertyByKeyImpl(this,
        () -> getMapper().getInfoByCode(parentCode, code),
        "info", "code", code, new KeyValuePair("parentCode", parentCode));
  }

  /**
   * 根据编码获取指定的对象的基本信息。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param code
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的基本信息。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Info getInfoByCodeOrNull(final String parentCode, final String code)
      throws DataAccessException {
    return DaoImplHelper.getPropertyByKeyOrNullImpl(this,
        () -> getMapper().getInfoByCode(parentCode, code),
        "info", "code", code, new KeyValuePair("parentCode", parentCode));
  }

}
