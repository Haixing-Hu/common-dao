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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.impl.DaoImpl;
import ltd.qubit.commons.dao.testbed.AppDao;
import ltd.qubit.commons.dao.testbed.mapper.AppMapper;
import ltd.qubit.model.commons.App;
import ltd.qubit.model.commons.AuthorizeRecord;

import static ltd.qubit.commons.dao.DaoImplHelper.addImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updatePropertyByKeyImpl;

/**
 * 实现{@link AppDao}。
 *
 * @author 胡海星
 */
public class AppDaoImpl extends DaoImpl<App> implements AppDao {

  @Autowired
  private AppMapper mapper;

  public AppDaoImpl() {
    super(App.class);
  }

  @Override
  public AppMapper getMapper() {
    return mapper;
  }

  @Override
  public Instant add(final App obj) throws DataAccessException {
    obj.setLastAuthorize(new AuthorizeRecord());
    return addImpl(this, obj);
  }

  @Override
  public Instant update(final App obj) throws DataAccessException {
    return updateImpl(this, obj);
  }

  @Override
  public Instant updateByCode(final App obj) throws DataAccessException {
    return updateByKeyImpl(this, t -> mapper.updateByCode(obj), obj,
        "code", obj.getCode());
  }

  @Override
  public Instant updateLastAuthorize(final Long id, final AuthorizeRecord lastAuthorize)
      throws DataAccessException {
    return updatePropertyByKeyImpl(this,
        modifyTime -> mapper.updateLastAuthorize(id, lastAuthorize, modifyTime),
        "lastAuthorize", lastAuthorize, "id", id);
  }

  @Override
  public Instant updateLastAuthorizeByCode(final String code,
      final AuthorizeRecord lastAuthorize) throws DataAccessException {
    return updatePropertyByKeyImpl(this,
        modifyTime -> mapper.updateLastAuthorizeByCode(code, lastAuthorize, modifyTime),
        "lastAuthorize", lastAuthorize, "code", code);
  }
}
