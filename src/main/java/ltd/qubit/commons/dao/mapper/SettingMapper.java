////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.mapper;

import java.time.Instant;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.SettingDao;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.model.WithName;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.system.Setting;

/**
 * 实现{@link SettingDao}的MyBatis Mapper。
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #existName(String)}</li>
 * <li>{@link #getByName(String)}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #updateByName(WithName)}</li>
 * <li>{@link #updateTypeValueByName(String, Type, String, Instant)}</li>
 * <li>{@link #eraseByName(String)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @author 胡海星
 */
public interface SettingMapper extends ListableAddOrUpdatableByNameMapper<Setting>,
    GettableByNameMapper<Setting>, ErasableByNameMapper<Setting>,
    ClearableMapper<Setting> {

  long updateValueByName(@Param("name") String name,
      @Param("value") String value,
      @Param("modifyTime") Instant modifyTime) throws DataAccessException;

  long updateTypeValueByName(@Param("name") String name,
      @Param("type") Type type,
      @Param("value") String value,
      @Param("modifyTime") Instant modifyTime) throws DataAccessException;

}
