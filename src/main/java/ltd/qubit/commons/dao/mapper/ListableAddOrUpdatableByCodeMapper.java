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
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;

/**
 * 此接口表示实现了对实体类进行列表、根据编码查询，添加，根据编码更新，根据编码添加或更新等操作
 * 的MyBatis Mapper。
 *
 * <p><b>注意：</b>对象的编码必须全局唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #existCode(String)}</li>
 * <li>{@link #getByCode(String)}</li>
 * <li>{@link #getIdByCode(String)}</li>
 * <li>{@link #updateByCode(WithCode)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface ListableAddOrUpdatableByCodeMapper<T extends WithCode>
    extends ListableMapper<T>, AddOrUpdatableByCodeMapper<T> {
  //  empty
}
