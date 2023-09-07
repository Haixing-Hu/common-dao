////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.mapper;

import javax.annotation.Nullable;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.model.Identifiable;

/**
 * This interface represents a MyBatis Mapper that implements query operations
 * on entity classes with ID properties.
 *
 * <p>This interface implements the following Mapper operations:</p>
 * <ul>
 * <li>{@link #exist(Long)}: Tests whether there exist an entity with the
 * specified ID.</li>
 * <li>{@link #get(Long)}： Gets the entity with the specified ID, and
 * returns {@code null} if there is no such entity.</li>
 * </ul>
 *
 * @param <T>
 *     The type of entities being operated on, which must implement the
 *     {@link Identifiable} interface.
 * @author Haixing Hu
 */
public interface GettableMapper<T extends Identifiable> extends Mapper<T> {

  /**
   * Tests whether there exists an entity with the specified ID.
   *
   * @param id
   *     The specified ID.
   * @return
   *     If the entity with the specified ID exists, return {@code true};
   *     otherwise return {@code false}.
   * @throws DataAccessException
   *     If any data access error occurs.
   */
  boolean exist(@Param("id") Long id) throws DataAccessException;

  /**
   * Gets the entity with the specified ID, and returns {@code null} if there
   * is no such entity.
   *
   * @param id
   *     The ID of the specified entity.
   * @return
   *     The entity with the specified ID, or {@code null} if no such entity.
   * @throws DataAccessException
   *     If other data access errors occur.
   */
  @Nullable
  T get(@Param("id") Long id) throws DataAccessException;
}
