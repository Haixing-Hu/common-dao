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

import ltd.qubit.commons.model.Creatable;
import ltd.qubit.commons.model.Deletable;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.Stateful;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.commons.State;

/**
 * 此接口表示实现了对实体进行列表、查询、添加、更新状态、标记删除、恢复删除、彻底删除、清空数据
 * 库等操作的MyBatis Mapper。
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #add(Identifiable)}</li>
 * <li>{@link #updateState(Long, State, Instant)}</li>
 * <li>{@link #delete(Long, Instant)}</li>
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
public interface BasicUnupdatableStatefulMapper
    <T extends Identifiable & Stateful & Creatable & Deletable>
    extends BasicUnupdatableMapper<T>, StateUpdatableMapper<T> {

}