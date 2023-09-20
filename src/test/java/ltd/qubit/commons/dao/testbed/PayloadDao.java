////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed;

import java.time.Instant;
import java.util.List;

import javax.annotation.Nullable;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.annotation.NoAutoTest;
import ltd.qubit.commons.annotation.Unmodified;
import ltd.qubit.commons.dao.testbed.mapper.PayloadMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.error.DuplicateKeyException;
import ltd.qubit.commons.error.FieldTooLongException;
import ltd.qubit.commons.error.FieldValueOutOfRangeException;
import ltd.qubit.commons.error.ForeignKeyConstraintFailException;
import ltd.qubit.commons.error.InvalidFieldFormatException;
import ltd.qubit.commons.error.NullFieldException;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.commons.Owner;
import ltd.qubit.model.commons.Payload;

/**
 * 对{@link Payload}对象进行数据库存取的DAO接口。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #countForOwner(Owner)} </li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #getForOwner(Owner)}</li>
 * <li>{@link #getIdForOwner(Owner)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #add(Identifiable)}</li>
 * <li>{@link #update(Payload)}</li>
 * <li>{@link #updateByKey(Payload)}</li>
 * <li>{@link #delete(Long)}</li>
 * <li>{@link #deleteForOwner(Owner)}</li>
 * <li>{@link #restore(Long)}</li>
 * <li>{@link #restoreForOwner(Owner)}</li>
 * <li>{@link #purge(Long)}</li>
 * <li>{@link #purgeForOwner(Owner)}</li>
 * <li>{@link #purgeAll()}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #eraseForOwner(Owner)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @author 胡海星
 */
public interface PayloadDao extends BasicWithOwnerDao<Payload> {

  @Override
  PayloadMapper getMapper();

  @Unmodified({"id", "key", "owner", "createTime", "deleteTime"})
  Instant update(Payload obj) throws DataAccessException;

  /**
   * 根据所有者({@code ownerType}, @code ownerID})和主键({@code key})更新一个已存在的
   * {@link Payload}实体。
   *
   * @param obj
   *     待更新的已存在的实体对象的新数据，更新时使用其所有者({@code ownerType},
   *     {@code ownerID})和主键({@code key})确定该对象。
   * @return
   *     数据被修改时的时间戳。
   * @throws DataNotExistException
   *     若指定的实体对象不存在。
   * @throws NullFieldException
   *     如果该实体的某个属性对应的字段非空但其属性值为空。
   * @throws InvalidFieldFormatException
   *     如果该实体的某个属性值不符合其对应的字段所需的格式。
   * @throws FieldTooLongException
   *     如果该实体的某个属性值长度超过了其对应字段允许的长度。
   * @throws FieldValueOutOfRangeException
   *     如果该实体的某个属性值的范围超出了其对应字段允许的范围。
   * @throws DuplicateKeyException
   *     如果该实体的某个属性值对应的字段要求唯一，但该属性值和数据库中已存在的对象重复。
   * @throws ForeignKeyConstraintFailException
   *     如果某个属性对应的字段关联了另一张关联表的某个关联字段，而该属性值在关联表的关联字段
   *     中不存在。
   * @throws DataAccessException
   *     如果发生其他无法归类的数据库操作错误。
   */
  @Unmodified({"id", "key", "owner", "createTime", "deleteTime"})
  Instant updateByKey(Payload obj) throws DataAccessException;

  @NoAutoTest
  <T extends Identifiable>
  Instant addForOwner(T obj, String property, @Nullable Payload payload)
      throws DataAccessException;

  @NoAutoTest
  <T extends Identifiable>
  Instant addForOwner(T obj, String property, @Nullable List<Payload> payloads)
      throws DataAccessException;

  @NoAutoTest
  <T extends Identifiable>
  Instant updateForOwner(T obj, String property, String key,
      @Nullable Payload payload) throws DataAccessException;

  @NoAutoTest
  <T extends Identifiable>
  Instant updateForOwner(T obj, String property,
      @Nullable List<Payload> payloads) throws DataAccessException;
}
