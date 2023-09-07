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
import ltd.qubit.commons.dao.mapper.AddableMapper;
import ltd.qubit.commons.error.DuplicateKeyException;
import ltd.qubit.commons.error.FieldTooLongException;
import ltd.qubit.commons.error.FieldValueOutOfRangeException;
import ltd.qubit.commons.error.ForeignKeyConstraintFailException;
import ltd.qubit.commons.error.InvalidFieldFormatException;
import ltd.qubit.commons.error.NullFieldException;

import static ltd.qubit.commons.dao.DaoImplHelper.addImpl;

/**
 * This interface represents a DAO that implements the adding entity operation.
 * <p>
 * This interface implements the following DAO operations:
 * <ul>
 * <li>{@link #add(T)}: Adds a new entity.</li>
 * </ul>
 *
 * @param <T>
 *     The type of entity being manipulated.
 * @author Haixing Hu
 */
public interface AddableDao<T> extends Dao<T> {

  @Override
  AddableMapper<T> getMapper();

  /**
   * Adds a new entity.
   *
   * @param obj
   *     The entity object to be added.
   * @return
   *     The timestamp when the data was added.
   * @throws NullFieldException
   *     If the database field corresponding to an attribute of the entity
   *     should not be empty but its attribute value is empty.
   * @throws InvalidFieldFormatException
   *     If an attribute value of the entity does not conform to the format
   *     required by its corresponding database field.
   * @throws FieldTooLongException
   *     If the length of an attribute value of the entity exceeds the length
   *     allowed by its corresponding database field.
   * @throws FieldValueOutOfRangeException
   *     If the range of an attribute value of the entity exceeds the range
   *     allowed by its corresponding database field.
   * @throws DuplicateKeyException
   *     If the database field corresponding to an attribute value of the entity
   *     is required to be unique, but the attribute value is the same as an
   *     existing object in the database.
   * @throws ForeignKeyConstraintFailException
   *     If the database field corresponding to an attribute is associated with
   *     an associated field of another associated table, but the attribute
   *     value does not exist in the associated field of the associated table.
   * @throws DataAccessException
   *     If other uncategorizable database operation errors occur.
   */
  @Modified({"id", "createTime", "modifyTime", "deleteTime"})
  default Instant add(final T obj) throws DataAccessException {
    return addImpl(this, obj);
  }
}
