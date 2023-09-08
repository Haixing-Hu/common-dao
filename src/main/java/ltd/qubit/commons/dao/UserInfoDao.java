////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import ltd.qubit.commons.dao.mapper.UserInfoMapper;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.model.person.UserInfo;

/**
 * 此接口表示实现了对{@link UserInfo}类进行查询、添加、更新、删除等基本操作的DAO。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #existUsername(String)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getByUsername(String)}</li>
 * <li>{@link #add(Identifiable)}</li>
 * <li>{@link #update(Identifiable)}</li>
 * <li>{@link #delete(Long)}</li>
 * <li>{@link #restore(Long)}</li>
 * <li>{@link #purge(Long)}</li>
 * <li>{@link #purgeAll()}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @author 胡海星
 */
public interface UserInfoDao extends GettableDao<UserInfo>,
    GettableByUsernameDao<UserInfo>, AddableDao<UserInfo>,
    UpdatableDao<UserInfo>, DeletableDao<UserInfo>, ErasableDao<UserInfo> {

  @Override
  UserInfoMapper getMapper();

  //  empty
}
