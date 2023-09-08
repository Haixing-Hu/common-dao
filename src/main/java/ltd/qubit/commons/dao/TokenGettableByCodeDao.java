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

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.mapper.TokenGettableByCodeMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.WithCode;
import ltd.qubit.commons.model.WithToken;
import ltd.qubit.model.commons.Token;

import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有{@code info}属性的实体类实现了查询操作的DAO。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #getTokenByCode(String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface TokenGettableByCodeDao<T extends WithCode & WithToken>
    extends Dao<T> {

  @Override
  TokenGettableByCodeMapper<T> getMapper();

  /**
   * 根据编码获取指定的对象的令牌。
   *
   * @param code
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的令牌，注意返回值可能为{@code null}。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Token getTokenByCode(final String code) throws DataAccessException {
    final Token token = getPropertyByKeyOrNullImpl(this,
        () -> getMapper().getTokenByCode(code), "token", "code", code);
    if (token == null) {
      // 注意token允许为null，因此我们需要额外判定到底是因为code不存在导致其为null还是
      // 其本身就是null
      if (! getMapper().existCode(code)) {
        throw new DataNotExistException(getEntityClass(), "code", code);
      }
    }
    return token;
  }

  /**
   * 根据编码获取指定的对象的令牌。
   *
   * @param code
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的令牌，注意返回值可能为{@code null}，要么表示指定的对象不存在，要么
   *     表示指定的对象的token就是{@code null}。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Token getTokenByCodeOrNull(final String code) throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getTokenByCode(code),
        "token", "code", code);
  }
}
