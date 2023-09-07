////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.mapper;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;

/**
 * This interface represents a MyBatis Mapper that implements the list query
 * operations.
 * <p>
 * This interface implements the following Mapper operations:
 * <ul>
 * <li>{@link #count(Criterion)}: Get the number of entities that match the
 * specified criteria.</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}：Lists the specified
 * subsequence of entities that match the specified criteria.</li>
 * </ul>
 *
 * @param <T>
 *     The type of entity being manipulated.
 * @author Haixing Hu
 */
public interface ListableMapper<T> extends Mapper<T> {

  /**
   * Get the number of entities that match the specified criteria.
   *
   * @param filter
   *     The criteria used to filter entities. A {@code null} value indicates
   *     no restriction.
   * @return
   *     The number of all entities matching the filtering criteria.
   * @throws DataAccessException
   *     If any data access error occurs.
   */
  long count(@Param("filter") @Nullable Criterion<T> filter)
      throws DataAccessException;

  /**
   * Lists the specified subsequence of entities that match the specified
   * criteria.
   *
   * @param filter
   *     The criteria used to filter entities. A {@code null} value indicates
   *     no restriction.
   * @param sortRequest
   *     Specify the sorting field and sorting method. If it is {@code null},
   *     the default sorting will be used.
   * @param limit
   *     Specifies the maximum length of the subsequence to be returned. A
   *     {@code null} value indicates no limit.
   * @param offset
   *     Specifies the index (starting from 0) of the first element of the
   *     subsequence to be returned in the sequence of all eligible entities.
   *     A {@code null} value indicates the default offset 0.
   * @return
   *     The specified subsequence of eligible entities, sorted by the
   *     specified sorting order. If no entity meets the criteria, an empty
   *     list is returned.
   * @throws DataAccessException
   *     If any data access error occurs.
   */
  List<T> list(@Param("filter") @Nullable Criterion<T> filter,
      @Param("sortRequest") @Nullable SortRequest<T> sortRequest,
      @Param("limit") @Nullable Integer limit,
      @Param("offset") @Nullable Long offset)
      throws DataAccessException;
}
