////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import javax.annotation.Nullable;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.dao.mapper.ContactGettableMapper;
import ltd.qubit.commons.error.DataNotExistException;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithContact;
import ltd.qubit.model.contact.Contact;

import static ltd.qubit.commons.dao.DaoImplHelper.getPropertyByKeyOrNullImpl;

/**
 * 此接口表示对拥有{@code contact}属性的实体类实现了查询操作的DAO。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #getContact(Long)}</li>
 * <li>{@link #getContactOrNull(Long)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 */
public interface ContactGettableDao<T extends Identifiable & WithContact>
    extends GettableDao<T> {

  @Override
  ContactGettableMapper<T> getMapper();

  /**
   * 根据ID获取指定的对象的联系方式。
   *
   * @param id
   *     指定的对象的ID。
   * @return
   *     具有指定ID的对象的联系方式，注意可能为空或{@code null}。
   * @throws DataNotExistException
   *     若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Contact getContact(final Long id) throws DataAccessException {
    final Contact result = getPropertyByKeyOrNullImpl(this,
        () -> getMapper().getContact(id), "contact", "id", id);
    if (result == null) {
      if (! getMapper().exist(id)) {
        throw new DataNotExistException(getEntityClass(), "id", id);
      }
    }
    return result;
  }

  /**
   * 根据ID获取指定的对象的联系方式。
   *
   * @param id
   *     指定的对象的ID。
   * @return
   *     具有指定ID的对象的联系方式，或{@code null}若指定的对象不存在或其联系方式为空。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  default Contact getContactOrNull(final Long id) throws DataAccessException {
    return getPropertyByKeyOrNullImpl(this, () -> getMapper().getContact(id),
        "contact", "id", id);
  }
}
