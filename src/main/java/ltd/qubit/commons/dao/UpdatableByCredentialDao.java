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

import ltd.qubit.commons.annotation.Unmodified;
import ltd.qubit.commons.dao.mapper.UpdatableByCredentialMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.error.DuplicateKeyException;
import ltd.qubit.commons.error.FieldTooLongException;
import ltd.qubit.commons.error.FieldValueOutOfRangeException;
import ltd.qubit.commons.error.ForeignKeyConstraintFailException;
import ltd.qubit.commons.error.InvalidFieldFormatException;
import ltd.qubit.commons.error.NullFieldException;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.Modifiable;
import ltd.qubit.commons.model.Normalizable;
import ltd.qubit.commons.model.WithCredential;

/**
 * 此接口表示实现根据证件更新实体操作的DAO。
 *
 * <p><b>注意：</b>对象的证件必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #updateByCredential(T)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface UpdatableByCredentialDao<T extends Identifiable & WithCredential>
    extends UpdatableDao<T> {

  @Override
  UpdatableByCredentialMapper<T> getMapper();

  /**
   * 根据证件更新一个已存在的实体。
   *
   * @param obj
   *     待更新的已存在的实体对象的新数据，更新时使用其<b>证件</b>确定该对象。
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
   *     如果某个属性对应的字段关联了另一张关联表的某个关联字段，而该属性值在关联表的关
   *     联字段中不存在。
   * @throws DataAccessException
   *     如果发生其他无法归类的数据库操作错误。
   */
  @Unmodified({"id", "code", "entity", "app", "owner", "username", "credential",
      "password", "visible", "enabled", "state", "creator", "createTime",
      "deleter", "deleteTime"})
  default Instant updateByCredential(final T obj) throws DataAccessException {
    final Long id = getMapper().getIdByCredential(obj.getCredential());
    if (id == null) {
      throw new DataNotExistException(getEntityClass(), "credential", obj.getCredential());
    }
    obj.setId(id);
    if (obj instanceof Normalizable) {
      ((Normalizable) obj).normalize();
    }
    final Instant modifyTime = now();
    if (obj instanceof Modifiable) {
      ((Modifiable) obj).setModifyTime(modifyTime);
    }
    getLogger().debug("Update {}: {}", getEntityName(), obj);
    final long count = getMapper().update(obj);
    if (count == 0) {
      throw new DataNotExistException(getEntityClass(), "credential", obj.getCredential());
    }
    return modifyTime;
  }
}
