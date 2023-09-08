////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.mapper;

import javax.annotation.Nullable;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.model.WithCode;
import ltd.qubit.commons.model.WithToken;
import ltd.qubit.model.commons.Token;

/**
 * 此接口表示对拥有{@code token}属性和全局唯一编码的实体类实现了查询操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>对象的编码必须全局唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #getTokenByCode(String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface TokenGettableByCodeMapper<T extends WithCode & WithToken>
    extends GettableByCodeMapper<T> {

  /**
   * 根据编码获取指定的对象的令牌。
   *
   * @param code
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的令牌，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  Token getTokenByCode(@Param("code") String code) throws DataAccessException;

}
