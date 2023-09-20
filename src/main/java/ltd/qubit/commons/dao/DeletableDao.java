////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import java.time.Instant;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.annotation.Modified;
import ltd.qubit.commons.dao.mapper.DeletableMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.util.Deletable;
import ltd.qubit.commons.model.util.Identifiable;

import static ltd.qubit.commons.dao.DaoImplHelper.deleteByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.existNonDeletedKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.purgeAllImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.purgeByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.restoreByKeyImpl;

/**
 * This interface represents a DAO that implements mark deletion operations on
 * entities.
 * <p>
 * This interface implements the following DAO operations:
 * <ul>
 * <li>{@link #existNonDeleted(Long)}: Tests whether there exists the entity
 * with the specified ID that has not been marked as deleted.</li>
 * <li>{@link #delete(Long)}: Marks the specified entity as deleted.</li>
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
public interface DeletableDao<T extends Identifiable & Deletable> extends Dao<T> {

  @Override
  DeletableMapper<T> getMapper();

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
  default boolean existNonDeleted(final Long id) throws DataAccessException {
    return existNonDeletedKeyImpl(this, () -> getMapper().existNonDeleted(id), "id", id);
  }

  /**
   * Marks the specified entity as deleted.
   *
   * <p><b>Note: </b>This operation does not actually remove the specified
   * object from the database, instead it marks it as deleted and records the
   * timestamp when it was marked.
   *
   * @param id
   *     The ID of the entity to be marked as deleted.
   * @return
   *     The timestamp when the entity was marked.
   * @throws DataNotExistException
   *     If the specified entity does not exist or has already been marked as
   *     deleted.
   * @throws DataAccessException
   *     If any other data access error occurs.
   * @see #purge(Long)
   * @see #purgeAll()
   * @see #restore(Long)
   * @see ErasableDao#erase(Long)
   * @see ClearableDao#clear()
   */
  @Modified("deleteTime")
  default Instant delete(final Long id) throws DataAccessException {
    return deleteByKeyImpl(this, (t) -> getMapper().delete(id, t), "id", id);
  }

  /**
   * Restores the specified mark deleted entity.
   *
   * <p><b>Note: </b>This operation can only restore entities marked as deleted.
   * If the entity has been completely removed from the database, it cannot be
   * restored.
   *
   * @param id
   *     The ID of the entity to be restored.
   * @throws DataNotExistException
   *     If the specified entity does not exist, or the entity has not been
   *     marked as deleted.
   * @throws DataAccessException
   *     If any other data access error occurs.
   * @see #delete(Long)
   * @see #purge(Long)
   * @see #purgeAll()
   * @see ErasableDao#erase(Long)
   * @see ClearableDao#clear()
   */
  @Modified("deleteTime")
  default void restore(final Long id) throws DataAccessException {
    restoreByKeyImpl(this, (t) -> getMapper().restore(id), "id", id);
  }

  /**
   * Completely removes the specified mark deleted entity from the database.
   *
   * <p><b>Note: </b>This operation will completely remove the mark deleted
   * entities from the database. The removed entity cannot be restored.
   *
   * @param id
   *     The ID of the mark deleted entity to be purged.
   * @throws DataNotExistException
   *     If the specified entity does not exist, or the entity has not been
   *     marked as deleted.
   * @throws DataAccessException
   *     If any other data access error occurs.
   * @see #delete(Long)
   * @see #restore(Long)
   * @see #purgeAll()
   * @see ErasableDao#erase(Long)
   * @see ClearableDao#clear()
   */
  default void purge(final Long id) throws DataAccessException {
    purgeByKeyImpl(this, (t) -> getMapper().purge(id), "id", id);
  }

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
   * @see #delete(Long)
   * @see #restore(Long)
   * @see #purge(Long)
   * @see ErasableDao#erase(Long)
   * @see ClearableDao#clear()
   */
  default long purgeAll() throws DataAccessException {
    return purgeAllImpl(this);
  }
}
