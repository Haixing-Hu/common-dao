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

import ltd.qubit.commons.model.HasInfo;
import ltd.qubit.commons.model.Info;
import ltd.qubit.commons.model.WithToken;
import ltd.qubit.model.commons.Token;

/**
 * 此接口表示对拥有{@code info}属性和全局唯一令牌值的实体类实现了查询操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>对象的令牌值必须全局唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #getInfoByToken(Token)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface InfoGettableByTokenMapper<T extends HasInfo & WithToken>
    extends Mapper<T> {

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
  Info getInfoByToken(@Param("token") Token token) throws DataAccessException;
}
