////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.mapper;

import ltd.qubit.commons.model.util.Identifiable;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;

/**
 * 此接口表示实现了对实体进行列表、查询、添加、彻底删除、清空数据库等操作的MyBatis Mapper。
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface BasicAddableMapper<T extends Identifiable>
    extends ListableMapper<T>, GettableMapper<T>, AddableMapper<T>,
    ErasableMapper<T>, ClearableMapper<T> {
  // empty
}
