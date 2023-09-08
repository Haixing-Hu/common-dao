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
import ltd.qubit.commons.dao.testbed.DistrictDao;
import ltd.qubit.commons.dao.testbed.mapper.DistrictMapper;

import org.springframework.beans.factory.annotation.Autowired;

import ltd.qubit.model.contact.District;

/**
 * 实现{@link DistrictDao}。
 *
 * @author 胡海星
 */
public class DistrictDaoImpl extends DaoImpl<District> implements DistrictDao {

  @Autowired
  private DistrictMapper mapper;

  public DistrictDaoImpl() {
    super(District.class);
  }

  @Override
  public DistrictMapper getMapper() {
    return mapper;
  }
}
