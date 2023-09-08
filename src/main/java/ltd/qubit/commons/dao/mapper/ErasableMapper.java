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

import ltd.qubit.commons.model.Identifiable;

/**
 * This interface represents the MyBatis Mapper that implements the operation of
 * erasing entities from the database.
 *
 * <p>This interface implements the following Mapper operations:</p>
 * <ul>
 * <li>{@link #erase(Long)}: Completely erases the specified entity from the
 * database.</li>
 * </ul>
 *
 * @param <T>
 *     The type of entities being operated on, which must implement the
 *     {@link Identifiable} interface.
 * @author Haixing Hu
 */
public interface ErasableMapper<T extends Identifiable> extends Mapper<T> {

  /**
   * Completely erases the specified entity from the database.
   * <p>
   * <b>Note: </b>This operation will actually remove the specified entity
   * from the database, and the deleted entity cannot be recovered.
   *
   * @param id
   *     The ID of the entity to be erased.
   * @return
   *     The number of entities erased by this operation, or 0 if the specified
   *     entity does not exist.
   * @throws DataAccessException
   *     If any other data access error occurs.
   * @see ClearableMapper#clear()
   * @see DeletableMapper#delete(Long, Instant)
   * @see DeletableMapper#restore(Long)
   * @see DeletableMapper#purge(Long)
   * @see DeletableMapper#purgeAll()
   */
  long erase(@Param("id") Long id) throws DataAccessException;
}
