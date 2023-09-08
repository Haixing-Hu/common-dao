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
import ltd.qubit.commons.dao.testbed.ProvinceDao;
import ltd.qubit.commons.dao.testbed.mapper.ProvinceMapper;

import org.springframework.beans.factory.annotation.Autowired;

import ltd.qubit.model.contact.Province;

/**
 * 实现{@link ProvinceDao}。
 *
 * @author 胡海星
 */
public class ProvinceDaoImpl extends DaoImpl<Province> implements ProvinceDao {

  @Autowired
  private ProvinceMapper mapper;

  public ProvinceDaoImpl() {
    super(Province.class);
  }

  @Override
  public ProvinceMapper getMapper() {
    return mapper;
  }
}
