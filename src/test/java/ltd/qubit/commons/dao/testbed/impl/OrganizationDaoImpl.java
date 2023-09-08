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

import ltd.qubit.commons.dao.impl.DaoImpl;
import ltd.qubit.commons.dao.testbed.CredentialDao;
import ltd.qubit.commons.dao.testbed.OrganizationDao;
import ltd.qubit.commons.dao.testbed.PayloadDao;
import ltd.qubit.commons.dao.testbed.mapper.OrganizationMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ltd.qubit.model.organization.Organization;
import ltd.qubit.model.person.PersonInfo;

import static ltd.qubit.commons.dao.DaoImplHelper.addImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updatePropertyByKeyImpl;

/**
 * 实现{@link OrganizationDao}。
 *
 * @author 胡海星
 */
public class OrganizationDaoImpl extends DaoImpl<Organization> implements OrganizationDao {

  @Autowired
  private OrganizationMapper mapper;

  @Autowired
  private CredentialDao credentialDao;

  @Autowired
  private PayloadDao payloadDao;

  public OrganizationDaoImpl() {
    super(Organization.class);
  }

  @Override
  public OrganizationMapper getMapper() {
    return mapper;
  }

  @Override
  public Instant add(final Organization obj) throws DataAccessException {
    final Instant createTime = addImpl(this, obj);
    credentialDao.addForOwner(obj, "credential", obj.getCredential());
    credentialDao.addForOwner(obj, "licenses", obj.getLicenses());
    payloadDao.addForOwner(obj, "payloads", obj.getPayloads());
    return createTime;
  }

  @Override
  public Instant update(final Organization obj) throws DataAccessException {
    final Instant modifyTime = updateImpl(this, obj);
    credentialDao.updateForOwner(obj, "credential", obj.getCredential());
    credentialDao.updateForOwner(obj, "licenses", obj.getLicenses());
    payloadDao.updateForOwner(obj, "payloads", obj.getPayloads());
    return modifyTime;
  }

  @Override
  public Instant updateByCode(final Organization obj) throws DataAccessException {
    final Instant modifyTime = updateByKeyImpl(this, t -> mapper.updateByCode(obj), obj,
        "code", obj.getCode());
    // 需要确保调用 payloadDao.updateForOwner() 时 obj.id正确
    final Long id = mapper.getIdByCode(obj.getCode());
    obj.setId(id);
    credentialDao.updateForOwner(obj, "credential", obj.getCredential());
    credentialDao.updateForOwner(obj, "licenses", obj.getLicenses());
    payloadDao.updateForOwner(obj, "payloads", obj.getPayloads());
    return modifyTime;
  }

  @Override
  public Instant updateByName(final Organization obj) throws DataAccessException {
    final Instant modifyTime = updateByKeyImpl(this, t -> mapper.updateByName(obj), obj,
        "name", obj.getName());
    // 需要确保调用 payloadDao.updateForOwner() 时 obj.id正确
    final Long id = mapper.getIdByName(obj.getName());
    obj.setId(id);
    credentialDao.updateForOwner(obj, "credential", obj.getCredential());
    credentialDao.updateForOwner(obj, "licenses", obj.getLicenses());
    payloadDao.updateForOwner(obj, "payloads", obj.getPayloads());
    return modifyTime;
  }

  @Override
  public Instant updatePrincipal(final Long id, final PersonInfo principal)
      throws DataAccessException {
    return updatePropertyByKeyImpl(this,
        modifyTime -> mapper.updatePrincipal(id, principal, modifyTime),
        "principal", principal, "id", id);
  }

  @Override
  public Instant updatePrincipalByCode(final String code, final PersonInfo principal)
      throws DataAccessException {
    return updatePropertyByKeyImpl(this,
        modifyTime -> mapper.updatePrincipalByCode(code, principal, modifyTime),
        "principal", principal, "code", code);
  }
}
