////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed.impl;

import ltd.qubit.commons.dao.impl.DaoImpl;
import ltd.qubit.commons.dao.testbed.StreetDao;
import ltd.qubit.commons.dao.testbed.mapper.StreetMapper;

import org.springframework.beans.factory.annotation.Autowired;

import ltd.qubit.model.contact.Street;

/**
 * 实现{@link StreetDao}。
 *
 * @author 胡海星
 */
public class StreetDaoImpl extends DaoImpl<Street> implements StreetDao {

  @Autowired
  private StreetMapper mapper;

  public StreetDaoImpl() {
    super(Street.class);
  }

  @Override
  public StreetMapper getMapper() {
    return mapper;
  }
}
