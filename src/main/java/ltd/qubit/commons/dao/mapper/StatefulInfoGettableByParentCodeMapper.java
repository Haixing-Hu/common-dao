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
 * 此接口表示对拥有带状态的{@code info}属性和全局唯一编码的实体类实现了查询操作的
 * MyBatis Mapper。
 *
 * <p><b>注意：</b>此实体对象的编码在其所属App下必须唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #getInfoByCode(String, String)}，此函数返回一个{@link StatefulInfo}对象</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface StatefulInfoGettableByParentCodeMapper<T extends HasStatefulInfo>
    extends InfoGettableByParentCodeMapper<T> {

  /**
   * 根据编码获取指定的对象的基本信息。
   *
   * @param parentCode
   *     指定的对象所属父对象的编码。
   * @param code
   *     指定的对象的编码。
   * @return
   *     具有指定编码的对象的基本信息，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  StatefulInfo getInfoByCode(@Param("parentCode") String parentCode,
      @Param("code") String code) throws DataAccessException;

}
