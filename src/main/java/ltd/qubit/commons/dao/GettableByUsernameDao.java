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

import ltd.qubit.commons.dao.mapper.GettableByUsernameMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.WithUsername;

import static ltd.qubit.commons.dao.DaoImplHelper.existKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyOrNullImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有{@code username}属性的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>对象的用户名必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #existUsername(String)}</li>
 * <li>{@link #getByUsername(String)}</li>
 * <li>{@link #getByUsernameOrNull(String)}</li>
 * <li>{@link #getIdByUsername(String)}</li>
 * <li>{@link #getIdByUsernameOrNull(String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface GettableByUsernameDao<T extends WithUsername>
    extends Dao<T> {

  @Override
  GettableByUsernameMapper<T> getMapper();

  /**
   * 判定拥有指定用户名的对象是否存在。
   *
   * @param username
   *     指定的用户名。
   * @return
   *     若拥有指定用户名的对象存在，则返回{@code true}；否则返回{@code false}。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  default boolean existUsername(final String username) throws DataAccessException {
    return existKeyImpl(this, () -> getMapper().existUsername(username),
        "username", username);
  }

  /**
   * 根据用户名获取指定的对象。
   *
   * @param username
   *     指定的对象的用户名。
   * @return
   *     具有指定用户名的对象。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default T getByUsername(final String username) throws DataAccessException {
    return getByKeyImpl(this, () -> getMapper().getByUsername(username),
        "username", username);
  }

  /**
   * 根据用户名获取指定的对象。
   *
   * @param username
   *     指定的对象的用户名。
   * @return
   *     具有指定用户名的对象，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default T getByUsernameOrNull(final String username) throws DataAccessException {
    return getByKeyOrNullImpl(this, () -> getMapper().getByUsername(username),
         "username", username);
  }

  /**
   * 根据用户名获取指定的对象的ID。
   *
   * @param username
   *     指定的对象的用户名。
   * @return
   *     具有指定用户名的对象的ID。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default Long getIdByUsername(final String username) throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> getMapper().getIdByUsername(username),
        "id", "username", username);
  }

  /**
   * 根据用户名获取指定的对象的ID。
   *
   * @param username
   *     指定的对象的用户名。
   * @return
   *     具有指定用户名的对象的ID，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Long getIdByUsernameOrNull(final String username) throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getIdByUsername(username),
        "id", "username", username);
  }
}
