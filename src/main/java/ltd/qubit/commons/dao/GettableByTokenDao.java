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

import ltd.qubit.commons.dao.mapper.GettableByTokenMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.WithToken;
import ltd.qubit.model.commons.Token;

import static ltd.qubit.commons.dao.DaoImplHelper.existKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyOrNullImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有{@code token}属性的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>对象的令牌的值必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #existToken(Token)}</li>
 * <li>{@link #getByToken(Token)}</li>
 * <li>{@link #getByTokenOrNull(Token)}</li>
 * <li>{@link #getIdByToken(Token)}</li>
 * <li>{@link #getIdByTokenOrNull(Token)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface GettableByTokenDao<T extends WithToken> extends Dao<T> {

  @Override
  GettableByTokenMapper<T> getMapper();

  /**
   * 判定拥有指定令牌的值的对象是否存在。
   *
   * @param token
   *     指定的令牌，注意只有{@code token.value}起作用。
   * @return
   *     若拥有指定令牌的值的对象存在，则返回{@code true}；否则返回{@code false}。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  default boolean existToken(final Token token) throws DataAccessException {
    return existKeyImpl(this, () -> getMapper().existToken(token),
        "token", token);
  }

  /**
   * 根据令牌的值获取指定的对象。
   *
   * @param token
   *     指定的对象的令牌，注意只有{@code token.value}起作用。
   * @return
   *     具有指定令牌的值的对象。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default T getByToken(final Token token) throws DataAccessException {
    return getByKeyImpl(this, () -> getMapper().getByToken(token),
        "token", token);
  }

  /**
   * 根据令牌的值获取指定的对象。
   *
   * @param token
   *     指定的对象的令牌，注意只有{@code token.value}起作用。
   * @return
   *     具有指定令牌的值的对象，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default T getByTokenOrNull(final Token token) throws DataAccessException {
    return getByKeyOrNullImpl(this, () -> getMapper().getByToken(token),
        "token", token);
  }

  /**
   * 根据令牌的值获取指定的对象的ID。
   *
   * @param token
   *     指定的对象的令牌，注意只有{@code token.value}起作用。
   * @return
   *     具有指定令牌的值的对象的ID。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default Long getIdByToken(final Token token) throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> getMapper().getIdByToken(token),
        "id", "token", token);
  }

  /**
   * 根据令牌的值获取指定的对象的ID。
   *
   * @param token
   *     指定的对象的令牌，注意只有{@code token.value}起作用。
   * @return
   *     具有指定令牌的值的对象的ID，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Long getIdByTokenOrNull(final Token token) throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getIdByToken(token),
        "id", "token", token);
  }
}
