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
import ltd.qubit.commons.dao.mapper.UpdatableByCodeMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.error.DuplicateKeyException;
import ltd.qubit.commons.error.FieldTooLongException;
import ltd.qubit.commons.error.FieldValueOutOfRangeException;
import ltd.qubit.commons.error.ForeignKeyConstraintFailException;
import ltd.qubit.commons.error.InvalidFieldFormatException;
import ltd.qubit.commons.error.NullFieldException;
import ltd.qubit.commons.model.WithCode;

import static ltd.qubit.commons.dao.DaoImplHelper.updateByKeyImpl;

/**
 * 此接口表示实现根据编码更新实体操作的DAO。
 *
 * <p><b>注意：</b>对象的编码必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #updateByCode(T)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface UpdatableByCodeDao<T extends WithCode> extends Dao<T> {

  @Override
  UpdatableByCodeMapper<T> getMapper();

  /**
   * 根据编码更新一个已存在的实体。
   *
   * @param obj
   *     待更新的已存在的实体对象的新数据，更新时使用其<b>编码</b>确定该对象。
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
  @Unmodified({"id", "code", "entity", "app", "owner", "username",
      "password", "visible", "enabled", "state", "creator", "createTime",
      "deleter", "deleteTime"})
  default Instant updateByCode(final T obj) throws DataAccessException {
    return updateByKeyImpl(this, modifyTime -> getMapper().updateByCode(obj),
        obj, "code", obj.getCode());
  }
}
