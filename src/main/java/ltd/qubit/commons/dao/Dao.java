////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import ltd.qubit.commons.annotation.NoAutoTest;
import ltd.qubit.commons.dao.mapper.Mapper;
import ltd.qubit.commons.model.HasClock;
import ltd.qubit.commons.model.HasLogger;

/**
 * Common root interface for all DAO interfaces.
 *
 * @param <T>
 *     The type of entity accessed by this DAO.
 * @author 胡海星
 */
public interface Dao<T> extends HasLogger, HasClock {

  /**
   * Gets the type of entity accessed by the current DAO.
   *
   * @return
   *     The type of entity being accessed by the current DAO.
   */
  @NoAutoTest
  Class<T> getEntityClass();

  /**
   * Get the name of the entity accessed by the current DAO.
   *
   * @return
   *     The name of the entity being accessed by the current DAO.
   */
  @NoAutoTest
  default String getEntityName() {
    return getEntityClass().getSimpleName();
  }

  /**
   * Generate the next ID.
   *
   * @return
   *     The next unique ID.
   */
  @NoAutoTest
  long generateId();

  /**
   * Get MyBatis Mapper for implementing DAOs.
   *
   * <p><b>Note:</b> This interface should be overridden by sub-interfaces of
   * the {@link Dao} interface.</p>
   *
   * @return
   *     The MyBatis Mapper for implementing DAOs.
   */
  @NoAutoTest
  Mapper<T> getMapper();
}
