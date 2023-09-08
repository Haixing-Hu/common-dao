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

import ltd.qubit.commons.dao.mapper.GettableByNameMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.WithName;

import static ltd.qubit.commons.dao.DaoImplHelper.existKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyOrNullImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有{@code name}属性的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>对象的名称必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #existName(String)}</li>
 * <li>{@link #getByName(String)}</li>
 * <li>{@link #getByNameOrNull(String)}</li>
 * <li>{@link #getIdByName(String)}</li>
 * <li>{@link #getIdByNameOrNull(String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface GettableByNameDao<T extends WithName> extends Dao<T> {

  @Override
  GettableByNameMapper<T> getMapper();

  /**
   * 判定拥有指定名称的对象是否存在。
   *
   * @param name
   *     指定的名称。
   * @return
   *     若拥有指定名称的对象存在，则返回{@code true}；否则返回{@code false}。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  default boolean existName(final String name) throws DataAccessException {
    return existKeyImpl(this, () -> getMapper().existName(name), "name", name);
  }

  /**
   * 根据名称获取指定的对象。
   *
   * @param name
   *     指定的对象的名称。
   * @return
   *     具有指定名称的对象。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default T getByName(final String name) throws DataAccessException {
    return getByKeyImpl(this, () -> getMapper().getByName(name), "name", name);
  }

  /**
   * 根据名称获取指定的对象。
   *
   * @param name
   *     指定的对象的名称。
   * @return
   *     具有指定名称的对象，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default T getByNameOrNull(final String name) throws DataAccessException {
    return getByKeyOrNullImpl(this, () -> getMapper().getByName(name), "name", name);
  }

  /**
   * 根据名称获取指定的对象的ID。
   *
   * @param name
   *     指定的对象的名称。
   * @return
   *     具有指定名称的对象的ID。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default Long getIdByName(final String name) throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> getMapper().getIdByName(name),
        "id", "name", name);
  }

  /**
   * 根据名称获取指定的对象的ID。
   *
   * @param name
   *     指定的对象的名称。
   * @return
   *     具有指定名称的对象的ID，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Long getIdByNameOrNull(final String name) throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getIdByName(name),
        "id", "name", name);
  }
}
