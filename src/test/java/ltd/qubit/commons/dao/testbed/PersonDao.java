////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed;

import java.time.Instant;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.annotation.Modified;
import ltd.qubit.commons.annotation.Unmodified;
import ltd.qubit.commons.dao.BasicDao;
import ltd.qubit.commons.dao.testbed.mapper.PersonMapper;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.commons.CredentialInfo;
import ltd.qubit.model.person.Person;
import ltd.qubit.model.person.PersonInfo;

/**
 * 对{@link Person}对象进行数据库存取的DAO接口。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #add(Person)}</li>
 * <li>{@link #update(Person)}</li>
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
public interface PersonDao extends BasicDao<Person>,
    GettableByCredentialDao<Person>, ContactGettableDao<Person>,
    ContactUpdatableDao<Person>, CommentUpdatableDao<Person> {

  PersonMapper getMapper();

  PersonInfo getInfo(Long id) throws DataAccessException;

  PersonInfo getInfoByCredential(CredentialInfo credential)
      throws DataAccessException;

  PersonInfo getGuardian(Long id) throws DataAccessException;

  @Modified({"id", "credential", "medicareCard", "socialSecurityCard",
      "createTime", "modifyTime", "deleteTime"})
  Instant add(Person person) throws DataAccessException;

  @Unmodified({"id", "contact", "guardian", "comment", "createTime", "deleteTime"})
  Instant update(Person person) throws DataAccessException;
}
