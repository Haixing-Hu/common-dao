////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import ltd.qubit.commons.dao.mapper.BasicMapper;
import ltd.qubit.commons.model.util.Deletable;
import ltd.qubit.commons.model.util.Identifiable;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;

/**
 * 此接口表示实现了对实体类进行列表、查询、添加、更新、标记删除、恢复删除、彻底删除、
 * 清空数据库等操作的DAO。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #listFirst(Criterion, SortRequest)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getOrNull(Long)}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #update(T)}</li>
 * <li>{@link #delete(Long)}</li>
 * <li>{@link #restore(Long)}</li>
 * <li>{@link #purge(Long)}</li>
 * <li>{@link #purgeAll()}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface BasicDao<T extends Identifiable & Deletable>
    extends BasicUnupdatableDao<T>, UpdatableDao<T> {

  @Override
  BasicMapper<T> getMapper();

  // empty
}