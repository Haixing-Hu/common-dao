////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.impl;

import javax.annotation.Nullable;

import ltd.qubit.commons.dao.HostDao;
import ltd.qubit.commons.dao.mapper.HostMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ltd.qubit.model.system.Host;

import static ltd.qubit.commons.dao.DaoImplHelper.clearImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.existKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyOrNullImpl;

/**
 * 实现{@link HostDao}。
 *
 * <p><b>注意：</b>此DAO对象应通过IoC注入一个{@link ltd.qubit.id.AutoIncrementIdGenerator}。</p>
 * @author 胡海星
 */
public class HostDaoImpl extends DaoImpl<Host> implements HostDao {

  @Autowired
  private HostMapper mapper;

  public HostDaoImpl() {
    super(Host.class);
  }

  @Override
  public HostMapper getMapper() {
    return mapper;
  }

  @Override
  public boolean existUdid(final String udid) throws DataAccessException {
    return existKeyImpl(this, () -> mapper.existUdid(udid), "udid", udid);
  }

  @Override
  public Host getByUdid(final String udid) throws DataAccessException {
    return getByKeyImpl(this, () -> mapper.getByUdid(udid), "udid", udid);
  }

  @Nullable
  @Override
  public Host getByUdidOrNull(final String udid) throws DataAccessException {
    return getByKeyOrNullImpl(this, () -> mapper.getByUdid(udid), "udid", udid);
  }

  public long clear() throws DataAccessException {
    final long result = clearImpl(this);
    idGenerator.reset();    // 重置ID generator
    return result;
  }
}
