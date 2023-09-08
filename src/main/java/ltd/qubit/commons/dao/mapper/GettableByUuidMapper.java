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

import ltd.qubit.commons.model.WithUuid;

/**
 * 此接口表示对拥有{@code uuid}属性的实体类实现了查询操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>对象的UUID必须全局唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #existUuid(String)}</li>
 * <li>{@link #getByUuid(String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface GettableByUuidMapper<T extends WithUuid>
    extends Mapper<T> {

  /**
   * 判定拥有指定UUID的对象是否存在。
   *
   * @param uuid
   *     指定的UUID。
   * @return
   *     若拥有指定UUID的对象存在，则返回{@code true}；否则返回{@code false}。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  boolean existUuid(@Param("uuid") String uuid) throws DataAccessException;

  /**
   * 根据UUID获取指定的对象。
   *
   * @param uuid
   *     指定的对象的UUID。
   * @return
   *     具有指定UUID的对象，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  T getByUuid(@Param("uuid") String uuid) throws DataAccessException;

  /**
   * 根据UUID获取指定的对象的ID。
   *
   * @param uuid
   *     指定的对象的UUID。
   * @return
   *     具有指定UUID的对象的ID，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  Long getIdByUuid(@Param("uuid") String uuid) throws DataAccessException;
}
