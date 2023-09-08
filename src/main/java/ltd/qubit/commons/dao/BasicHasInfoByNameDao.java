////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import ltd.qubit.commons.dao.mapper.BasicHasInfoByNameMapper;
import ltd.qubit.commons.model.Auditable;
import ltd.qubit.commons.model.HasInfo;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithName;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;

/**
 * 此接口表示实现了对实体类进行列表、查询、查询基本信息、添加、更新、标记删除、恢复删除、
 * 彻底删除、清空数据库等操作的DAO。
 *
 * <p><b>注意：</b>实体类对象的名称必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #existName(String)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getOrNull(Long)}</li>
 * <li>{@link #getByName(String)}</li>
 * <li>{@link #getByNameOrNull(String)}</li>
 * <li>{@link #getIdByName(String)}</li>
 * <li>{@link #getIdByNameOrNull(String)}</li>
 * <li>{@link #getInfo(Long)}</li>
 * <li>{@link #getInfoOrNull(Long)}</li>
 * <li>{@link #getInfoByName(String)}</li>
 * <li>{@link #getInfoByNameOrNull(String)}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #update(Identifiable)}</li>
 * <li>{@link #updateByName(WithName)}</li>
 * <li>{@link #delete(Long)}</li>
 * <li>{@link #deleteByName(String)}</li>
 * <li>{@link #restore(Long)}</li>
 * <li>{@link #restoreByName(String)}</li>
 * <li>{@link #purge(Long)}</li>
 * <li>{@link #purgeByName(String)}</li>
 * <li>{@link #purgeAll()}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #eraseByName(String)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface BasicHasInfoByNameDao<T extends HasInfo & Auditable>
    extends BasicByNameDao<T>, InfoGettableDao<T>, InfoGettableByNameDao<T> {

  @Override
  BasicHasInfoByNameMapper<T> getMapper();

  // empty
}
