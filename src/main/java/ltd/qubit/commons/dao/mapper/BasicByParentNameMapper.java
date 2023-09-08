////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.mapper;

import java.time.Instant;

import ltd.qubit.commons.model.Deletable;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithName;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;

/**
 * 此接口表示实现了对实体类进行列表、查询、添加、更新、标记删除、恢复删除、彻底删除、清空数据库
 * 等操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>对象的名称必须全局唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #existName(String, String)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getByName(String, String)}</li>
 * <li>{@link #getIdByName(String, String)}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #update(Identifiable)}</li>
 * <li>{@link #updateByName(WithName)}</li>
 * <li>{@link #delete(Long, Instant)}</li>
 * <li>{@link #deleteByName(Long, String, Instant)}</li>
 * <li>{@link #restore(Long)}</li>
 * <li>{@link #restoreByName(Long, String)}</li>
 * <li>{@link #purge(Long)}</li>
 * <li>{@link #purgeByName(Long, String)}</li>
 * <li>{@link #purgeAll()}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #eraseByName(Long, String)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface BasicByParentNameMapper<T extends Identifiable & WithName & Deletable>
    extends BasicMapper<T>, ListableAddOrUpdatableByParentNameMapper<T>,
    DeletableByParentNameMapper<T>, ErasableByParentNameMapper<T> {
  //  empty
}
