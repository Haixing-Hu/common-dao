////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import java.util.List;

import javax.annotation.Nullable;

import jakarta.validation.constraints.NotNull;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.mapper.ListableMapper;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;

import static ltd.qubit.commons.dao.DaoImplHelper.countImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.listImpl;

/**
 * This interface represents a DAO that can perform list query operations.
 * <p>
 * This interface implements the following DAO operations:
 * <ul>
 * <li>{@link #count(Criterion)}: Get the number of entities that match the
 * specified criteria.</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}：Lists the specified
 * subsequence of entities that match the specified criteria.</li>
 * <li>{@link #listFirst(Criterion, SortRequest)}: List the first matching
 * entity.</li>
 * </ul>
 *
 * @param <T>
 *     The type of entity being manipulated.
 * @author Haixing Hu
 */
public interface ListableDao<T> extends Dao<T> {

  @Override
  ListableMapper<T> getMapper();

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
  default long count(@Nullable final Criterion<T> filter)
      throws DataAccessException {
    return countImpl(this, filter);
  }

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
  @NotNull
  default List<T> list(@Nullable final Criterion<T> filter,
      @Nullable final SortRequest<T> sortRequest, @Nullable final Integer limit,
      @Nullable final Long offset) throws DataAccessException {
    return listImpl(this, filter, sortRequest, limit, offset);
  }

  /**
   * List the first matching entity.
   *
   * @param filter
   *     The criteria used to filter entities. A {@code null} value indicates
   *     no restriction.
   * @param sortRequest
   *     Specify the sorting field and sorting method. If it is {@code null},
   *     the default sorting will be used.
   * @return
   *     The first entity that meets the condition according to the specified
   *     sorting order; if there is no entity that meets the condition, return
   *     {@code null}.
   * @throws DataAccessException
   *     If any data access error occurs.
   */
  @Nullable
  default T listFirst(@Nullable final Criterion<T> filter,
      @Nullable final SortRequest<T> sortRequest) throws DataAccessException {
    final List<T> list = listImpl(this, filter, sortRequest, 1, 0L);
    if (list.isEmpty()) {
      return null;
    } else {
      return list.get(0);
    }
  }
}
