////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import java.time.Instant;

import javax.annotation.Nullable;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.annotation.Modified;
import ltd.qubit.commons.dao.mapper.ContactUpdatableByCodeMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.error.DuplicateKeyException;
import ltd.qubit.commons.error.FieldTooLongException;
import ltd.qubit.commons.error.FieldValueOutOfRangeException;
import ltd.qubit.commons.error.ForeignKeyConstraintFailException;
import ltd.qubit.commons.error.InvalidFieldFormatException;
import ltd.qubit.commons.error.NullFieldException;
import ltd.qubit.commons.model.WithCode;
import ltd.qubit.commons.model.WithContact;
import ltd.qubit.model.contact.Contact;

import static ltd.qubit.commons.dao.DaoImplHelper.updatePropertyByKeyImpl;

/**
 * 此接口表示对有联系方式和全局唯一编码的实体类实现了更新联系方式操作的DAO。
 *
 * <p><b>注意：</b>对象的编码必须全局唯一。</p>
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #updateContactByCode(String, Contact)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型，必须实现{@link WithContact}接口。
 * @author 胡海星
 */
public interface ContactUpdatableByCodeDao<T extends WithCode & WithContact>
    extends Dao<T> {

  @Override
  ContactUpdatableByCodeMapper<T> getMapper();

  /**
   * 更新一个已存在的实体的联系方式。
   *
   * @param code
   *     待更新的已存在的实体对象的编码。
   * @param contact
   *     待更新的新的联系方式，为{@code null}则表示清除联系方式。
   * @return
   *     此操作所更新的对象的数目，若指定的对象不存在或已被标记删除则返回0。
   * @throws DataNotExistException
   *     若指定的实体对象不存在。
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
  @Modified({"contact", "modifyTime"})
  default Instant updateContactByCode(final String code, @Nullable final Contact contact)
      throws DataAccessException {
    return updatePropertyByKeyImpl(this,
        modifyTime -> getMapper().updateContactByCode(code, contact, modifyTime),
        "contact", contact, "code", code);
  }
}
