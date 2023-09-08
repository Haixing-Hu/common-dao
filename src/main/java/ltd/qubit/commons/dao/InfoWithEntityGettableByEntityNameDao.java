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

import ltd.qubit.commons.dao.mapper.InfoWithEntityGettableByEntityNameMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.HasInfoWithEntity;
import ltd.qubit.commons.model.InfoWithEntity;
import ltd.qubit.commons.util.pair.KeyValuePair;

import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有{@code info}属性的实体类实现了查询操作的DAO。
 *
 * <p><b>注意：</b>对象的{@code name}在同一个{@code entity}下必须唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #getInfoByName(String, String)}，此函数返回一个{@link InfoWithEntity}对象</li>
 * <li>{@link #getInfoByNameOrNull(String, String)}，此函数返回一个
 * {@link InfoWithEntity}对象或{@code null}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface InfoWithEntityGettableByEntityNameDao<T extends HasInfoWithEntity>
    extends Dao<T> {

  @Override
  InfoWithEntityGettableByEntityNameMapper<T> getMapper();

  /**
   * 根据指定实体类和名称获取指定的对象的基本信息。
   *
   * @param entity
   *     指定的实体类。
   * @param name
   *     指定的对象的名称。
   * @return
   *     具有指定实体类和名称的对象的基本信息。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @NotNull
  default InfoWithEntity getInfoByName(final String entity, final String name)
      throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> getMapper().getInfoByName(entity, name),
        "info", "name", name, new KeyValuePair("entity", entity));
  }

  /**
   * 根据指定实体类和名称获取指定的对象的基本信息。
   *
   * @param entity
   *     指定的实体类。
   * @param name
   *     指定的对象的名称。
   * @return
   *     具有指定实体类和名称的对象的基本信息，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default InfoWithEntity getInfoByNameOrNull(final String entity, final String name)
      throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getInfoByName(entity, name),
        "info", "name", name, new KeyValuePair("entity", entity));
  }
}
