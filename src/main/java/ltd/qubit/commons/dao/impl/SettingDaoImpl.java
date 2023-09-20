////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.model.system.Setting;

import static ltd.qubit.commons.dao.DaoImplHelper.updateByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updatePropertyByKeyImpl;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;

public class SettingDaoImpl extends DaoImpl<Setting> implements SettingDao {

  @Autowired
  private SettingMapper mapper;

  public SettingDaoImpl() {
    super(Setting.class);
  }

  public SettingMapper getMapper() {
    return mapper;
  }

  @Override
  public Instant updateByName(final Setting obj) throws DataAccessException {
    return updateByKeyImpl(this, modifyTime -> getMapper().updateByName(obj),
        obj, "name", obj.getName());
  }

  @Override
  public Instant updateValueByName(final String name, @Nullable final String value)
      throws DataAccessException {
    return updatePropertyByKeyImpl(this,
        modifyTime -> mapper.updateValueByName(name, value, modifyTime),
        "value", value, "name", name);
  }

  @Nullable
  public Boolean getBool(final String name) throws DataAccessException {
    final Setting setting = getByName(name);
    return setting.getBool();
  }

  @Nullable
  @Override
  public Boolean getBool(final String name, final Boolean defaultValue)
      throws DataAccessException {
    return defaultIfNull(getBool(name), defaultValue);
  }

  @Nullable
  public Character getChar(final String name) throws DataAccessException {
    final Setting setting = getByName(name);
    return setting.getChar();
  }

  @Nullable
  @Override
  public Character getChar(final String name, final Character defaultValue)
      throws DataAccessException {
    return defaultIfNull(getChar(name), defaultValue);
  }

  @Nullable
  public Byte getByte(final String name) throws DataAccessException {
    final Setting setting = getByName(name);
    return setting.getByte();
  }

  @Nullable
  @Override
  public Byte getByte(final String name, final Byte defaultValue) throws DataAccessException {
    return defaultIfNull(getByte(name), defaultValue);
  }

  @Nullable
  public Short getShort(final String name) throws DataAccessException {
    final Setting setting = getByName(name);
    return setting.getShort();
  }

  @Nullable
  @Override
  public Short getShort(final String name, final Short defaultValue) throws DataAccessException {
    return defaultIfNull(getShort(name), defaultValue);
  }

  @Nullable
  public Integer getInt(final String name) throws DataAccessException {
    final Setting setting = getByName(name);
    return setting.getInt();
  }

  @Nullable
  @Override
  public Integer getInt(final String name, final Integer defaultValue) throws DataAccessException {
    return defaultIfNull(getInt(name), defaultValue);
  }

  @Nullable
  public Long getLong(final String name) throws DataAccessException {
    final Setting setting = getByName(name);
    return setting.getLong();
  }

  @Override
  public Long getLong(final String name, final Long defaultValue) throws DataAccessException {
    return defaultIfNull(getLong(name), defaultValue);
  }

  @Nullable
  public Float getFloat(final String name) throws DataAccessException {
    final Setting setting = getByName(name);
    return setting.getFloat();
  }

  @Nullable
  @Override
  public Float getFloat(final String name, final Float defaultValue) throws DataAccessException {
    return defaultIfNull(getFloat(name), defaultValue);
  }

  @Nullable
  public Double getDouble(final String name) throws DataAccessException {
    final Setting setting = getByName(name);
    return setting.getDouble();
  }

  @Nullable
  @Override
  public Double getDouble(final String name, final Double defaultValue) throws DataAccessException {
    return defaultIfNull(getDouble(name), defaultValue);
  }

  @Nullable
  public String getString(final String name) throws DataAccessException {
    final Setting setting = getByName(name);
    return setting.getString();
  }

  @Nullable
  @Override
  public String getString(final String name, final String defaultValue) throws DataAccessException {
    return defaultIfNull(getString(name), defaultValue);
  }

  @Nullable
  @Override
  public <T extends Enum<T>> T getEnum(final String name, final Class<T> enumClass)
      throws DataAccessException {
    final Setting setting = getByName(name);
    return setting.getEnum(enumClass);
  }

  @Nullable
  @Override
  public <T extends Enum<T>> T getEnum(final String name, final Class<T> enumClass,
      final T defaultValue) throws DataAccessException {
    return defaultIfNull(getEnum(name, enumClass), defaultValue);
  }

  @Nullable
  public LocalDate getDate(final String name) throws DataAccessException {
    final Setting setting = getByName(name);
    return setting.getDate();
  }

  @Nullable
  @Override
  public LocalDate getDate(final String name, final LocalDate defaultValue)
      throws DataAccessException {
    return defaultIfNull(getDate(name), defaultValue);
  }

