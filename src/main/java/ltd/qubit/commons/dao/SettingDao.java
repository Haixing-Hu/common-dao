////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.annotation.Nullable;

import ltd.qubit.commons.dao.mapper.SettingMapper;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.annotation.Modified;
import ltd.qubit.commons.annotation.NoAutoTest;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.error.TypeMismatchException;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.system.Setting;

/**
 * 对{@link Setting}对象进行数据库存取的DAO接口。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #existName(String)}
 * <li>{@link #getByName(String)}
 * <li>{@link #getByNameOrNull(String)}
 * <li>{@link #add(Object)}</li>
 * <li>{@link #updateByName(Setting)}
 * <li>{@link #eraseByName(String)}
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @author 胡海星
 */
public interface SettingDao extends ListableAddOrUpdatableByNameDao<Setting>,
    GettableByNameDao<Setting>, ErasableByNameDao<Setting>, ClearableDao<Setting> {

  @Override
  SettingMapper getMapper();

  @Modified({"value", "description", "modifyTime"})
  Instant updateByName(Setting obj) throws DataAccessException;

  @Modified({"value", "modifyTime"})
  Instant updateValueByName(String name, @Nullable String value) throws DataAccessException;

  /**
   * 以{@link Boolean}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @return
   *     指定的设置的值，以{@link Boolean}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Boolean getBool(String name) throws DataAccessException;

  /**
   * 以{@link Boolean}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param defaultValue
   *     当指定的设置的值为{@code null}时，返回的默认值。
   * @return
   *     指定的设置的值，以{@link Boolean}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Boolean getBool(String name, Boolean defaultValue) throws DataAccessException;

  /**
   * 以{@link Character}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @return
   *     指定的设置的值，以{@link Character}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Character getChar(String name) throws DataAccessException;

  /**
   * 以{@link Character}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param defaultValue
   *     当指定的设置的值为{@code null}时，返回的默认值。
   * @return
   *     指定的设置的值，以{@link Character}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Character getChar(String name, Character defaultValue) throws DataAccessException;

  /**
   * 以{@link Byte}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @return
   *     指定的设置的值，以{@link Byte}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Byte getByte(String name) throws DataAccessException;

  /**
   * 以{@link Byte}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param defaultValue
   *     当指定的设置的值为{@code null}时，返回的默认值。
   * @return
   *     指定的设置的值，以{@link Byte}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Byte getByte(String name, Byte defaultValue) throws DataAccessException;

  /**
   * 以{@link Short}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @return
   *     指定的设置的值，以{@link Short}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Short getShort(String name) throws DataAccessException;

  /**
   * 以{@link Short}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param defaultValue
   *     当指定的设置的值为{@code null}时，返回的默认值。
   * @return
   *     指定的设置的值，以{@link Short}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Short getShort(String name, Short defaultValue) throws DataAccessException;

  /**
   * 以{@link Integer}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @return
   *     指定的设置的值，以{@link Integer}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Integer getInt(String name) throws DataAccessException;

  /**
   * 以{@link Integer}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param defaultValue
   *     当指定的设置的值为{@code null}时，返回的默认值。
   * @return
   *     指定的设置的值，以{@link Integer}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Integer getInt(String name, Integer defaultValue) throws DataAccessException;

  /**
   * 以{@link Long}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @return
   *     指定的设置的值，以{@link Long}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  Long getLong(String name) throws DataAccessException;

  /**
   * 以{@link Long}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param defaultValue
   *     当指定的设置的值为{@code null}时，返回的默认值。
   * @return
   *     指定的设置的值，以{@link Long}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  Long getLong(String name, Long defaultValue) throws DataAccessException;

  /**
   * 以{@link Float}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @return
   *     指定的设置的值，以{@link Float}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Float getFloat(String name) throws DataAccessException;

  /**
   * 以{@link Float}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param defaultValue
   *     当指定的设置的值为{@code null}时，返回的默认值。
   * @return
   *     指定的设置的值，以{@link Float}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Float getFloat(String name, Float defaultValue) throws DataAccessException;

  /**
   * 以{@link Double}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @return
   *     指定的设置的值，以{@link Double}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Double getDouble(String name) throws DataAccessException;

  /**
   * 以{@link Double}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param defaultValue
   *     当指定的设置的值为{@code null}时，返回的默认值。
   * @return
   *     指定的设置的值，以{@link Double}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Double getDouble(String name, Double defaultValue) throws DataAccessException;

  /**
   * 以{@link String}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @return
   *     指定的设置的值，以{@link String}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  String getString(String name) throws DataAccessException;

  /**
   * 以{@link String}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param defaultValue
   *     当指定的设置的值为{@code null}时，返回的默认值。
   * @return
   *     指定的设置的值，以{@link String}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  String getString(String name, String defaultValue) throws DataAccessException;

  /**
   * 以枚举值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param enumClass
   *     指定的枚举的类。
   * @return
   *     指定的设置的值，以枚举值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  <T extends Enum<T>> T getEnum(String name, Class<T> enumClass)
      throws DataAccessException;

  /**
   * 以枚举值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param enumClass
   *     指定的枚举的类。
   * @param defaultValue
   *     当指定的设置的值为{@code null}时，返回的默认值。
   * @return
   *     指定的设置的值，以枚举值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  <T extends Enum<T>> T getEnum(String name, Class<T> enumClass, T defaultValue)
      throws DataAccessException;

  /**
   * 以{@link LocalDate}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @return
   *     指定的设置的值，以{@link LocalDate}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  LocalDate getDate(String name) throws DataAccessException;

  /**
   * 以{@link LocalDate}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param defaultValue
   *     当指定的设置的值为{@code null}时，返回的默认值。
   * @return
   *     指定的设置的值，以{@link LocalDate}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  LocalDate getDate(String name, LocalDate defaultValue) throws DataAccessException;

  /**
   * 以{@link LocalTime}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @return
   *     指定的设置的值，以{@link LocalTime}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  LocalTime getTime(String name) throws DataAccessException;

  /**
   * 以{@link LocalTime}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param defaultValue
   *     当指定的设置的值为{@code null}时，返回的默认值。
   * @return
   *     指定的设置的值，以{@link LocalTime}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  LocalTime getTime(String name, LocalTime defaultValue) throws DataAccessException;

  /**
   * 以{@link LocalDateTime}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @return
   *     指定的设置的值，以{@link LocalDateTime}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  LocalDateTime getDateTime(String name) throws DataAccessException;

  /**
   * 以{@link LocalDateTime}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param defaultValue
   *     当指定的设置的值为{@code null}时，返回的默认值。
   * @return
   *     指定的设置的值，以{@link LocalDateTime}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  LocalDateTime getDateTime(String name, LocalDateTime defaultValue) throws DataAccessException;

  /**
   * 以{@link Timestamp}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @return
   *     指定的设置的值，以{@link Timestamp}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Timestamp getTimestamp(String name) throws DataAccessException;

  /**
   * 以{@link Timestamp}值的形式获取指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param defaultValue
   *     当指定的设置的值为{@code null}时，返回的默认值。
   * @return
   *     指定的设置的值，以{@link Timestamp}值形式表示，注意可能为{@code null}。
   * @throws DataNotExistException
   *     如果指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   * @throws TypeMismatchException
   *     如果指定的设置的值的类型无法转化为指定的返回类型。
   */
  @NoAutoTest
  @Nullable
  Timestamp getTimestamp(String name, Timestamp defaultValue) throws DataAccessException;

  /**
   * 以{@link Boolean}形式设置指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param value
   *     待设置的值。
   * @return
   *     该设置被修改时的时间戳。
   * @throws DataAccessException
   *     若指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   */
  @NoAutoTest
  Instant setBool(String name, @Nullable Boolean value)
      throws DataAccessException;

  /**
   * 以{@link Character}形式设置指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param value
   *     待设置的值。
   * @return
   *     该设置被修改时的时间戳。
   * @throws DataAccessException
   *     若指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   */
  @NoAutoTest
  Instant setChar(String name, @Nullable Character value)
      throws DataAccessException;

  /**
   * 以{@link Byte}形式设置指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param value
   *     待设置的值。
   * @return
   *     该设置被修改时的时间戳。
   * @throws DataAccessException
   *     若指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   */
  @NoAutoTest
  Instant setByte(String name, @Nullable Byte value)
      throws DataAccessException;

  /**
   * 以{@link Short}形式设置指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param value
   *     待设置的值。
   * @return
   *     该设置被修改时的时间戳。
   * @throws DataAccessException
   *     若指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   */
  @NoAutoTest
  Instant setShort(String name, @Nullable Short value)
      throws DataAccessException;

  /**
   * 以{@link Integer}形式设置指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param value
   *     待设置的值。
   * @return
   *     该设置被修改时的时间戳。
   * @throws DataAccessException
   *     若指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   */
  @NoAutoTest
  Instant setInt(String name, @Nullable Integer value)
      throws DataAccessException;

  /**
   * 以{@link Long}形式设置指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param value
   *     待设置的值。
   * @return
   *     该设置被修改时的时间戳。
   * @throws DataAccessException
   *     若指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   */
  @NoAutoTest
  Instant setLong(String name, @Nullable Long value)
      throws DataAccessException;

  /**
   * 以{@link Float}形式设置指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param value
   *     待设置的值。
   * @return
   *     该设置被修改时的时间戳。
   * @throws DataAccessException
   *     若指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   */
  @NoAutoTest
  Instant setFloat(String name, @Nullable Float value)
      throws DataAccessException;

  /**
   * 以{@link Double}形式设置指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param value
   *     待设置的值。
   * @return
   *     该设置被修改时的时间戳。
   * @throws DataAccessException
   *     若指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   */
  @NoAutoTest
  Instant setDouble(String name, @Nullable Double value)
      throws DataAccessException;

  /**
   * 以{@link String}形式设置指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param value
   *     待设置的值。
   * @return
   *     该设置被修改时的时间戳。
   * @throws DataAccessException
   *     若指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   */
  @NoAutoTest
  Instant setString(String name, @Nullable String value)
      throws DataAccessException;

  /**
   * 以{@link Enum}形式设置指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param value
   *     待设置的值。
   * @return
   *     该设置被修改时的时间戳。
   * @throws DataAccessException
   *     若指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   */
  @NoAutoTest
  <T extends Enum<T>> Instant setEnum(String name, @Nullable T value)
      throws DataAccessException;

  /**
   * 以{@link LocalDate}形式设置指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param value
   *     待设置的值。
   * @return
   *     该设置被修改时的时间戳。
   * @throws DataAccessException
   *     若指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   */
  @NoAutoTest
  Instant setDate(String name, @Nullable LocalDate value)
      throws DataAccessException;

  /**
   * 以{@link LocalTime}形式设置指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param value
   *     待设置的值。
   * @return
   *     该设置被修改时的时间戳。
   * @throws DataAccessException
   *     若指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   */
  @NoAutoTest
  Instant setTime(String name, @Nullable LocalTime value)
      throws DataAccessException;

  /**
   * 以{@link LocalDateTime}形式设置指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param value
   *     待设置的值。
   * @return
   *     该设置被修改时的时间戳。
   * @throws DataAccessException
   *     若指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   */
  @NoAutoTest
  Instant setDateTime(String name, @Nullable LocalDateTime value)
      throws DataAccessException;

  /**
   * 以{@link Timestamp}形式设置指定的设置的值。
   *
   * @param name
   *     指定的设置的名称。
   * @param value
   *     待设置的值。
   * @return
   *     该设置被修改时的时间戳。
   * @throws DataAccessException
   *     若指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   */
  @NoAutoTest
  Instant setTimestamp(String name, @Nullable Timestamp value)
      throws DataAccessException;

  /**
   * 根据名称，更新指定的设置的类型和取值。
   *
   * @param setting
   *     指定的设置。
   * @return
   *     该设置被修改时的时间戳。
   * @throws DataAccessException
   *     若指定的设置不存在。
   * @throws DataAccessException
   *     如果发生任何其他数据库存取错误。
   */
  @NoAutoTest
  Instant updateTypeValueByName(Setting setting);
}
