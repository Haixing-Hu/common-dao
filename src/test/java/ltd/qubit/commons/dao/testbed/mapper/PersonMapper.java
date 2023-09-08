////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed.mapper;

import java.time.Instant;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.mapper.BasicMapper;
import ltd.qubit.commons.dao.mapper.CommentUpdatableMapper;
import ltd.qubit.commons.dao.mapper.ContactGettableMapper;
import ltd.qubit.commons.dao.mapper.ContactUpdatableMapper;
import ltd.qubit.commons.dao.mapper.GettableByCredentialMapper;
import ltd.qubit.commons.dao.testbed.PersonDao;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.commons.CredentialInfo;
import ltd.qubit.model.contact.Contact;
import ltd.qubit.model.person.Person;
import ltd.qubit.model.person.PersonInfo;

/**
 * 实现{@link PersonDao}的MyBatis Mapper。
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #existCredential(CredentialInfo)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getByCredential(CredentialInfo)}
 * <li>{@link #getIdByCredential(CredentialInfo)}</li>
 * <li>{@link #add(Identifiable)}</li>
 * <li>{@link #update(Identifiable)}</li>
 * <li>{@link #updateContact(Long, Contact, Instant)}</li>
 * <li>{@link #delete(Long, Instant)}</li>
 * <li>{@link #restore(Long)}</li>
 * <li>{@link #purge(Long)}</li>
 * <li>{@link #purgeAll()}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @author 胡海星
 */
public interface PersonMapper extends BasicMapper<Person>,
    GettableByCredentialMapper<Person>, ContactGettableMapper<Person>,
    ContactUpdatableMapper<Person>, CommentUpdatableMapper<Person> {

  PersonInfo getInfo(@Param("id") Long id) throws DataAccessException;

  PersonInfo getInfoByCredential(@Param("credential") CredentialInfo credential)
      throws DataAccessException;

  PersonInfo getGuardian(@Param("id") Long id) throws DataAccessException;

}
