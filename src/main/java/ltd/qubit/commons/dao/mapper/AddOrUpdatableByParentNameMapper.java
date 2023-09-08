////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.mapper;

import ltd.qubit.commons.model.WithName;

/**
 * 此接口表示实现根据名称添加或更新实体操作的MyBatis Mapper。
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #existName(String, String)}</li>
 * <li>{@link #getByName(String, String)}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #updateByName(WithName)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface AddOrUpdatableByParentNameMapper<T extends WithName>
    extends GettableByParentNameMapper<T>, AddableMapper<T>, UpdatableByParentNameMapper<T> {
  // empty
}