  @Nullable
  public LocalTime getTime(final String name) throws DataAccessException {
    final Setting setting = getByName(name);
    return setting.getTime();
  }

  @Nullable
  @Override
  public LocalTime getTime(final String name, final LocalTime defaultValue)
      throws DataAccessException {
    return defaultIfNull(getTime(name), defaultValue);
  }

  @Nullable
  public LocalDateTime getDateTime(final String name) throws DataAccessException {
    final Setting setting = getByName(name);
    return setting.getDateTime();
  }

  @Nullable
  @Override
  public LocalDateTime getDateTime(final String name, final LocalDateTime defaultValue)
      throws DataAccessException {
    return defaultIfNull(getDateTime(name), defaultValue);
  }

  @Nullable
  public Timestamp getTimestamp(final String name) throws DataAccessException {
    final Setting setting = getByName(name);
    return setting.getTimestamp();
  }

  @Nullable
  @Override
  public Timestamp getTimestamp(final String name, final Timestamp defaultValue)
      throws DataAccessException {
    return defaultIfNull(getTimestamp(name), defaultValue);
  }

  @Override
  public Instant setBool(final String name, @Nullable final Boolean value)
      throws DataAccessException {
    final Setting setting = new Setting(name);
    setting.setBool(value);
    return updateTypeValueByName(setting);
  }

  @Override
  public Instant setChar(final String name, @Nullable final Character value)
      throws DataAccessException {
    final Setting setting = new Setting(name);
    setting.setChar(value);
    return updateTypeValueByName(setting);
  }

  @Override
  public Instant setByte(final String name, @Nullable final Byte value)
      throws DataAccessException {
    final Setting setting = new Setting(name);
    setting.setByte(value);
    return updateTypeValueByName(setting);
  }

  @Override
  public Instant setShort(final String name, @Nullable final Short value)
      throws DataAccessException {
    final Setting setting = new Setting(name);
    setting.setShort(value);
    return updateTypeValueByName(setting);
  }

  @Override
  public Instant setInt(final String name, @Nullable final Integer value)
      throws DataAccessException {
    final Setting setting = new Setting(name);
    setting.setInt(value);
    return updateTypeValueByName(setting);
  }

  @Override
  public Instant setLong(final String name, @Nullable final Long value)
      throws DataAccessException {
    final Setting setting = new Setting(name);
    setting.setLong(value);
    return updateTypeValueByName(setting);
  }

  @Override
  public Instant setFloat(final String name, @Nullable final Float value)
      throws DataAccessException {
    final Setting setting = new Setting(name);
    setting.setFloat(value);
    return updateTypeValueByName(setting);
  }

  @Override
  public Instant setDouble(final String name, @Nullable final Double value)
      throws DataAccessException {
    final Setting setting = new Setting(name);
    setting.setDouble(value);
    return updateTypeValueByName(setting);
  }

  @Override
  public Instant setString(final String name, @Nullable final String value)
      throws DataAccessException {
    final Setting setting = new Setting(name);
    setting.setString(value);
    return updateTypeValueByName(setting);
  }

  @Override
  public <T extends Enum<T>> Instant setEnum(final String name,
      @Nullable final T value) throws DataAccessException {
    final Setting setting = new Setting(name);
    setting.setEnum(value);
    return updateTypeValueByName(setting);
  }

  @Override
  public Instant setDate(final String name, @Nullable final LocalDate value)
      throws DataAccessException {
    final Setting setting = new Setting(name);
    setting.setDate(value);
    return updateTypeValueByName(setting);
  }

  @Override
  public Instant setTime(final String name, @Nullable final LocalTime value)
      throws DataAccessException {
    final Setting setting = new Setting(name);
    setting.setTime(value);
    return updateTypeValueByName(setting);
  }

  @Override
  public Instant setDateTime(final String name, @Nullable final LocalDateTime value)
      throws DataAccessException {
    final Setting setting = new Setting(name);
    setting.setDateTime(value);
    return updateTypeValueByName(setting);
  }

  @Override
  public Instant setTimestamp(final String name, @Nullable final Timestamp value)
      throws DataAccessException {
    final Setting setting = new Setting(name);
    setting.setTimestamp(value);
    return updateTypeValueByName(setting);
  }

  public Instant updateTypeValueByName(final Setting setting) {
    logger.debug("Update the {} value by its name: name = {}, type = {}, value = {}",
        getEntityName(), setting.getName(), setting.getType(), setting.getValue());
    final Instant modifyTime = now();
    final long count = mapper.updateTypeValueByName(setting.getName(),
        setting.getType(), setting.getValue(), modifyTime);
    if (count == 0) {
      throw new DataNotExistException(Setting.class, "name", setting.getName());
    }
    return modifyTime;
  }
}
