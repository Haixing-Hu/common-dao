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
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.commons.CredentialInfo;

/**
 * 此接口表示实现了对实体类进行列表、根据证件查询，添加，根据证件更新，根据证件添加或更新
 * 等操作的MyBatis Mapper。
 *
 * <p><b>注意：</b>对象的证件必须全局唯一。</p>
 *
 * <p>此接口实现了以下Mapper操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #existCredential(CredentialInfo)}</li>
 * <li>{@link #getByCredential(CredentialInfo)}</li>
 * <li>{@link #getIdByCredential(CredentialInfo)}</li>
 * </ul>
 *
 * @param <T>
 *     被操作的实体的类型。
 * @author 胡海星
 */
public interface ListableAddOrUpdatableByCredentialMapper<T extends Identifiable & WithCredential>
    extends ListableMapper<T>, AddOrUpdatableByCredentialMapper<T> {
  //  empty
}
