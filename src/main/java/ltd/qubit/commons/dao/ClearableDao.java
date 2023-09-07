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

import ltd.qubit.commons.dao.mapper.ClearableMapper;

import static ltd.qubit.commons.dao.DaoImplHelper.clearImpl;

/**
 * This interface represents the DAO that implements the operation of clearing
 * the table corresponding to the entity in the database.
 *
 * <p>This interface implements the following DAO operations:</p>
 * <ul>
 * <li>{@link #clear()}: Completely removes <b>all</b> entities from the
 * database.</li>
 * </ul>
 *
 * @param <T>
 *     The type of entities being manipulated.
 * @author Haixing Hu
 */
public interface ClearableDao<T> extends Dao<T> {

  @Override
  ClearableMapper<T> getMapper();

  /**
   * Completely removes <b>all</b> entities from the database.
   * <p>
   * <b>Note: </b>This operation will completely remove <b>all</b> entities from
   * the database, that is, empty the entire database table. This operation is
   * irreversible.
   *
   * @return
   *     The number of entities removed by this operation.
   * @throws DataAccessException
   *     if any other data access error occurs.
   * @see ErasableDao#erase(Long)
   * @see DeletableDao#delete(Long)
   * @see DeletableDao#restore(Long)
   * @see DeletableDao#purge(Long)
   * @see DeletableDao#purgeAll()
   */
  default long clear() throws DataAccessException {
    return clearImpl(this);
  }
}
