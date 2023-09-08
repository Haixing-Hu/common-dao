////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed.impl;

import java.time.Instant;

import ltd.qubit.commons.dao.impl.DaoImpl;
import ltd.qubit.commons.dao.testbed.UserDao;
import ltd.qubit.commons.dao.testbed.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ltd.qubit.model.commons.AuthorizeRecord;
import ltd.qubit.model.contact.Phone;
import ltd.qubit.model.person.User;
import ltd.qubit.model.person.UserInfo;

import static ltd.qubit.commons.dao.DaoImplHelper.addImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updatePropertyByKeyImpl;

public class UserDaoImpl extends DaoImpl<User> implements UserDao {

  @Autowired
  private UserMapper mapper;

  public UserDaoImpl() {
    super(User.class);
  }

  @Override
  public UserMapper getMapper() {
    return mapper;
  }

  @Override
  public UserInfo getInfo(final Long id) throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> mapper.getInfo(id),
        "info", "id", id);
  }

  @Override
  public UserInfo getInfoOrNull(final Long id) throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> mapper.getInfo(id),
        "info", "id", id);
  }

  @Override
  public UserInfo getInfoByUsername(final String username)
      throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> mapper.getInfoByUsername(username),
        "info", "username", username);
  }

  @Override
  public UserInfo getInfoByUsernameOrNull(final String username)
      throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> mapper.getInfoByUsername(username),
        "info", "username", username);
  }

  @Override
  public UserInfo getInfoByMobile(final Phone mobile)
      throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> mapper.getInfoByMobile(mobile),
        "info", "mobile", mobile);
  }

  @Override
  public UserInfo getInfoByMobileOrNull(final Phone mobile)
      throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> mapper.getInfoByMobile(mobile),
        "info", "mobile", mobile);
  }

  @Override
  public UserInfo getInfoByEmail(final String email)
      throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> mapper.getInfoByEmail(email),
        "info", "email", email);
  }

  @Override
  public UserInfo getInfoByEmailOrNull(final String email)
      throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> mapper.getInfoByEmail(email),
        "info", "email", email);
  }

  @Override
  public Instant add(final User user) throws DataAccessException {
    user.setLastLogin(new AuthorizeRecord());
    return addImpl(this, user);
  }

  @Override
  public Instant update(final User user) throws DataAccessException {
    return updateImpl(this, user);
  }

  @Override
  public Instant updateByUsername(final User user) throws DataAccessException {
    return updateByKeyImpl(this, (modifyTime) -> {
          user.setModifyTime(modifyTime);
          return mapper.updateByUsername(user);
    }, user, "username", user.getUsername());
  }

  @Override
  public Instant updateAvatar(final Long id, final String avatar)
      throws DataAccessException {
    return updatePropertyByKeyImpl(this,
        (modifyTime) -> mapper.updateAvatar(id, avatar, modifyTime),
        "avatar", avatar, "id", id);
  }

  @Override
  public Instant updateLastLogin(final Long id, final AuthorizeRecord lastLogin)
      throws DataAccessException {
    return updatePropertyByKeyImpl(this,
        modifyTime -> mapper.updateLastLogin(id, lastLogin, modifyTime),
        "lastLogin", lastLogin, "id", id);
  }

  @Override
  public Instant updateChangePassword(final Long id, final Boolean changePassword)
      throws DataAccessException {
    return updatePropertyByKeyImpl(this,
        modifyTime -> mapper.updateChangePassword(id, changePassword, modifyTime),
        "changePassword", changePassword, "id", id);
  }

  @Override
  public Instant addOrUpdateByUsername(final User user)
      throws DataAccessException {
    logger.debug("Add or update {} by its username: {}", getEntityName(), user);
    final boolean exist = mapper.existUsername(user.getUsername());
    if (!exist) {
      return this.add(user);
    } else {
      return this.updateByUsername(user);
    }
  }
}
