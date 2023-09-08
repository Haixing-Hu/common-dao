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

import ltd.qubit.commons.annotation.Modified;
import ltd.qubit.commons.dao.mapper.ModifyTimeUpdatableMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.Modifiable;

import static ltd.qubit.commons.dao.DaoImplHelper.updateModifyTimeImpl;

public interface ModifyTimeUpdatableDao<T extends Identifiable & Modifiable>
    extends Dao<T> {

  @Override
  ModifyTimeUpdatableMapper<T> getMapper();

  /**
   * 更新一个已存在的实体的最后一次修改时间。
   *
   * @param id
   *     待更新的已存在的实体对象的ID。
   * @param modifyTime
   *     对象最后一次修改时间。
   * @return
   *     为保持接口标准一致性，返回数据被修改时的时间戳，即参数{@code modifyTime}。
   * @throws DataNotExistException
   *     若指定的实体对象不存在。
   * @throws DataAccessException
   *     如果发生其他无法归类的数据库操作错误。
   */
  @Modified("modifyTime")
  default Instant updateModifyTime(final Long id, final Instant modifyTime)
      throws DataAccessException {
    return updateModifyTimeImpl(this, id, modifyTime);
  }

}
