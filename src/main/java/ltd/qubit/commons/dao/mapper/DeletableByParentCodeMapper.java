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

import ltd.qubit.commons.model.Deletable;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithCode;

/**
 * 此接口表示实现根据所属App及其编码标记删除实体操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>此实体对象的编码在其所属App下必须唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #deleteByCode(Long, String, Instant)}</li>
 * <li>{@link #restoreByCode(Long, String)}</li>
 * <li>{@link #purgeByCode(Long, String)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface DeletableByParentCodeMapper
    <T extends Identifiable & WithCode & Deletable> extends Mapper<T> {

  /**
   * 标记删除指定的实体对象。
   *
   * <p><b>注意：</b>此操作并不真正从数据库中删除指定的对象，只是将其标记删除，并记录被
   * 标记删除时的时间戳。
   *
   * @param parentId
   *     指定的对象所属父对象的ID。
   * @param code
   *     待删除的实体对象的编码。
   * @param deleteTime
   *     删除操作发生时的时间戳。
   * @return
   *     此操作所标记删除的对象数，若指定的待标记删除对象不存在或已被标记删除则返回0。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #purgeByCode(Long, String)
   * @see #restoreByCode(Long, String)
   */
  long deleteByCode(@Param("parentId") Long parentId,
      @Param("code") String code,
      @Param("deleteTime") Instant deleteTime) throws DataAccessException;

  /**
   * 恢复被标记删除的实体对象。
   *
   * <p><b>注意：</b>此操作只能恢复被标记删除的对象，若该对象已经被彻底清除，则无法再被恢复。
   *
   * @param parentId
   *     指定的对象所属父对象的ID。
   * @param code
   *     待恢复标记删除的实体对象的编码。
   * @return
   *     此操作所恢复的对象数，若指定的待恢复对象不存在或未被标记删除则返回0。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #deleteByCode(Long, String, Instant)
   * @see #purgeByCode(Long, String)
   */
  long restoreByCode(@Param("parentId") Long parentId,
      @Param("code") String code) throws DataAccessException;

  /**
   * 彻底清除被标记删除的实体对象。
   *
   * <p><b>注意：</b>此操作将从数据库中彻底清除被标记删除的对象，被彻底清除的对象，无法再被恢
   * 复。
   *
   * @param parentId
   *     指定的对象所属父对象的ID。
   * @param code
   *     待彻底清除的已被标记删除的实体对象的编码。
   * @return
   *     此操作所彻底清除的对象数，若指定的待彻底清除的对象不存在或未被标记删除则返回0。
   * @throws DataAccessException
   *     若发生任何其他数据存取错误。
   * @see #deleteByCode(Long, String, Instant)
   * @see #restoreByCode(Long, String)
   */
  long purgeByCode(@Param("parentId") Long parentId,
      @Param("code") String code) throws DataAccessException;
}
