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

import ltd.qubit.commons.dao.mapper.AddOrUpdatableByParentNameMapper;
import ltd.qubit.commons.error.DuplicateKeyException;
import ltd.qubit.commons.error.FieldTooLongException;
import ltd.qubit.commons.error.FieldValueOutOfRangeException;
import ltd.qubit.commons.error.ForeignKeyConstraintFailException;
import ltd.qubit.commons.error.InvalidFieldFormatException;
import ltd.qubit.commons.error.NullFieldException;
import ltd.qubit.commons.model.WithName;

/**
 * 此接口表示实现根据名称添加或更新实体操作的DAO。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #existName(String, String)}</li>
 * <li>{@link #getByName(String, String)}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #updateByName(WithName)}</li>
 * <li>{@link #addOrUpdateByName(T)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface AddOrUpdatableByParentNameDao<T extends WithName>
    extends GettableByParentNameDao<T>, AddableDao<T>, UpdatableByParentNameDao<T> {

  @Override
  AddOrUpdatableByParentNameMapper<T> getMapper();

  /**
   * 增加一个新的实体或根据名称更新已有的实体。
   *
   * @param obj
   *     待增加或更新的实体，更新时使用其<b>所属父对象的ID</b>及其<b>名称</b>确定该对象；
   *     若该对象不存在，则添加一个新对象；否则用此参数更新存在的对象。
   * @return 数据被创建或被修改时的时间戳。
   * @throws NullFieldException
   *     若新增或更新的对象的任意必须字段为空。
   * @throws InvalidFieldFormatException
   *     若新增或更新的对象的某个字段格式不正确。
   * @throws FieldTooLongException
   *     若新增或更新的对象的某个字段值的长度超过了数据库表中该字段允许的长度。
   * @throws FieldValueOutOfRangeException
   *     若新增或更新的对象的某个字段值超过了数据库表中该字段允许的取值范围。
   * @throws DuplicateKeyException
   *     若新增或更新的对象的某个字段的取值与数据库中已有数据该字段的取值相同，且该字段的取值不
   *     可重复。
   * @throws ForeignKeyConstraintFailException
   *     若新增或更新的对象的某个字段值应该是对另一张表的某个字段的引用，但所引用的对象却不存在。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   */
  Instant addOrUpdateByName(T obj) throws DataAccessException;

}
