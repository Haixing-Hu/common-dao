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
import ltd.qubit.commons.dao.testbed.PersonDao;
import ltd.qubit.commons.dao.testbed.mapper.PersonMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.model.commons.CredentialInfo;
import ltd.qubit.model.person.Person;
import ltd.qubit.model.person.PersonInfo;

import static ltd.qubit.commons.dao.DaoImplHelper.addImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;
import static ltd.qubit.commons.dao.DaoImplHelper.updateImpl;

public class PersonDaoImpl extends DaoImpl<Person> implements PersonDao {

  @Autowired
  private PersonMapper mapper;

  @Autowired
  private CredentialDao credentialDao;

  public PersonDaoImpl() {
    super(Person.class);
  }

  @Override
  public PersonMapper getMapper() {
    return mapper;
  }

  @Override
  public PersonInfo getInfo(final Long id) throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> mapper.getInfo(id), "info", "id", id);
  }

  @Override
  public PersonInfo getInfoByCredential(final CredentialInfo credential)
      throws DataAccessException {
    return getPropertyByKeyImpl(this, () -> mapper.getInfoByCredential(credential),
        "info", "credential", credential);
  }

  @Override
  public PersonInfo getGuardian(final Long id) throws DataAccessException {
    PersonInfo guardian = getPropertyByKeyOrNullImpl(this,
        () -> mapper.getGuardian(id), "guardian", "id", id);
    if (guardian == null) {
      if (! mapper.exist(id)) {
        throw new DataNotExistException(Person.class, "id", id);
      }
    } else if (guardian.isEmpty()) {
      guardian = null;
    }
    return guardian;
  }

  @Override
  public Instant add(final Person person) throws DataAccessException {
    final Instant createTime = addImpl(this, person);
    credentialDao.addForOwner(person, "credential", person.getCredential());
    credentialDao.addForOwner(person, "medicareCard", person.getMedicareCard());
    credentialDao.addForOwner(person, "socialSecurityCard", person.getSocialSecurityCard());
    return createTime;
  }

  @Override
  public Instant update(final Person person) throws DataAccessException {
    final Instant modifyTime = updateImpl(this, person);
    credentialDao.updateForOwner(person, "credential", person.getCredential());
    credentialDao.updateForOwner(person, "medicareCard", person.getMedicareCard());
    credentialDao.updateForOwner(person, "socialSecurityCard", person.getSocialSecurityCard());
    return modifyTime;
  }
}
