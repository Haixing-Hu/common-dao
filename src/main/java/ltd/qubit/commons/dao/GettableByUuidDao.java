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

import ltd.qubit.commons.dao.mapper.GettableByUuidMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.WithUuid;

import static ltd.qubit.commons.dao.DaoImplHelper.existKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyOrNullImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有{@code uuid}属性的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>对象的UUID必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #existUuid(String)}</li>
 * <li>{@link #getByUuid(String)}</li>
 * <li>{@link #getByUuidOrNull(String)}</li>
 * <li>{@link #getIdByUuid(String)}</li>
 * <li>{@link #getIdByUuidOrNull(String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface GettableByUuidDao<T extends WithUuid> extends Dao<T> {

  @Override
  GettableByUuidMapper<T> getMapper();

  /**
   * 判定拥有指定UUID的对象是否存在。
   *
   * @param uuid
   *     指定的UUID。
   * @return
   *     若拥有指定UUID的对象存在，则返回{@code true}；否则返回{@code false}。
   * @throws DataAccessException
   *     如果出现任何数据存取错误。
   */
  default boolean existUuid(final String uuid) throws DataAccessException {
    return existKeyImpl(this, () -> getMapper().existUuid(uuid),
        "uuid", uuid);
  }

  /**
   * 根据UUID获取指定的对象。
   *
   * @param uuid
   *     指定的对象的UUID。
   * @return
   *     具有指定UUID的对象。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default T getByUuid(final String uuid) throws DataAccessException {
    return getByKeyImpl(this, () -> getMapper().getByUuid(uuid),
        "uuid", uuid);
  }

  /**
   * 根据UUID获取指定的对象。
   *
   * @param uuid
   *     指定的对象的UUID。
   * @return
   *     具有指定UUID的对象，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default T getByUuidOrNull(final String uuid) throws DataAccessException {
    return getByKeyOrNullImpl(this, () -> getMapper().getByUuid(uuid),
         "uuid", uuid);
  }

  /**
   * 根据UUID获取指定的对象的ID。
   *
   * @param uuid
   *     指定的对象的UUID。
   * @return
   *     具有指定UUID的对象的ID。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default Long getIdByUuid(final String uuid) throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> getMapper().getIdByUuid(uuid),
        "id", "uuid", uuid);
  }

  /**
   * 根据UUID获取指定的对象的ID。
   *
   * @param uuid
   *     指定的对象的UUID。
   * @return
   *     具有指定UUID的对象的ID，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Long getIdByUuidOrNull(final String uuid) throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getIdByUuid(uuid),
        "id", "uuid", uuid);
  }
}
