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

import ltd.qubit.commons.dao.mapper.InfoGettableByTokenMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.HasInfo;
import ltd.qubit.commons.model.Info;
import ltd.qubit.commons.model.WithToken;
import ltd.qubit.model.commons.Token;

import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有拥有{@code info}属性和全局唯一令牌的值的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>对象的令牌的值必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #getInfoByToken(Token)}</li>
 * <li>{@link #getInfoByTokenOrNull(Token)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型，注意其令牌的值应该能唯一确定其实体。
 */
public interface InfoGettableByTokenDao<T extends HasInfo & WithToken> extends Dao<T> {

  @Override
  InfoGettableByTokenMapper<T> getMapper();

  /**
   * 根据令牌的值获取指定的对象的基本信息。
   *
   * @param token
   *     指定的对象的令牌，注意只有{@code token.value}起作用。
   * @return
   *     具有指定令牌的值的对象的基本信息。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default Info getInfoByToken(final Token token) throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> getMapper().getInfoByToken(token),
        "info", "token", token);
  }

  /**
   * 根据令牌的值获取指定的对象的基本信息。
   *
   * @param token
   *     指定的对象的令牌，注意只有{@code token.value}起作用。
   * @return
   *     具有指定令牌的值的对象的基本信息，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Info getInfoByTokenOrNull(final Token token) throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getInfoByToken(token),
        "info", "token", token);
  }
}
