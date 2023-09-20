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
import java.util.List;

import javax.annotation.Nullable;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.annotation.Modified;
import ltd.qubit.commons.annotation.NoAutoTest;
import ltd.qubit.commons.dao.testbed.mapper.CredentialMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.error.DuplicateKeyException;
import ltd.qubit.commons.error.FieldTooLongException;
import ltd.qubit.commons.error.FieldValueOutOfRangeException;
import ltd.qubit.commons.error.ForeignKeyConstraintFailException;
import ltd.qubit.commons.error.InvalidFieldFormatException;
import ltd.qubit.commons.error.NullFieldException;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.commons.Credential;
import ltd.qubit.model.commons.CredentialInfo;
import ltd.qubit.model.commons.Owner;
import ltd.qubit.model.upload.Attachment;
import ltd.qubit.model.upload.Upload;

/**
 * 对{@link Credential}对象进行数据库存取的DAO接口。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #countForOwner(Owner)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #getForOwner(Owner)}</li>
 * <li>{@link #getIdForOwner(Owner)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #add(Credential)}</li>
 * <li>{@link #update(Credential)}</li>
 * <li>{@link #delete(Long)}</li>
 * <li>{@link #deleteForOwner(Owner)}</li>
 * <li>{@link #restore(Long)}</li>
 * <li>{@link #restoreForOwner(Owner)} </li>
 * <li>{@link #purge(Long)}</li>
 * <li>{@link #purgeForOwner(Owner)} </li>
 * <li>{@link #purgeAll()}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #eraseForOwner(Owner)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @author 胡海星
 */
public interface CredentialDao extends BasicWithOwnerDao<Credential>,
    UpdatableByOwnerDao<Credential> {

  @Override
  CredentialMapper getMapper();

  Credential getByIndex(Owner owner, int index)
      throws DataAccessException;

  @Nullable
  Credential getByIndexOrNull(Owner owner, int index)
      throws DataAccessException;

  /**
   * 根据ID获取指定的{@link Credential}对象的基本信息。
   *
   * @param id
   *    指定的{@link Credential}对象的ID。
   * @return
   *    具有指定ID的{@link Credential}对象的基本信息。
   * @throws DataNotExistException
   *    若指定的{@link Credential}对象不存在。
   * @throws DataAccessException
   *    若出现其他数据存取错误。
   */
  CredentialInfo getInfo(Long id) throws DataAccessException;

  /**
   * 获取指定所有者的全部{@link Credential}对象的基本信息。
   *
   * @param owner
   *    指定的{@link Credential}对象的所有者。
   * @return
   *    指定所有者所拥有的所有{@link Credential}对象的基本信息列表。
   * @throws DataAccessException
   *    若出现其他数据存取错误。
   */
  List<CredentialInfo> getInfoForOwner(Owner owner) throws DataAccessException;

  /**
   * 添加一个新的{@link Credential}对象。
   *
   * <p>此函数会同时添加该对象的{@code attachments}属性中的所有元素，注意每个
   * {@link Attachment}的{@link Upload}必须是已经存在于数据库中的。</p>
   *
   * @param obj
   *     待添加的{@link Credential}对象。
   * @return
   *     数据被被添加时的时间戳。
   * @throws NullFieldException
   *     如果该实体的某个属性对应的字段非空但其属性值为空。
   * @throws InvalidFieldFormatException
   *     如果该实体的某个属性值不符合其对应的字段所需的格式。
   * @throws FieldTooLongException
   *     如果该实体的某个属性值长度超过了其对应字段允许的长度。
   * @throws FieldValueOutOfRangeException
   *     如果该实体的某个属性值的范围超出了其对应字段允许的范围。
   * @throws DuplicateKeyException
   *     如果该实体的某个属性值对应的字段要求唯一，但该属性值和数据库中已存在的对象重复。
   * @throws ForeignKeyConstraintFailException
   *     如果某个属性对应的字段关联了另一张关联表的某个关联字段，而该属性值在关联表的关联字段
   *     中不存在。
   * @throws DataAccessException
   *     如果发生其他无法归类的数据库操作错误。
   */
  @Modified({"id", "attachments", "createTime", "modifyTime", "deleteTime"})
  Instant add(Credential obj) throws DataAccessException;

  @Modified({"type", "number", "verified", "index", "title", "description", "modifyTime"})
  Instant update(Credential obj) throws DataAccessException;

  @Modified({"attachments", "modifyTime"})
  Instant updateAttachments(Long id, List<Attachment> attachments)
      throws DataAccessException;

  @Modified({"type", "number", "verified", "index", "title", "description", "modifyTime"})
  Instant updateByOwner(Credential obj) throws DataAccessException;

  @NoAutoTest
  <T extends Identifiable>
  Instant addForOwner(T obj, String property, @Nullable CredentialInfo info)
      throws DataAccessException;

  @NoAutoTest
  <T extends Identifiable>
  Instant addForOwner(T obj, String property, @Nullable List<CredentialInfo> infos)
      throws DataAccessException;

  @NoAutoTest
  <T extends Identifiable>
  Instant updateForOwner(T obj, String property, @Nullable CredentialInfo info)
      throws DataAccessException;

  @NoAutoTest
  <T extends Identifiable>
  Instant updateForOwner(T obj, String property, @Nullable List<CredentialInfo> infos)
      throws DataAccessException;
}
