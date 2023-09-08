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

import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.Info;

/**
 * 此接口表示实现了对{@link Info}类进行查询、添加、更新、删除等基本操作的MyBatis Mapper。
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #existCode(String)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getByCode(String)}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #update(Identifiable)}</li>
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
public interface InfoMapper<T extends Info> extends GettableMapper<T>,
    GettableByCodeMapper<T>, AddableMapper<T>, UpdatableMapper<T>,
    DeletableMapper<T>, ErasableMapper<T>, ClearableMapper<T> {
  //  empty
}
