////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.mapper;

import java.time.Instant;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.error.DuplicateKeyException;
import ltd.qubit.commons.error.FieldTooLongException;
import ltd.qubit.commons.error.FieldValueOutOfRangeException;
import ltd.qubit.commons.error.ForeignKeyConstraintFailException;
import ltd.qubit.commons.error.InvalidFieldFormatException;
import ltd.qubit.commons.error.NullFieldException;
import ltd.qubit.commons.model.Stateful;
import ltd.qubit.commons.model.WithCode;
import ltd.qubit.model.commons.State;

/**
 * 此接口表示对有状态的实体类实现了更新状态操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>对象的编码在其所属父对象下必须唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #updateStateByCode(Long, String, State, Instant)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型，必须实现{@link Stateful}接口。
 * @author 胡海星
 */
public interface StateUpdatableByParentCodeMapper<T extends WithCode & Stateful>
    extends Mapper<T> {

  /**
   * 更新一个已存在的实体的状态。
   *
   * @param parentId
   *     指定的对象所属的父对象的ID。
   * @param code
   *     待更新的已存在的实体对象的编码。
   * @param state
   *     待更新的新的状态。
   * @param modifyTime
   *     对象被更新时的时间戳。
   * @return
   *     此操作所更新的对象的数目，若指定的对象不存在或已被标记删除则返回0。
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
  long updateStateByCode(@Param("parentId") Long parentId,
      @Param("code") String code,
      @Param("state") State state,
      @Param("modifyTime") Instant modifyTime) throws DataAccessException;
}
