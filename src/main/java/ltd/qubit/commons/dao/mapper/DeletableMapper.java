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

import ltd.qubit.commons.model.Deletable;
import ltd.qubit.commons.model.Identifiable;

/**
 * This interface represents a MyBatis Mapper that implements mark deletion
 * operations.
 * <p>
 * This interface implements the following Mapper operations:
 * <ul>
 * <li>{@link #existNonDeleted(Long)}: Tests whether there exists the entity
 * with the specified ID that has not been marked as deleted.</li>
 * <li>{@link #delete(Long, Instant)}: Marks the specified entity as deleted.</li>
 * <li>{@link #restore(Long)}: Restores the specified mark deleted entity.</li>
 * <li>{@link #purge(Long)}: Completely removes the specified mark deleted
 * entity from the database.</li>
 * <li>{@link #purgeAll()}: Completely removes <b>all</b> mark deleted entities
 * from the database</li>
 * </ul>
 *
 * @param <T>
 *     The type of entities being operated on, which must implement the
 *     {@link Identifiable} and {@link Deletable} interface.
 * @author Haixing Hu
 */
public interface DeletableMapper<T extends Identifiable & Deletable>
    extends Mapper<T> {

  /**
   * Tests whether there exists the entity with the specified ID that has not
   * been marked as deleted.
   *
   * @param id
   *     The ID of the specified entity.
   * @return
   *     Returns {@code true} if there exists the entity with the specified ID
   *     that has not been marked as deleted; returns {@code false} otherwise.
   * @throws DataAccessException
   *     If any data access error occurs.
   */
  boolean existNonDeleted(@Param("id") Long id) throws DataAccessException;

  /**
   * Marks the specified entity as deleted.
   *
   * <p><b>Note: </b>This operation does not actually remove the specified
   * object from the database, instead it marks it as deleted and records the
   * timestamp when it was marked.
   *
   * @param id
   *     The ID of the entity to be marked as deleted.
   * @param deleteTime
   *     The timestamp when the entity was marked.
   * @return
   *     The number of entities marked by this operation.
   * @throws DataAccessException
   *     If any other data access error occurs.
   * @see #purge(Long)
   * @see #purgeAll()
   * @see #restore(Long)
   * @see ErasableMapper#erase(Long)
   * @see ClearableMapper#clear()
   */
  long delete(@Param("id") Long id, @Param("deleteTime") Instant deleteTime)
      throws DataAccessException;

  /**
   * Restores the specified mark deleted entity.
   *
   * <p><b>Note: </b>This operation can only restore entities marked as deleted.
   * If the entity has been completely removed from the database, it cannot be
   * restored.
   *
   * @param id
   *     The ID of the entity to be restored.
   * @return
   *     The number of entities restored by this operation. If the specified
   *     entity does not exist, or the entity has not been marked as deleted,
   *     this function returns 0.
   * @throws DataAccessException
   *     If any other data access error occurs.
   * @see #delete(Long, Instant)
   * @see #purge(Long)
   * @see #purgeAll()
   * @see ErasableMapper#erase(Long)
   * @see ClearableMapper#clear()
   */
  long restore(@Param("id") Long id) throws DataAccessException;

  /**
   * Completely removes the specified mark deleted entity from the database.
   *
   * <p><b>Note: </b>This operation will completely remove the mark deleted
   * entities from the database. The removed entity cannot be restored.
   *
   * @param id
   *     The ID of the mark deleted entity to be purged.
   * @return
   *     The number of entities removed by this operation. If the specified
   *     entity does not exist, or the entity has not been marked as deleted,
   *     this function returns 0.
   * @throws DataAccessException
   *     If any other data access error occurs.
   * @see #delete(Long, Instant)
   * @see #restore(Long)
   * @see #purgeAll()
   * @see ErasableMapper#erase(Long)
   * @see ClearableMapper#clear()
   */
  long purge(@Param("id") Long id) throws DataAccessException;

  /**
   * Completely removes <b>all</b> mark deleted entities from the database.
   *
   * <p><b>Note: </b>This operation will completely remove <b>all</b> mark
   * deleted entities from the database. The removed entities cannot be restored.
   *
   * @return
   *     The number of entities removed by this operation.
   * @throws DataAccessException
   *     If any other data access error occurs.
   * @see #delete(Long, Instant)
   * @see #restore(Long)
   * @see #purge(Long)
   * @see ErasableMapper#erase(Long)
   * @see ClearableMapper#clear()
   */
  long purgeAll() throws DataAccessException;
}
