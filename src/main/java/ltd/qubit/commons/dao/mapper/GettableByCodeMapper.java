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

/**
 * 此接口表示对拥有{@code code}属性的实体类实现了查询操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>此实体对象的编码必须全局唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #existCode(String)}</li>
 * <li>{@link #getByCode(String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface GettableByCodeMapper<T extends WithCode> extends Mapper<T> {

  /**
   * 判定拥有指定编码的对象是否存在。
   *
   * @param code
   *     指定的编码。
   * @return
   *     若拥有指定编码的对象存在，则返回{@code true}；否则返回{@code false}。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  boolean existCode(@Param("code") String code) throws DataAccessException;

  /**
   * 根据编码获取指定的对象。
   *
   * @param code
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  T getByCode(@Param("code") String code) throws DataAccessException;

  /**
   * 根据编码获取指定的对象的ID。
   *
   * @param code
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的ID，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  Long getIdByCode(@Param("code") String code) throws DataAccessException;
}
