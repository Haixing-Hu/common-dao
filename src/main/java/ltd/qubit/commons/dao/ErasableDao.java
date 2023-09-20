////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.mapper.ErasableMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.util.Identifiable;

import static ltd.qubit.commons.dao.DaoImplHelper.eraseByKeyImpl;

/**
 * This interface represents a DAO that implements the deletion operation.
 *
 * <p>This interface implements the following DAO operations:</p>
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
public interface ErasableDao<T extends Identifiable> extends Dao<T> {

  @Override
  ErasableMapper<T> getMapper();

  /**
   * Completely erases the specified entity from the database.
   * <p>
   * <b>Note: </b>This operation will actually remove the specified entity
   * from the database, and the deleted entity cannot be recovered.
   *
   * @param id
   *     The ID of the entity to be erased.
   * @throws DataNotExistException
   *     If the specified entity does not exist.
   * @throws DataAccessException
   *     If any other data access error occurs.
   * @see ClearableDao#clear()
   * @see DeletableDao#delete(Long)
   * @see DeletableDao#restore(Long)
   * @see DeletableDao#purge(Long)
   * @see DeletableDao#purgeAll()
   */
  default void erase(final Long id) throws DataAccessException {
    eraseByKeyImpl(this, t -> getMapper().erase(id), "id", id);
  }
}
