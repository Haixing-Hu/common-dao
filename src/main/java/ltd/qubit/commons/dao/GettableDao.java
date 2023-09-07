////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import javax.annotation.Nullable;

import jakarta.validation.constraints.NotNull;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.mapper.GettableMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.Identifiable;

import static ltd.qubit.commons.dao.DaoImplHelper.existKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyOrNullImpl;

/**
 * This interface represents a DAO that implements a query operation on an
 * entity class with an ID property.
 *
 * <p>This interface implements the following DAO operations:</p>
 * <ul>
 * <li>{@link #exist(Long)}: Tests whether there exist an entity with the
 * specified ID.</li>
 * <li>{@link #get(Long)}: Gets the entity with the specified ID, and throws a
 * {@link DataNotExistException} if there is no such entity.</li>
 * <li>{@link #getOrNull(Long)}： Gets the entity with the specified ID, and
 * returns {@code null} if there is no such entity.</li>
 * </ul>
 *
 * @param <T>
 *     The type of entities being operated on, which must implement the
 *     {@link Identifiable} interface.
 * @author Haixing Hu
 */
public interface GettableDao<T extends Identifiable> extends Dao<T> {

  @Override
  GettableMapper<T> getMapper();

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
  default boolean exist(final Long id) throws DataAccessException {
    return existKeyImpl(this, () -> getMapper().exist(id), "id", id);
  }

  /**
   * Gets the entity with the specified ID, and throws a
   * {@link DataNotExistException} if there is no such entity.
   *
   * @param id
   *     The ID of the specified entity.
   * @return
   *     The entity with the specified ID.
   * @throws DataNotExistException
   *     If the specified entity does not exist.
   * @throws DataAccessException
   *     If other data access errors occur.
   */
  @NotNull
  default T get(final Long id) throws DataAccessException {
    return getByKeyImpl(this, () -> getMapper().get(id), "id", id);
  }

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
  default T getOrNull(final Long id) throws DataAccessException {
    return getByKeyOrNullImpl(this, () -> getMapper().get(id), "id", id);
  }
}
