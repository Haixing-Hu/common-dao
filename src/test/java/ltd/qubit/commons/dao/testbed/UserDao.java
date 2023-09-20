////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed;

import java.time.Instant;

import javax.annotation.Nullable;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.annotation.Modified;
import ltd.qubit.commons.dao.testbed.mapper.UserMapper;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.commons.AuthorizeRecord;
import ltd.qubit.model.commons.State;
import ltd.qubit.model.contact.Phone;
import ltd.qubit.model.person.User;
import ltd.qubit.model.person.UserInfo;

/**
 * 对{@link User}对象进行数据库存取的DAO接口。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #existUsername(String)}
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getOrNull(Long)}</li>
 * <li>{@link #getByUsername(String)}</li>
 * <li>{@link #getByUsernameOrNull(String)}</li>
 * <li>{@link #getIdByUsername(String)}</li>
 * <li>{@link #getIdByUsernameOrNull(String)}</li>
 * <li>{@link #add(User)}</li>
 * <li>{@link #update(User)}</li>
 * <li>{@link #updateState(Long, State)}</li>
 * <li>{@link #updatePassword(Long, String)}</li>
 * <li>{@link #updateEmail(Long, String)}</li>
 * <li>{@link #updateMobile(Long, Phone)}</li>
 * <li>{@link #updateAvatar(Long, String)}</li>
 * <li>{@link #updateComment(Long, String)}</li>
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
public interface UserDao extends BasicStatefulDao<User>, GettableByUsernameDao<User>,
    GettableByMobileDao<User>, GettableByEmailDao<User>, PasswordUpdatableDao<User>,
    MobileUpdatableDao<User>, EmailUpdatableDao<User>, CommentUpdatableDao<User> {

  UserMapper getMapper();

  UserInfo getInfo(Long id) throws DataAccessException;

  @Nullable
  UserInfo getInfoOrNull(Long id) throws DataAccessException;

  UserInfo getInfoByUsername(String username) throws DataAccessException;

  @Nullable
  UserInfo getInfoByUsernameOrNull(String username) throws DataAccessException;

  UserInfo getInfoByMobile(Phone mobile) throws DataAccessException;

  @Nullable
  UserInfo getInfoByMobileOrNull(Phone mobile) throws DataAccessException;

  UserInfo getInfoByEmail(String email) throws DataAccessException;

  @Nullable
  UserInfo getInfoByEmailOrNull(String email) throws DataAccessException;

  @Modified({"id", "lastLogin", "createTime", "modifyTime", "deleteTime"})
  Instant add(User user) throws DataAccessException;

  @Modified({"name", "nickname", "gender", "avatar", "url", "description", "modifyTime"})
  Instant update(User user) throws DataAccessException;

  @Modified({"name", "nickname", "gender", "avatar", "url", "description", "modifyTime"})
  Instant updateByUsername(User user) throws DataAccessException;

  @Modified({"avatar", "modifyTime"})
  Instant updateAvatar(Long id, String avatar) throws DataAccessException;

  @Modified({"lastLogin", "modifyTime"})
  Instant updateLastLogin(Long id, AuthorizeRecord lastLogin)
      throws DataAccessException;

  @Modified({"changePassword", "modifyTime"})
  Instant updateChangePassword(Long id, Boolean changePassword)
      throws DataAccessException;

  @Modified({"name", "nickname", "avatar", "url", "description", "modifyTime"})
  Instant addOrUpdateByUsername(User user) throws DataAccessException;
}
