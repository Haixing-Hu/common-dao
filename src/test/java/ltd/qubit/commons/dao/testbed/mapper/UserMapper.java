////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed.mapper;

import java.time.Instant;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.testbed.UserDao;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.commons.AuthorizeRecord;
import ltd.qubit.model.commons.State;
import ltd.qubit.model.contact.Phone;
import ltd.qubit.model.person.User;
import ltd.qubit.model.person.UserInfo;

/**
 * 实现{@link UserDao}的MyBatis Mapper。
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #existUsername(String)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getByUsername(String)}</li>
 * <li>{@link #getIdByUsername(String)}</li>
 * <li>{@link #getInfo(Long)}</li>
 * <li>{@link #getInfoByUsername(String)}</li>
 * <li>{@link #getInfoByMobile(Phone)}</li>
 * <li>{@link #getInfoByEmail(String)}</li>
 * <li>{@link #add(Identifiable)}</li>
 * <li>{@link #update(Identifiable)}</li>
 * <li>{@link #updatePassword(Long, String, Instant)}</li>
 * <li>{@link #updateMobile(Long, Phone, Instant)}</li>
 * <li>{@link #updateEmail(Long, String, Instant)}</li>
 * <li>{@link #updateAvatar(Long, String, Instant)}</li>
 * <li>{@link #updateState(Long, State, Instant)}</li>
 * <li>{@link #updateComment(Long, String, Instant)}</li>
 * <li>{@link #delete(Long, Instant)}</li>
 * <li>{@link #restore(Long)}</li>
 * <li>{@link #purge(Long)}</li>
 * <li>{@link #purgeAll()}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @author 胡海星
 */
public interface UserMapper extends GettableByUsernameMapper<User>,
    GettableByMobileMapper<User>, GettableByEmailMapper<User>, PasswordUpdatableMapper<User>,
    MobileUpdatableMapper<User>, EmailUpdatableMapper<User>, CommentUpdatableMapper<User>,
    ltd.qubit.commons.dao.mapper.BasicMapper<User>,
    ltd.qubit.commons.dao.mapper.StateUpdatableMapper<User> {

  UserInfo getInfo(@Param("id") Long id) throws DataAccessException;

  UserInfo getInfoByUsername(@Param("username") String username)
      throws DataAccessException;

  UserInfo getInfoByMobile(@Param("mobile") Phone mobile)
      throws DataAccessException;

  UserInfo getInfoByEmail(@Param("email") String email)
      throws DataAccessException;

  long updateByUsername(User user) throws DataAccessException;

  long updateAvatar(@Param("id") Long id,
      @Param("avatar") String avatar,
      @Param("modifyTime") Instant modifyTime)
      throws DataAccessException;

  long updateLastLogin(@Param("id") Long id,
      @Param("lastLogin") AuthorizeRecord lastLogin,
      @Param("modifyTime") Instant modifyTime)
      throws DataAccessException;

  long updateChangePassword(@Param("id") Long id,
      @Param("changePassword") Boolean changePassword,
      @Param("modifyTime") Instant modifyTime)
      throws DataAccessException;
}
