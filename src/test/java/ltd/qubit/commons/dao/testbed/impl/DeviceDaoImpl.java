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
import ltd.qubit.commons.dao.testbed.DeviceDao;
import ltd.qubit.commons.dao.testbed.mapper.DeviceMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ltd.qubit.model.contact.Address;
import ltd.qubit.model.device.Device;
import ltd.qubit.model.device.SimCard;

import static ltd.qubit.commons.dao.DaoImplHelper.addImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.existKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updatePropertyByKeyImpl;

/**
 * 实现{@link DeviceDao}。
 *
 * @author 胡海星
 */
public class DeviceDaoImpl extends DaoImpl<Device> implements DeviceDao {

  @Autowired
  private DeviceMapper mapper;

  public DeviceDaoImpl() {
    super(Device.class);
  }

  @Override
  public DeviceMapper getMapper() {
    return mapper;
  }

  @Override
  public boolean existUdid(final String udid) throws DataAccessException {
    return existKeyImpl(this, () -> mapper.existUdid(udid), "udid", udid);
  }

  @Override
  public Device getByUdid(final String udid) throws DataAccessException {
    return getByKeyImpl(this, () -> mapper.getByUdid(udid), "udid", udid);
  }

  @Override
  public Instant add(final Device obj) throws DataAccessException {
    obj.setRegisterTime(null);
    obj.setLastStartupTime(null);
    obj.setLastHeartbeatTime(null);
    return addImpl(this, obj);
  }

  @Override
  public Instant update(final Device obj) throws DataAccessException {
    return updateImpl(this, obj);
  }

  @Override
  public Instant updateByCode(final Device obj) throws DataAccessException {
    return updateByKeyImpl(this, modifyTime -> mapper.updateByCode(obj),
        obj, "code", obj.getCode());
  }

  @Override
  public Instant updateDeployAddress(final Long id, final Address deployAddress)
      throws DataAccessException {
    return updatePropertyByKeyImpl(this,
        modifyTime -> mapper.updateDeployAddress(id, deployAddress, modifyTime),
        "deployAddress", deployAddress, "id", id);
  }

  @Override
  public Instant updateSimCard(final Long id, final SimCard simCard)
      throws DataAccessException {
    return updatePropertyByKeyImpl(this,
        modifyTime -> mapper.updateSimCard(id, simCard, modifyTime),
        "simCard", simCard, "id", id);
  }
}
