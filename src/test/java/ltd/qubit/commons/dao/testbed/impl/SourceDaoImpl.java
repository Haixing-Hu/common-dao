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
import ltd.qubit.commons.dao.testbed.SourceDao;
import ltd.qubit.commons.dao.testbed.mapper.SourceMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ltd.qubit.model.commons.Source;

import static ltd.qubit.commons.dao.DaoImplHelper.updateByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateImpl;

/**
 * 实现{@link SourceDao}。
 *
 * @author 胡海星
 */
public class SourceDaoImpl extends DaoImpl<Source> implements SourceDao {

  @Autowired
  private SourceMapper mapper;

  public SourceDaoImpl() {
    super(Source.class);
  }

  @Override
  public SourceMapper getMapper() {
    return mapper;
  }

  @Override
  public Instant update(final Source obj) throws DataAccessException {
    return updateImpl(this, obj);
  }

  @Override
  public Instant updateByCode(final Source obj) throws DataAccessException {
    return updateByKeyImpl(this, modifyTime -> mapper.updateByCode(obj), obj,
        "code", obj.getCode());
  }
}
