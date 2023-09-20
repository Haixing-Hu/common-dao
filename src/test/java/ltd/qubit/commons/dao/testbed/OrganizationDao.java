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
import ltd.qubit.commons.dao.testbed.mapper.OrganizationMapper;
import ltd.qubit.commons.error.DuplicateKeyException;
import ltd.qubit.commons.error.FieldTooLongException;
import ltd.qubit.commons.error.FieldValueOutOfRangeException;
import ltd.qubit.commons.error.ForeignKeyConstraintFailException;
import ltd.qubit.commons.error.InvalidFieldFormatException;
import ltd.qubit.commons.error.NullFieldException;
import ltd.qubit.model.organization.Organization;
import ltd.qubit.model.person.PersonInfo;
import ltd.qubit.model.upload.Upload;

/**
 * 对{@link Organization}对象进行数据库存取的DAO接口。
 *
 * @author 胡海星
 */
public interface OrganizationDao extends
    BasicHasStatefulInfoByCodeByNameDao<Organization>,
    CommentUpdatableDao<Organization>, CommentUpdatableByCodeDao<Organization>,
    ContactGettableDao<Organization>, ContactUpdatableDao<Organization>,
    ContactUpdatableByCodeDao<Organization> {

  @Override
  OrganizationMapper getMapper();

  /**
   * 添加一个新的{@link Organization}对象。
   *
   * <p>此函数会同时添加该对象的{@code credential}, {@code licenses}和{@code payloads}
   * 属性中的所有元素，注意每个{@code Credential.attachments}的{@link Upload}必须是已
   * 经存在于数据库中的。</p>
   *
   * @param obj
   *     待添加的{@link Organization}对象。
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
  @Modified({"id", "credential", "licenses", "payloads",
      "createTime", "modifyTime", "deleteTime"})
  Instant add(Organization obj) throws DataAccessException;

  @Modified({"name", "category", "icon", "description", "taxPayerType",
      "taxNumber", "credential", "licenses", "payloads", "modifyTime"})
  Instant update(Organization obj) throws DataAccessException;

  @Modified({"name", "category", "icon", "description", "taxPayerType",
      "taxNumber", "credential", "licenses", "payloads", "modifyTime"})
  Instant updateByCode(Organization obj) throws DataAccessException;

  @Modified({"category", "icon", "description", "taxPayerType",
      "taxNumber", "credential", "licenses", "payloads", "modifyTime"})
  Instant updateByName(Organization obj) throws DataAccessException;

  @Modified({"principal", "modifyTime"})
  Instant updatePrincipal(Long id, PersonInfo principal)
      throws DataAccessException;

  @Modified({"principal", "modifyTime"})
  Instant updatePrincipalByCode(String code, PersonInfo principal)
      throws DataAccessException;
}
