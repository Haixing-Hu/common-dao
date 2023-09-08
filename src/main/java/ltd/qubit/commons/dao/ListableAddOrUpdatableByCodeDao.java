////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import ltd.qubit.commons.dao.mapper.ListableAddOrUpdatableByCodeMapper;
import ltd.qubit.commons.model.WithCode;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;

/**
 * 此接口表示实现了对实体类进行列表、根据编码查询，添加，根据编码更新，根据编码添加或更新
 * 等操作的 DAO。
 *
 * <p><b>注意：</b>对象的编码必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #existCode(String)}</li>
 * <li>{@link #getByCode(String)}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #updateByCode(WithCode)}</li>
 * <li>{@link #addOrUpdateByCode(WithCode)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface ListableAddOrUpdatableByCodeDao<T extends WithCode>
    extends ListableDao<T>, AddOrUpdatableByCodeDao<T> {

  @Override
  ListableAddOrUpdatableByCodeMapper<T> getMapper();

  // empty
}
