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

import ltd.qubit.commons.dao.mapper.ClearableMapper;
import ltd.qubit.commons.dao.mapper.DeletableByCodeMapper;
import ltd.qubit.commons.dao.mapper.DeletableMapper;
import ltd.qubit.commons.dao.mapper.ErasableByCodeMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.WithCode;

import static ltd.qubit.commons.dao.DaoImplHelper.eraseByKeyImpl;

/**
 * 此接口表示实现删除实体操作的DAO。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #eraseByCode(String)} </li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface ErasableByCodeDao<T extends WithCode>
    extends Dao<T> {

  @Override
  ErasableByCodeMapper<T> getMapper();

  /**
   * 彻底删除指定的实体对象。
   *
   * <p><b>注意：</b>此操作将真正从数据库中删除指定的对象，被删除的对象不可恢复。
   *
   * @param code
   *     待彻底删除的实体对象的编码。
   * @throws DataNotExistException
   *     若指定的待删除对象不存在。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see ClearableMapper#clear()
   * @see DeletableMapper#delete(Long, Instant)
   * @see DeletableByCodeMapper#deleteByCode(String, Instant)
   * @see DeletableMapper#restore(Long)
   * @see DeletableByCodeMapper#restoreByCode(String)
   * @see DeletableMapper#purge(Long)
   * @see DeletableByCodeMapper#purgeByCode(String)
   * @see DeletableMapper#purgeAll()
   */
  default void eraseByCode(final String code) throws DataAccessException {
    eraseByKeyImpl(this, t -> getMapper().eraseByCode(code), "code", code);
  }
}
