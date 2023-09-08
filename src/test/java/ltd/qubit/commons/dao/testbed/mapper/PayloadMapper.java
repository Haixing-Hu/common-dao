////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed.mapper;

import java.time.Instant;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.mapper.BasicWithOwnerMapper;
import ltd.qubit.commons.dao.testbed.PayloadDao;
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
 * 实现{@link PayloadDao}的MyBatis Mapper。
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #countForOwner(Owner)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #getForOwner(Owner)}</li>
 * <li>{@link #getIdForOwner(Owner)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #add(Identifiable)}</li>
 * <li>{@link #update(Identifiable)}</li>
 * <li>{@link #updateByKey(Payload)}</li>
 * <li>{@link #delete(Long, Instant)}</li>
 * <li>{@link #deleteForOwner(Owner, Instant)}</li>
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
public interface PayloadMapper extends BasicWithOwnerMapper<Payload> {

  boolean existKey(@Param("owner") Owner owner, @Param("key") String key)
      throws DataAccessException;

  /**
   * 根据所有者和主键更新一个已存在的实体。
   *
   * @param entity
   *     待更新的已存在的实体对象的新数据，更新时使用其所有者({@code owner}和主键
   *     ({@code key})确定该对象。
   * @return
   *     此操作所更新的对象的数目，若指定的对象不存在或已被标记删除则返回0。
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
  long updateByKey(Payload entity) throws DataAccessException;


  long eraseByKey(@Param("owner") Owner owner, @Param("key") String key)
      throws DataAccessException;
}
