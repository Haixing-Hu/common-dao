////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.impl;

import java.time.Clock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ltd.qubit.commons.dao.Dao;
import ltd.qubit.commons.id.IdGenerator;

/**
 * 实现所有DAO接口的基类。
 *
 * @param <T>
 *     该DAO所管理的实体的类型。
 * @author 胡海星
 */
public abstract class DaoImpl<T> implements Dao<T> {

  /**
   * 打印日志用的logger。
   */
  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * 自动注入一个{@link Clock}对象以提供系统时间。
   */
  protected Clock clock;

  /**
   * 自动注入一个{@link IdGenerator}。
   */
  protected IdGenerator idGenerator;

  /**
   * 该DAO所管理的实体的类型的类对象。
   */
  protected final Class<T> entityClass;

  public DaoImpl(final Class<T> entityClass) {
    this.entityClass = entityClass;
  }

  @Override
  public Class<T> getEntityClass() {
    return entityClass;
  }

  public Logger getLogger() {
    return logger;
  }

  public Clock getClock() {
    return clock;
  }

  @Autowired
  public void setClock(final Clock clock) {
    this.clock = clock;
  }

  @Autowired
  public void setIdGenerator(final IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public long generateId() {
    return idGenerator.generate();
  }
}
