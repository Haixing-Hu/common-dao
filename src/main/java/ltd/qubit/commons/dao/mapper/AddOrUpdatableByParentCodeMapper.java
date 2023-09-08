////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.mapper;

import ltd.qubit.commons.model.WithCode;

/**
 * 此接口表示实现根据所属父对象及其编码添加或更新实体操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>此实体对象的编码在其所属父对象下必须唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #existCode(String, String)}</li>
 * <li>{@link #getByCode(String, String)}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #updateByCode(WithCode)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface AddOrUpdatableByParentCodeMapper<T extends WithCode>
    extends GettableByParentCodeMapper<T>, AddableMapper<T>,
    UpdatableByParentCodeMapper<T> {
  // empty
}
