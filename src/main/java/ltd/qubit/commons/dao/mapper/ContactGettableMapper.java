////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.mapper;

import javax.annotation.Nullable;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithContact;
import ltd.qubit.model.contact.Contact;

/**
 * 此接口表示对拥有{@code contact}属性的实体类实现了查询操作的MyBatis Mapper。
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #getContact(Long)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface ContactGettableMapper<T extends Identifiable & WithContact>
    extends GettableMapper<T> {

  /**
   * 根据ID获取指定的对象的联系方式。
   *
   * @param id
   *     指定的对象的ID。
   * @return
   *     具有指定ID的对象的联系方式，或{@code null}若指定的对象不存在。
   * @throws DataAccessException
   *     若出现其他数据存取错误。
   */
  @Nullable
  Contact getContact(@Param("id") Long id) throws DataAccessException;

}
