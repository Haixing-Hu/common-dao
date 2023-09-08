////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed.impl;

import java.time.Instant;
import java.util.List;

import javax.annotation.Nullable;

import ltd.qubit.commons.dao.impl.DaoImpl;
import ltd.qubit.commons.dao.testbed.AttachmentDao;
import ltd.qubit.commons.dao.testbed.mapper.AttachmentMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.util.pair.KeyValuePair;
import ltd.qubit.model.commons.Owner;
import ltd.qubit.model.upload.Attachment;
import ltd.qubit.model.upload.Upload;

import static ltd.qubit.commons.dao.DaoImplHelper.addImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.batchAddImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.eraseByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.existKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getByKeyOrNullImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updatePropertyByKeyImpl;

/**
 * 实现{@link AttachmentDao}。
 *
 * @author 胡海星
 */
public class AttachmentDaoImpl extends DaoImpl<Attachment> implements AttachmentDao {

  @Autowired
  private AttachmentMapper mapper;

  public AttachmentDaoImpl() {
    super(Attachment.class);
  }

  @Override
  public AttachmentMapper getMapper() {
    return mapper;
  }

  @Override
  public boolean existIndex(final Owner owner, final int index)
      throws DataAccessException {
    return existKeyImpl(this, () -> mapper.existIndex(owner, index),
        "index", index, new KeyValuePair("owner", owner));
  }

  @Override
  public Attachment getByIndex(final Owner owner, final int index)
      throws DataAccessException {
    return getByKeyImpl(this, () -> mapper.getByIndex(owner, index),
        "index", index, new KeyValuePair("owner", owner));
  }

  @Override
  public Attachment getByIndexOrNull(final Owner owner, final int index)
      throws DataAccessException {
    return getByKeyOrNullImpl(this, () -> mapper.getByIndex(owner, index),
        "index", index, new KeyValuePair("owner", owner));
  }

  @Override
  public Instant update(final Attachment obj) throws DataAccessException {
    return updateImpl(this, obj);
  }

  @Override
  public Instant updateByIndex(final Attachment obj)
      throws DataAccessException {
    return updateByKeyImpl(this, modifyTime -> mapper.updateByIndex(obj), obj,
        "index", obj.getIndex(), new KeyValuePair("owner", obj.getOwner()));
  }

  @Override
  public Instant updateUpload(final Long id, final Upload upload)
      throws DataAccessException {
    return updatePropertyByKeyImpl(this,
        modifyTime -> mapper.updateUpload(id, upload, modifyTime),
        "upload", upload, "id", id);
  }

  @Override
  public Instant addOrUpdateByIndex(final Attachment obj)
      throws DataAccessException {
    logger.debug("Add or update {} by its owner and index: {}", getEntityName(), obj);
    if (mapper.existIndex(obj.getOwner(), obj.getIndex())) {
      return updateByIndex(obj);
    } else {
      return add(obj);
    }
  }

  @Override
  public void eraseByIndex(final Owner owner, final int index)
      throws DataAccessException {
    eraseByKeyImpl(this, (eraseTime) -> mapper.eraseByIndex(owner, index),
        "index", index, new KeyValuePair("owner", owner));
  }

  @Override
  public Instant addForOwner(final Owner owner,
      @Nullable final Attachment attachment) throws DataAccessException {
    logger.debug("Add the {} for the owner: owner = {}, attachment = {}",
        getEntityName(), owner, attachment);
    if (attachment != null) {
      attachment.setOwner(owner);
      attachment.setIndex(0);
      return addImpl(this, attachment);
    }
    return now();
  }

  @Override
  public Instant addForOwner(final Owner owner,
      @Nullable final List<Attachment> attachments) throws DataAccessException {
    logger.debug("Add the list of {} for the owner: owner = {}, attachments = {}",
        getEntityName(), owner, attachments);
    if (attachments != null && attachments.size() > 0) {
      int index = 0;
      for (final Attachment attachment : attachments) {
        attachment.setOwner(owner);
        attachment.setIndex(index++);
      }
      return batchAddImpl(this, attachments);
    }
    return now();
  }

  @Override
  public Instant updateForOwner(final Owner owner,
      @Nullable final Attachment attachment) throws DataAccessException {
    logger.debug("Update the {} for the owner: owner = {},attachment = {}",
        getEntityName(), owner, attachment);
    mapper.eraseByIndex(owner, 0);
    if (attachment != null) {
      attachment.setOwner(owner);
      attachment.setIndex(0);
      return addImpl(this, attachment);
    }
    return now();
  }

  @Override
  public Instant updateForOwner(final Owner owner,
      @Nullable final List<Attachment> attachments) throws DataAccessException {
    logger.debug("Update the list of {} for the owner: owner = {}, attachments = {}",
        getEntityName(), owner, attachments);
    mapper.eraseForOwner(owner);
    if (attachments != null && attachments.size() > 0) {
      int index = 0;
      for (final Attachment attachment : attachments) {
        attachment.setOwner(owner);
        attachment.setIndex(index++);
      }
      return batchAddImpl(this, attachments);
    }
    return now();
  }
}
