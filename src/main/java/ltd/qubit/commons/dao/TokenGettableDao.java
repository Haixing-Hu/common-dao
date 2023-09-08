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

import ltd.qubit.commons.dao.mapper.TokenGettableMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithToken;
import ltd.qubit.model.commons.Token;

import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有{@code info}属性的实体类实现了查询操作的DAO。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #getToken(Long)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface TokenGettableDao<T extends Identifiable & WithToken>
    extends Dao<T> {

  @Override
  TokenGettableMapper<T> getMapper();

  /**
   * 根据ID获取指定的对象的令牌。
   *
   * @param id
   *     指定的对象的ID。
   * @return
   *     具有指定ID的对象的令牌，注意返回值可能为{@code null}。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Token getToken(final Long id) throws DataAccessException {
    final Token token = getPropertyByKeyOrNullImpl(this,
        () -> getMapper().getToken(id), "token", "id", id);
    if (token == null) {
      // 注意token允许为null，因此我们需要额外判定到底是因为id不存在导致其为null还是
      // 其本身就是null
      if (! getMapper().exist(id)) {
        throw new DataNotExistException(getEntityClass(), "id", id);
      }
    }
    return token;
  }

  /**
   * 根据ID获取指定的对象的令牌。
   *
   * @param id
   *     指定的对象的ID。
   * @return
   *     具有指定ID的对象的令牌，注意返回值可能为{@code null}，要么表示指定的对象不存在，
   *     要么表示指定的对象的token就是{@code null}。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Token getTokenOrNull(final Long id) throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getToken(id),
        "token", "id", id);
  }
}
