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

import ltd.qubit.commons.model.WithEmail;

/**
 * 此接口表示对拥有{@code email}属性的实体类实现了查询操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>对象的电子邮件地址必须全局唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #existEmail(String)}</li>
 * <li>{@link #getByEmail(String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface GettableByEmailMapper<T extends WithEmail>
    extends Mapper<T> {

  /**
   * 判定拥有指定电子邮件地址的对象是否存在。
   *
   * @param email
   *     指定的电子邮件地址。
   * @return
   *     若拥有指定电子邮件地址的对象存在，则返回{@code true}；否则返回{@code false}。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  boolean existEmail(@Param("email") String email) throws DataAccessException;

  /**
   * 根据电子邮件地址获取指定的对象。
   *
   * @param email
   *     指定的对象的电子邮件地址。
   * @return
   *     具有指定电子邮件地址的对象，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  T getByEmail(@Param("email") String email) throws DataAccessException;

  /**
   * 根据电子邮件地址获取指定的对象的ID。
   *
   * @param email
   *     指定的对象的电子邮件地址。
   * @return
   *     具有指定电子邮件地址的对象的ID，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  Long getIdByEmail(@Param("email") String email) throws DataAccessException;
}
