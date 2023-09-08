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

import ltd.qubit.commons.dao.mapper.StatefulInfoGettableMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.HasStatefulInfo;
import ltd.qubit.commons.model.StatefulInfo;

import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有带状态的{@code info}属性的实体类实现了查询操作的DAO。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #getInfo(Long)}，此函数返回一个{@link StatefulInfo}对象</li>
 * <li>{@link #getInfoOrNull(Long)}，此函数返回一个{@link StatefulInfo}对象或{@code null}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface StatefulInfoGettableDao<T extends HasStatefulInfo>
    extends InfoGettableDao<T> {

  @Override
  StatefulInfoGettableMapper<T> getMapper();

  /**
   * 根据ID获取指定的对象的基本信息。
   *
   * @param id
   *     指定的对象的ID。
   * @return
   *     具有指定ID的对象的基本信息。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default StatefulInfo getInfo(final Long id) throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> getMapper().getInfo(id),
        "info", "id", id);
  }

  /**
   * 根据ID获取指定的对象的基本信息。
   *
   * @param id
   *     指定的对象的ID。
   * @return
   *     具有指定ID的对象的基本信息，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default StatefulInfo getInfoOrNull(final Long id) throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getInfo(id),
        "info", "id", id);
  }
}
