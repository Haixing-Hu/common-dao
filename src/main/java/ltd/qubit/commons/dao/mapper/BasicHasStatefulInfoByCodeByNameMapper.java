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
import ltd.qubit.commons.model.HasStatefulInfo;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.StatefulInfo;
import ltd.qubit.commons.model.WithCode;
import ltd.qubit.commons.model.WithName;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.commons.State;

/**
 * 此接口表示实现了对实体类进行列表、查询、查询基本信息、添加、更新、标记删除、恢复删除、
 * 彻底删除、清空数据库等操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>实体类对象的编码必须全局唯一，名称也必须全局唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #existCode(String)}</li>
 * <li>{@link #existName(String)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getByCode(String)}</li>
 * <li>{@link #getByName(String)}</li>
 * <li>{@link #getIdByCode(String)}</li>
 * <li>{@link #getIdByName(String)}</li>
 * <li>{@link #getInfo(Long)}，返回一个{@link StatefulInfo}对象</li>
 * <li>{@link #getInfoByCode(String)}，返回一个{@link StatefulInfo}对象</li>
 * <li>{@link #getInfoByName(String)}，返回一个{@link StatefulInfo}对象</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #update(Identifiable)}</li>
 * <li>{@link #updateByCode(WithCode)}</li>
 * <li>{@link #updateByName(WithName)}</li>
 * <li>{@link #updateState(Long, State, Instant)}</li>
 * <li>{@link #updateStateByCode(String, State, Instant)}</li>
 * <li>{@link #updateStateByName(String, State, Instant)}</li>
 * <li>{@link #delete(Long, Instant)}</li>
 * <li>{@link #deleteByCode(String, Instant)}</li>
 * <li>{@link #deleteByName(String, Instant)}</li>
 * <li>{@link #restore(Long)}</li>
 * <li>{@link #restoreByCode(String)}</li>
 * <li>{@link #restoreByName(String)}</li>
 * <li>{@link #purge(Long)}</li>
 * <li>{@link #purgeByCode(String)}</li>
 * <li>{@link #purgeByName(String)}</li>
 * <li>{@link #purgeAll()}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #eraseByCode(String)}</li>
 * <li>{@link #eraseByName(String)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface BasicHasStatefulInfoByCodeByNameMapper<T extends HasStatefulInfo & Auditable>
    extends BasicStatefulByCodeByNameMapper<T>,
    StatefulInfoGettableMapper<T>,
    StatefulInfoGettableByCodeMapper<T>,
    StatefulInfoGettableByNameMapper<T> {
  // empty
}
