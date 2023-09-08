////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.BasicUnupdatableDao;
import ltd.qubit.commons.dao.testbed.mapper.UploadMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.upload.Attachment;
import ltd.qubit.model.upload.Upload;

/**
 * 对{@link Upload}对象进行数据库存取的DAO接口。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getByUrl(String)}</li>
 * <li>{@link #getByAttachmentId(Long)}</li>
 * <li>{@link #add(Identifiable)}</li>
 * <li>{@link #delete(Long)}</li>
 * <li>{@link #restore(Long)}</li>
 * <li>{@link #purge(Long)}</li>
 * <li>{@link #purgeAll()}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @author 胡海星
 */
public interface UploadDao extends BasicUnupdatableDao<Upload> {

  @Override
  UploadMapper getMapper();

  /**
   * 根据原始文件URL获取指定的{@link Upload}对象。
   *
   * <p><b>注意：</b>此函数不考虑待获取对象是否已被标记删除。
   *
   * @param url
   *     指定的对象的原始文件URL。
   * @return 具有指定原始文件URL的{@link Upload}对象。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  Upload getByUrl(String url) throws DataAccessException;

  /**
   * 获取指定的{@link Attachment}对象关联的{@link Upload}对象。
   *
   * <p><b>注意：</b>此函数不考虑待获取对象是否已被标记删除。
   *
   * @param attachmentId
   *     指定的{@link Attachment}对象的ID。
   * @return 指定ID的{@link Attachment}对象所关联的{@link Upload}对象。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  Upload getByAttachmentId(Long attachmentId) throws DataAccessException;
}
