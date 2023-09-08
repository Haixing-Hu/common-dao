////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed.impl;

import ltd.qubit.commons.dao.impl.DaoImpl;
import ltd.qubit.commons.dao.testbed.UploadDao;
import ltd.qubit.commons.dao.testbed.mapper.UploadMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ltd.qubit.model.upload.Upload;

import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyImpl;

/**
 * 实现{@link UploadDao}。
 *
 * @author 胡海星
 */
public class UploadDaoImpl extends DaoImpl<Upload> implements UploadDao {

  @Autowired
  private UploadMapper mapper;

  public UploadDaoImpl() {
    super(Upload.class);
  }

  @Override
  public UploadMapper getMapper() {
    return mapper;
  }

  @Override
  public Upload getByUrl(final String url) throws DataAccessException {
    return getByKeyImpl(this, () -> mapper.getByUrl(url), "url", url);
  }

  @Override
  public Upload getByAttachmentId(final Long attachmentId)
      throws DataAccessException {
    return getByKeyImpl(this, () -> mapper.getByAttachmentId(attachmentId),
        "attachmentId", attachmentId);
  }
}
