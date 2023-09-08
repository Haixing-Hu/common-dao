////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.mapper;

import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.WithCredential;
import ltd.qubit.model.commons.CredentialInfo;

/**
 * 此接口表示实现根据证件添加或更新实体操作的MyBatis Mapper。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #existCredential(CredentialInfo)} </li>
 * <li>{@link #getByCredential(CredentialInfo)} </li>
 * <li>{@link #getIdByCredential(CredentialInfo)}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #update(Identifiable)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface AddOrUpdatableByCredentialMapper<T extends Identifiable & WithCredential>
    extends GettableByCredentialMapper<T>, AddableMapper<T>, UpdatableByCredentialMapper<T> {
  // empty
}
