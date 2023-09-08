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

import ltd.qubit.commons.model.WithName;

/**
 * 此接口表示对拥有所属父对象下唯一名称的实体类实现了查询操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>对象的名称在其所属父对象下必须唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #existName(String, String)}</li>
 * <li>{@link #getByName(String, String)}</li>
 * <li>{@link #getIdByName(String, String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface GettableByParentNameMapper<T extends WithName> extends Mapper<T> {

  /**
   * 判定拥有指定名称的对象是否存在。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param name
   *     指定的名称。
   * @return
   *     若拥有指定名称的对象存在，则返回{@code true}；否则返回{@code false}。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  boolean existName(@Param("parentCode") String parentCode,
      @Param("name") String name) throws DataAccessException;

  /**
   * 根据名称获取指定的对象。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param name
   *     指定的对象的名称。
   * @return
   *     具有指定名称的对象，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  T getByName(@Param("parentCode") String parentCode,
      @Param("name") String name) throws DataAccessException;

  /**
   * 根据名称获取指定的对象的ID。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param name
   *     指定的对象的名称。
   * @return
   *     具有指定名称的对象的ID，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  Long getIdByName(@Param("parentCode") String parentCode,
      @Param("name") String name) throws DataAccessException;
}
