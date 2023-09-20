////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ltd.qubit.model.person.UserInfo;

public class UserInfoDaoImpl extends DaoImpl<UserInfo> implements UserInfoDao {

  @Autowired
  private UserInfoMapper mapper;

  public UserInfoDaoImpl() {
    super(UserInfo.class);
  }

  @Override
  public UserInfoMapper getMapper() {
    return mapper;
  }
}
