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
import ltd.qubit.commons.dao.testbed.CountryDao;
import ltd.qubit.commons.dao.testbed.mapper.CountryMapper;

import org.springframework.beans.factory.annotation.Autowired;

import ltd.qubit.model.contact.Country;

/**
 * 实现{@link CountryDao}。
 *
 * @author 胡海星
 */
public class CountryDaoImpl extends DaoImpl<Country> implements CountryDao {

  @Autowired
  private CountryMapper mapper;

  public CountryDaoImpl() {
    super(Country.class);
  }

  @Override
  public CountryMapper getMapper() {
    return mapper;
  }
}
