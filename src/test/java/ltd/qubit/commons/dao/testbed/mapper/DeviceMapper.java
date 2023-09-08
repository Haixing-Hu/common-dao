////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed.mapper;

import java.time.Instant;

import javax.annotation.Nullable;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.mapper.BasicHasStatefulInfoByCodeMapper;
import ltd.qubit.commons.dao.mapper.CommentUpdatableMapper;
import ltd.qubit.commons.dao.mapper.LocationUpdatableMapper;
import ltd.qubit.commons.dao.testbed.DeviceDao;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.StatefulInfo;
import ltd.qubit.commons.model.WithCode;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.commons.State;
import ltd.qubit.model.contact.Address;
import ltd.qubit.model.device.Device;
import ltd.qubit.model.device.SimCard;

/**
 * 实现{@link DeviceDao}的MyBatis Mapper。
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #existCode(String)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getByCode(String)}</li>
 * <li>{@link #getIdByCode(String)}</li>
 * <li>{@link #getInfo(Long)}，返回一个{@link StatefulInfo}对象</li>
 * <li>{@link #getInfoByCode(String)}，返回一个{@link StatefulInfo}对象</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #update(Identifiable)}</li>
 * <li>{@link #updateByCode(WithCode)}</li>
 * <li>{@link #updateState(Long, State, Instant)}</li>
 * <li>{@link #delete(Long, Instant)}</li>
 * <li>{@link #deleteByCode(String, Instant)}</li>
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
public interface DeviceMapper extends BasicHasStatefulInfoByCodeMapper<Device>,
    CommentUpdatableMapper<Device>, LocationUpdatableMapper<Device> {

  /**
   * 判定拥有指定UDID的设备是否存在。
   *
   * @param udid
   *     指定的UDId。
   * @return
   *     若拥有指定UDID的设备存在，则返回{@code true}；否则返回{@code false}。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  boolean existUdid(@Param("udid") String udid) throws DataAccessException;

  /**
   * 根据UDID获取指定的设备。
   *
   * @param udid
   *     指定的设备的UDID。
   * @return
   *     具有指定UDID的设备，或{@code null}若指定的设备不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  Device getByUdid(@Param("udid") String udid) throws DataAccessException;

  long updateDeployAddress(@Param("id") Long id,
      @Param("deployAddress") Address deployAddress,
      @Param("modifyTime") Instant modifyTime) throws DataAccessException;

  long updateSimCard(@Param("id") Long id,
      @Param("simCard") SimCard simCard,
      @Param("modifyTime") Instant modifyTime) throws DataAccessException;
}
