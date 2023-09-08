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
 * 此接口表示实现根据所属App及其编码添加或更新实体操作的MyBatis Mapper。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #existCode(String)}</li>
 * <li>{@link #getByCode(String)}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #updateByCode(WithCode)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface AddOrUpdatableByCodeMapper<T extends WithCode>
    extends GettableByCodeMapper<T>, AddableMapper<T>, UpdatableByCodeMapper<T> {
  // empty
}
