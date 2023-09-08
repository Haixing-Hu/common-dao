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
import ltd.qubit.commons.dao.testbed.CityDao;
import ltd.qubit.commons.dao.testbed.mapper.CityMapper;

import org.springframework.beans.factory.annotation.Autowired;

import ltd.qubit.model.contact.City;

/**
 * 实现{@link CityDao}。
 *
 * @author 胡海星
 */
public class CityDaoImpl extends DaoImpl<City> implements CityDao {

  @Autowired
  private CityMapper mapper;

  public CityDaoImpl() {
    super(City.class);
  }

  @Override
  public CityMapper getMapper() {
    return mapper;
  }
}
