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

import ltd.qubit.commons.dao.mapper.StatefulInfoGettableByNameMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.HasStatefulInfo;
import ltd.qubit.commons.model.StatefulInfo;

import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有带状态的{@code info}属性和全局唯一名称的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>对象的名称必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #getInfoByName(String)}，此函数返回一个{@link StatefulInfo}对象</li>
 * <li>{@link #getInfoByNameOrNull(String)}，此函数返回一个{@link StatefulInfo}对象或{@code null}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型，注意其名称应该能唯一确定其实体。
 */
public interface StatefulInfoGettableByNameDao<T extends HasStatefulInfo>
    extends InfoGettableByNameDao<T> {

  @Override
  StatefulInfoGettableByNameMapper<T> getMapper();

  /**
   * 根据名称获取指定的对象的基本信息。
   *
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
  default StatefulInfo getInfoByName(final String name) throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> getMapper().getInfoByName(name),
        "info", "name", name);
  }

  /**
   * 根据名称获取指定的对象的基本信息。
   *
   * @param name
   *     指定的对象的名称。
   * @return
   *     具有指定名称的对象的基本信息，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default StatefulInfo getInfoByNameOrNull(final String name) throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getInfoByName(name),
        "info", "name", name);
  }
}
