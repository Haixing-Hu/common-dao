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

import ltd.qubit.commons.model.HasStatefulInfo;
import ltd.qubit.commons.model.StatefulInfo;

/**
 * 此接口表示对拥有{@code info}属性和全局唯一名称的实体类实现了查询操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>对象的名称在其所属父对象下必须唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #getInfoByName(String, String)}，此函数返回一个{@link StatefulInfo}对象</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface StatefulInfoGettableByParentNameMapper<T extends HasStatefulInfo>
    extends InfoGettableByParentNameMapper<T> {

  /**
   * 根据名称获取指定的对象的基本信息。
   *
   * @param parentCode
   *     指定的对象所属的父对象的编码。
   * @param name
   *     指定的对象的名称。
   * @return
   *     具有指定名称的对象的基本信息，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  StatefulInfo getInfoByName(@Param("parentCode") String parentCode,
      @Param("name") String name) throws DataAccessException;
}
