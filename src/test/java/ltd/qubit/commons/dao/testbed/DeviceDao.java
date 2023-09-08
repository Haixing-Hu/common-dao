////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed;

import java.time.Instant;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.annotation.Modified;
import ltd.qubit.commons.dao.BasicHasStatefulInfoByCodeDao;
import ltd.qubit.commons.dao.CommentUpdatableDao;
import ltd.qubit.commons.dao.LocationUpdatableDao;
import ltd.qubit.commons.dao.testbed.mapper.DeviceMapper;
import ltd.qubit.commons.model.StatefulInfo;
import ltd.qubit.commons.model.WithCode;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.commons.State;
import ltd.qubit.model.contact.Address;
import ltd.qubit.model.device.Device;
import ltd.qubit.model.device.SimCard;

/**
 * 对{@link Device}对象进行数据库存取的DAO接口。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #existCode(String)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getOrNull(Long)}</li>
 * <li>{@link #getByCode(String)}</li>
 * <li>{@link #getByCodeOrNull(String)}</li>
 * <li>{@link #getIdByCode(String)}</li>
 * <li>{@link #getIdByCodeOrNull(String)}</li>
 * <li>{@link #getInfo(Long)}，此函数返回一个{@link StatefulInfo}对象</li>
 * <li>{@link #getInfoOrNull(Long)}，此函数返回一个{@link StatefulInfo}对象或{@code null}</li>
 * <li>{@link #getInfoByCode(String)}，此函数返回一个{@link StatefulInfo}对象</li>
 * <li>{@link #getInfoByCodeOrNull(String)}，此函数返回一个{@link StatefulInfo}对象或{@code null}</li>
 * <li>{@link #add(Device)}</li>
 * <li>{@link #update(Device)}</li>
 * <li>{@link #updateByCode(Device)}</li>
 * <li>{@link #updateState(Long, State)}</li>
 * <li>{@link #updateStateByCode(String, State)}</li>
 * <li>{@link #addOrUpdateByCode(WithCode)}</li>
 * <li>{@link #delete(Long)}</li>
 * <li>{@link #deleteByCode(String)}</li>
 * <li>{@link #restore(Long)}</li>
 * <li>{@link #restoreByCode(String)}</li>
 * <li>{@link #purge(Long)}</li>
 * <li>{@link #purgeByCode(String)}</li>
 * <li>{@link #purgeAll()}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #eraseByCode(String)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @author 胡海星
 */
public interface DeviceDao extends BasicHasStatefulInfoByCodeDao<Device>,
    CommentUpdatableDao<Device>, LocationUpdatableDao<Device> {

  @Override
  DeviceMapper getMapper();

  boolean existUdid(String udid) throws DataAccessException;

  Device getByUdid(String udid) throws DataAccessException;

  @Modified({"id", "registerTime", "lastStartupTime", "lastHeartbeatTime",
      "createTime", "modifyTime", "deleteTime"})
  Instant add(Device obj) throws DataAccessException;

  @Modified({"name", "brand", "model", "manufacturer", "version", "description",
      "osType", "osName", "osVersion", "clientAppName", "clientAppVersion",
      "clientAppManufacturer", "modifyTime"})
  Instant update(Device obj) throws DataAccessException;

  @Modified({"name", "brand", "model", "manufacturer", "version", "description",
      "osType", "osName", "osVersion", "clientAppName", "clientAppVersion",
      "clientAppManufacturer", "modifyTime"})
  Instant updateByCode(Device obj) throws DataAccessException;

  @Modified({"deployAddress", "modifyTime"})
  Instant updateDeployAddress(Long id, Address deployAddress) throws DataAccessException;

  @Modified({"simCard", "modifyTime"})
  Instant updateSimCard(Long id, SimCard simCard) throws DataAccessException;
}
