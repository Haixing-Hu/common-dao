////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.mapper;

import javax.annotation.Nullable;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithName;

/**
 * 此接口表示实现根据所属实体类和名称彻底删除实体操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>此实体对象的名称必须在其所属实体类下唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #eraseByName(String, String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface ErasableByEntityNameMapper<T extends Identifiable & WithName>
    extends Mapper<T> {

  /**
   * 彻底删除指定的实体对象。
   *
   * <p><b>注意：</b>此操作将真正从数据库中删除指定的对象，被删除的对象不可恢复。
   *
   * @param entity
   *     待彻底清除的实体对象所属的实体类名称。
   * @param name
   *     待彻底清除的体对象的名称，如果为{@code null}则不做限制。
   * @return
   *     此操作所彻底删除的对象数，若指定的待删除对象不存在则返回0。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   */
  long eraseByName(@Param("entity") String entity,
      @Nullable @Param("name") String name) throws DataAccessException;
}
