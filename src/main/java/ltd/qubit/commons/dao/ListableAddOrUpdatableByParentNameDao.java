////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import ltd.qubit.commons.dao.mapper.ListableAddOrUpdatableByParentNameMapper;
import ltd.qubit.commons.model.WithName;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;

/**
 * 此接口表示实现了对实体类进行列表、根据名称查询，添加，根据名称更新，根据名称添加或更新
 * 等操作的 DAO。
 *
 * <p><b>注意：</b>对象的名称在其所属父对象下必须唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #existName(String, String)}</li>
 * <li>{@link #getByName(String, String)}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #updateByName(WithName)}</li>
 * <li>{@link #addOrUpdateByName(WithName)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface ListableAddOrUpdatableByParentNameDao<T extends WithName>
    extends ListableDao<T>, AddOrUpdatableByParentNameDao<T> {

  @Override
  ListableAddOrUpdatableByParentNameMapper<T> getMapper();

  // empty
}
