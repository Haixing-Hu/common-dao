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

import ltd.qubit.commons.dao.mapper.InfoGettableByParentNameMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.HasInfo;
import ltd.qubit.commons.model.Info;
import ltd.qubit.commons.util.pair.KeyValuePair;

import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有拥有{@code info}属性和父对象下唯一名称的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>对象的名称在其所属父对象下必须唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #getInfoByName(String, String)}</li>
 * <li>{@link #getInfoByNameOrNull(String, String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型，注意其名称应该能唯一确定其实体。
 */
public interface InfoGettableByParentNameDao<T extends HasInfo> extends Dao<T> {

  @Override
  InfoGettableByParentNameMapper<T> getMapper();

  /**
   * 根据名称获取指定的对象的基本信息。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param name
   *     指定的对象的名称。
   * @return
   *     具有指定名称的对象的基本信息。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default Info getInfoByName(final String parentCode, final String name)
      throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> getMapper().getInfoByName(parentCode, name),
        "info", "name", name, new KeyValuePair("parentCode", parentCode));
  }

  /**
   * 根据名称获取指定的对象的基本信息。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param name
   *     指定的对象的名称。
   * @return
   *     具有指定名称的对象的基本信息，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Info getInfoByNameOrNull(final String parentCode, final String name)
      throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getInfoByName(parentCode, name),
        "info", "name", name, new KeyValuePair("parentCode", parentCode));
  }
}
