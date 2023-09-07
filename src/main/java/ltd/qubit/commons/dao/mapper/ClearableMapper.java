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

import org.springframework.dao.DataAccessException;

/**
 * This interface represents the MyBatis Mapper that implements the operation
 * of clearing the table corresponding to the entity in the database.
 *
 * <p>This interface implements the following Mapper operations:</p>
 * <ul>
 * <li>{@link #clear()}: Completely removes <b>all</b> entities from the
 * database.</li>
 * </ul>
 *
 * @param <T>
 *     The type of entities being manipulated.
 * @author Haixing Hu
 */
public interface ClearableMapper<T> extends Mapper<T> {

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
   * @see ErasableMapper#erase(Long)
   * @see DeletableMapper#delete(Long, Instant)
   * @see DeletableMapper#restore(Long)
   * @see DeletableMapper#purge(Long)
   * @see DeletableMapper#purgeAll()
   */
  long clear() throws DataAccessException;
}
