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

import ltd.qubit.commons.model.Auditable;
import ltd.qubit.commons.model.HasInfoWithEntity;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.InfoWithEntity;
import ltd.qubit.commons.model.WithEntity;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;

/**
 * 此接口表示实现了对实体类进行列表、查询、查询基本信息、添加、更新、标记删除、恢复删除、
 * 彻底删除、清空数据库等操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>对象的名称在其所属实体类下必须唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #existName(String, String)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getByName(String, String)}</li>
 * <li>{@link #getIdByName(String, String)} </li>
 * <li>{@link #getInfo(Long)}，此函数返回一个{@link InfoWithEntity}对象</li>
 * <li>{@link #getInfoByName(String, String)}，此函数返回一个{@link InfoWithEntity}对象</li>
 * <li>{@link #add(Identifiable)}</li>
 * <li>{@link #update(Identifiable)}</li>
 * <li>{@link #updateByName(WithEntity)} </li>
 * <li>{@link #delete(Long, Instant)}</li>
 * <li>{@link #deleteByName(String, String, Instant)}</li>
 * <li>{@link #restore(Long)}</li>
 * <li>{@link #restoreByName(String, String)}</li>
 * <li>{@link #purge(Long)}</li>
 * <li>{@link #purgeByName(String, String)}</li>
 * <li>{@link #purgeAll()}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #eraseByName(String, String)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface BasicHasInfoWithEntityByEntityNameMapper
    <T extends HasInfoWithEntity & Auditable>
    extends BasicHasInfoWithEntityMapper<T>, GettableByEntityNameMapper<T>,
    InfoWithEntityGettableByEntityNameMapper<T>, UpdatableByEntityNameMapper<T>,
    AddOrUpdatableByEntityNameMapper<T>, DeletableByEntityNameMapper<T>,
    ErasableByEntityNameMapper<T> {
  // empty
}
