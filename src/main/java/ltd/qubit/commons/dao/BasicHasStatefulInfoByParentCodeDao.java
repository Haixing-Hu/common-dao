////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import ltd.qubit.commons.dao.mapper.BasicHasStatefulInfoByParentCodeMapper;
import ltd.qubit.commons.model.Auditable;
import ltd.qubit.commons.model.HasStatefulInfo;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.StatefulInfo;
import ltd.qubit.commons.model.WithCode;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.commons.State;

/**
 * 此接口表示实现了对实体类进行列表、查询、查询基本信息、添加、更新、标记删除、恢复删除、
 * 彻底删除、清空数据库等操作的DAO。
 *
 * <p><b>注意：</b>对象的编码在其所属父对象下必须唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #existCode(String, String)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getOrNull(Long)}</li>
 * <li>{@link #getByCode(String, String)}</li>
 * <li>{@link #getByCodeOrNull(String, String)}</li>
 * <li>{@link #getIdByCode(String, String)}</li>
 * <li>{@link #getIdByCodeOrNull(String, String)}</li>
 * <li>{@link #getInfo(Long)}，此函数返回一个{@link StatefulInfo}对象</li>
 * <li>{@link #getInfoOrNull(Long)}，此函数返回一个{@link StatefulInfo}对象或{@code null}</li>
 * <li>{@link #getInfoByCode(String, String)}，此函数返回一个{@link StatefulInfo}对象</li>
 * <li>{@link #getInfoByCodeOrNull(String, String)}，此函数返回一个{@link StatefulInfo}对象或{@code null}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #update(Identifiable)}</li>
 * <li>{@link #updateByCode(WithCode)}</li>
 * <li>{@link #updateState(Long, State)}</li>
 * <li>{@link #updateStateByCode(Long, String, State)}</li>
 * <li>{@link #addOrUpdateByCode(WithCode)}</li>
 * <li>{@link #delete(Long)}</li>
 * <li>{@link #deleteByCode(Long, String)}</li>
 * <li>{@link #restore(Long)}</li>
 * <li>{@link #restoreByCode(Long, String)}</li>
 * <li>{@link #purge(Long)}</li>
 * <li>{@link #purgeByCode(Long, String)}</li>
 * <li>{@link #purgeAll()}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #eraseByCode(Long, String)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface BasicHasStatefulInfoByParentCodeDao
    <T extends HasStatefulInfo & Auditable>
    extends BasicStatefulByParentCodeDao<T>, StatefulInfoGettableDao<T>,
    StatefulInfoGettableByParentCodeDao<T> {

  @Override
  BasicHasStatefulInfoByParentCodeMapper<T> getMapper();

}
