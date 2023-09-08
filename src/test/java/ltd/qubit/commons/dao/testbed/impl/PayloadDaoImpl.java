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
import java.util.List;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.impl.DaoImpl;
import ltd.qubit.commons.dao.testbed.PayloadDao;
import ltd.qubit.commons.dao.testbed.mapper.PayloadMapper;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.model.commons.Owner;
import ltd.qubit.model.commons.Payload;

import static ltd.qubit.commons.dao.DaoImplHelper.addImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.batchAddImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateImpl;

/**
 * 实现{@link PayloadDao}。
 *
 * @author 胡海星
 */
public class PayloadDaoImpl extends DaoImpl<Payload> implements PayloadDao {

  @Autowired
  private PayloadMapper mapper;

  public PayloadDaoImpl() {
    super(Payload.class);
  }

  @Override
  public PayloadMapper getMapper() {
    return mapper;
  }

  @Override
  public Instant update(final Payload obj) throws DataAccessException {
    return updateImpl(this, obj);
  }

  @Override
  public Instant updateByKey(final Payload obj) throws DataAccessException {
    return updateByKeyImpl(this, modifyTime -> mapper.updateByKey(obj),
        obj, "key", obj.getKey());
  }

  @Override
  public <T extends Identifiable>
  Instant addForOwner(final T obj, final String property,
      @Nullable final Payload payload) throws DataAccessException {
    if (payload == null) {
      return now();
    }
    final Owner owner = new Owner(obj, property);
    logger.debug("Add a {} to the owner: owner = {}, payload = {}",
        getEntityName(), owner, payload);
    payload.setOwner(owner);
    return addImpl(this, payload);
  }

  @Override
  public <T extends Identifiable>
  Instant addForOwner(final T obj, final String property,
      @Nullable final List<Payload> payloads) throws DataAccessException {
    if (payloads == null) {
      return now();
    }
    final Owner owner = new Owner(obj, property);
    logger.debug("Add the list of {} to the owner: owner = {}, payloads = {}",
      getEntityName(), owner, payloads);
    for (final Payload payload : payloads) {
      payload.setOwner(owner);
    }
    return batchAddImpl(this, payloads);
  }

  @Override
  public <T extends Identifiable>
  Instant updateForOwner(final T obj, final String property, final String key,
      @Nullable final Payload payload) throws DataAccessException {
    final Owner owner = new Owner(obj, property);
    logger.debug("Update the {} for the owner: owner = {}, key = {}, payload = {}",
        getEntityName(), owner, key, payload);
    mapper.eraseByKey(owner, key);
    if (payload != null) {
      payload.setOwner(owner);
      return addImpl(this, payload);
    }
    return now();
  }

  @Override
  public <T extends Identifiable>
  Instant updateForOwner(final T obj, final String property,
      @Nullable final List<Payload> payloads) throws DataAccessException {
    final Owner owner = new Owner(obj, property);
    logger.debug("Update the list of {} for the owner: owner = {}, payloads = {}",
        getEntityName(), owner, payloads);
    // 全部清除旧的后再添加，否则实现起来很麻烦
    mapper.eraseForOwner(owner);
    if (payloads != null && payloads.size() > 0) {
      for (final Payload payload : payloads) {
        payload.setOwner(owner);
      }
      return batchAddImpl(this, payloads);
    }
    return now();
  }
}
