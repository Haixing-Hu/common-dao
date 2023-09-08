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

import ltd.qubit.commons.model.WithCode;

/**
 * 此接口表示实现根据编码彻底删除实体操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>此实体对象的编码必须全局唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #eraseByCode(String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface ErasableByCodeMapper<T extends WithCode>
    extends Mapper<T> {

  /**
   * 彻底删除指定的实体对象。
   *
   * <p><b>注意：</b>此操作将真正从数据库中删除指定的对象，被删除的对象不可恢复。
   *
   * @param code
   *     待彻底删除的实体对象的编码。
   * @return
   *     此操作所彻底删除的对象数，若指定的待删除对象不存在则返回0。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see ClearableMapper#clear()
   * @see DeletableMapper#delete(Long, Instant)
   * @see DeletableByCodeMapper#deleteByCode(String, Instant)
   * @see DeletableMapper#restore(Long)
   * @see DeletableByCodeMapper#restoreByCode(String)
   * @see DeletableMapper#purge(Long)
   * @see DeletableByCodeMapper#purgeByCode(String)
   * @see DeletableMapper#purgeAll()
   */
  long eraseByCode(@Param("code") String code) throws DataAccessException;
}
