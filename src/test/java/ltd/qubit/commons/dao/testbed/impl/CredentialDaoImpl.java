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
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.impl.DaoImpl;
import ltd.qubit.commons.dao.testbed.AttachmentDao;
import ltd.qubit.commons.dao.testbed.CredentialDao;
import ltd.qubit.commons.dao.testbed.mapper.CredentialMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.util.pair.KeyValuePair;
import ltd.qubit.model.commons.Credential;
import ltd.qubit.model.commons.CredentialInfo;
import ltd.qubit.model.commons.Owner;
import ltd.qubit.model.system.VerifyState;
import ltd.qubit.model.upload.Attachment;

import static ltd.qubit.commons.dao.DaoImplHelper.addImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.batchAddImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.fixSelectedResultList;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateImpl;

/**
 * 实现{@link CredentialDao}。
 *
 * @author 胡海星
 */
public class CredentialDaoImpl extends DaoImpl<Credential> implements CredentialDao {

  @Autowired
  private CredentialMapper mapper;

  @Autowired
  private AttachmentDao attachmentDao;

  public CredentialDaoImpl() {
    super(Credential.class);
  }

  @Override
  public CredentialMapper getMapper() {
    return mapper;
  }

  @Override
  public Credential getByIndex(final Owner owner, final int index)
      throws DataAccessException {
    return getByKeyImpl(this, () -> mapper.getByIndex(owner, index),
        "index", index, new KeyValuePair("owner", owner));
  }

  @Override
  public Credential getByIndexOrNull(final Owner owner, final int index)
      throws DataAccessException {
    return getByKeyOrNullImpl(this, () -> mapper.getByIndex(owner, index),
        "index", index, new KeyValuePair("owner", owner));
  }

  @Override
  public CredentialInfo getInfo(final Long id) throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> mapper.getInfo(id), "info", "id", id);
  }

  @Override
  public List<CredentialInfo> getInfoForOwner(final Owner owner)
      throws DataAccessException {
    logger.debug("Get info of {}: owner = {}", this.getEntityName(), owner);
    return fixSelectedResultList(mapper.getInfoForOwner(owner));
  }

  @Override
  public Instant add(final Credential obj) throws DataAccessException {
    obj.setVerified(VerifyState.NONE);
    final Instant createTime = addImpl(this, obj);
    final Owner owner = new Owner(Credential.class, obj.getId(), "attachments");
    attachmentDao.addForOwner(owner, obj.getAttachments());
    return createTime;
  }

  @Override
  public Instant update(final Credential obj) throws DataAccessException {
    return updateImpl(this, obj);
  }

  @Override
  public Instant updateAttachments(final Long id, final List<Attachment> attachments)
      throws DataAccessException {
    logger.debug("Update attachments for {}: id = {}, attachments = {}",
        getEntityName(), id, attachments);
    if (! mapper.existNonDeleted(id)) {
      throw new DataNotExistException(Credential.class, "id", id);
    }
    final Owner owner = new Owner(Credential.class, id, "attachments");
    final Instant modifyTime = attachmentDao.updateForOwner(owner, attachments);
    mapper.updateModifyTime(id, modifyTime);
    return modifyTime;
  }

  @Override
  public Instant updateByOwner(final Credential obj)
      throws DataAccessException {
    return updateByKeyImpl(this, modifyTime -> getMapper().updateByOwner(obj),
        obj, "owner", obj.getOwner());
  }

  @Override
  public <T extends Identifiable>
  Instant addForOwner(final T obj, final String property,
      @Nullable final CredentialInfo info) throws DataAccessException {
    if (info == null) {
      return now();
    }
    final Owner owner = new Owner(obj, property);
    logger.debug("Add a {} to the owner: owner = {}, info = {}",
        getEntityName(), owner, info);
    final Credential credential = new Credential(info);
    credential.setOwner(owner);
    credential.setIndex(0);
    credential.setVerified(VerifyState.NONE);
    final Instant createTime = addImpl(this, credential);
    info.setId(credential.getId());
    info.setVerified(credential.getVerified());
    return createTime;
  }

  @Override
  public <T extends Identifiable>
  Instant addForOwner(final T obj, final String property,
      @Nullable final List<CredentialInfo> infos) throws DataAccessException {
    if (infos == null || infos.isEmpty()) {
      return now();
    }
    final Owner owner = new Owner(obj, property);
    logger.debug("Add the list of {} to the owner: owner = {}, infos = {}",
        getEntityName(), owner, infos);
    return addForOwnerImpl(owner, infos);
  }

  private Instant addForOwnerImpl(final Owner owner, final List<CredentialInfo> infos) {
    final List<Credential> credentials = new ArrayList<>();
    int index = 0;
    for (final CredentialInfo info : infos) {
      final Credential credential = new Credential(info);
      credential.setOwner(owner);
      credential.setIndex(index++);
      credential.setVerified(VerifyState.NONE);
      credentials.add(credential);
    }
    final Instant createTime = batchAddImpl(this, credentials);
    // 更新参数infos，保证函数返回后参数值和数据库中值一致
    final ListIterator<CredentialInfo> infoIterator = infos.listIterator();
    final ListIterator<Credential> credentialIterator = credentials.listIterator();
    while (infoIterator.hasNext()) {
      final CredentialInfo info = infoIterator.next();
      final Credential credential = credentialIterator.next();
      info.setId(credential.getId());
      info.setVerified(credential.getVerified());
    }
    return createTime;
  }

  @Override
  public <T extends Identifiable>
  Instant updateForOwner(final T obj, final String property,
      @Nullable final CredentialInfo info) throws DataAccessException {
    final Owner owner = new Owner(obj, property);
    logger.debug("Update a {} for the owner: owner = {}, info = {}",
        getEntityName(), owner ,info);
    if (info == null) {
      mapper.eraseForOwner(owner);
      return now();
    }
    final Credential existing = mapper.getByIndex(owner, 0);
    if (existing == null) {
      final Credential credential = new Credential(info);
      credential.setOwner(owner);
      credential.setIndex(0);
      credential.setVerified(VerifyState.NONE);
      final Instant createTime = addImpl(this, credential);
      info.setId(credential.getId());
      info.setVerified(credential.getVerified());
      return createTime;
    } else {
      info.setId(existing.getId());
      if (info.equals(existing.getInfo())) {
        // 完全一致，无需更新
        return now();
      } else {
        existing.assign(info);
        return updateImpl(this, existing);
      }
    }
  }

  @Override
  public <T extends Identifiable>
  Instant updateForOwner(final T obj, final String property,
      @Nullable final List<CredentialInfo> infos) throws DataAccessException {
    final Owner owner = new Owner(obj, property);
    logger.debug("Update a list of {} for the owner: owner = {}, infos = {}",
        getEntityName(), owner ,infos);
    // 全部清除旧的后再添加，否则实现起来很麻烦
    mapper.eraseForOwner(owner);
    if (infos == null) {
      return now();
    } else {
      return addForOwnerImpl(owner, infos);
    }
  }
}
