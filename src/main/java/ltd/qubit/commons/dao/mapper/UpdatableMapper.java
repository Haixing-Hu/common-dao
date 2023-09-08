////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.mapper;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.error.DuplicateKeyException;
import ltd.qubit.commons.error.FieldTooLongException;
import ltd.qubit.commons.error.FieldValueOutOfRangeException;
import ltd.qubit.commons.error.ForeignKeyConstraintFailException;
import ltd.qubit.commons.error.InvalidFieldFormatException;
import ltd.qubit.commons.error.NullFieldException;
import ltd.qubit.commons.model.Identifiable;

/**
 * This interface represents a MyBatis Mapper that implements the updating
 * operation.
 *
 * <p>This interface implements the following Mapper operations:</p>
 * <ul>
 * <li>{@link #update(T)}: Updates an existing entity.</li>
 * </ul>
 *
 * @param <T>
 *     The type of entities being operated on, which must implement the
 *     {@link Identifiable} interface.
 * @author Haixing Hu
 */
public interface UpdatableMapper<T extends Identifiable> extends Mapper<T> {

  /**
   * Updates an existing entity.
   *
   * @param obj
   *     The new data of the existing entity to be updated, identified by its ID.
   * @return
   *     The number of entities updated by this operation, or 0 if the specified
   *     entity does not exist or has been marked as deleted.
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
  long update(T obj) throws DataAccessException;
}
