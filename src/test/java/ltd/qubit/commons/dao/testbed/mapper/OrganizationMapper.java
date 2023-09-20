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

import ltd.qubit.commons.dao.testbed.OrganizationDao;
import ltd.qubit.model.organization.Organization;
import ltd.qubit.model.person.PersonInfo;

/**
 * 实现{@link OrganizationDao}的MyBatis Mapper。
 *
 * @author 胡海星
 */
public interface OrganizationMapper extends CommentUpdatableMapper<Organization>,
    CommentUpdatableByCodeMapper<Organization>,
    ContactGettableMapper<Organization>, ContactUpdatableMapper<Organization>,
    ContactUpdatableByCodeMapper<Organization>, ltd.qubit.commons.dao.mapper.StatefulInfoGettableMapper<Organization>,
    ltd.qubit.commons.dao.mapper.StatefulInfoGettableByCodeMapper<Organization>,
    ltd.qubit.commons.dao.mapper.StatefulInfoGettableByNameMapper<Organization>,
    ltd.qubit.commons.dao.mapper.BasicStatefulByCodeMapper<Organization>, ltd.qubit.commons.dao.mapper.StateUpdatableByNameMapper<Organization>,
    ltd.qubit.commons.dao.mapper.DeletableByNameMapper<Organization>,
    ltd.qubit.commons.dao.mapper.ErasableByNameMapper<Organization>,
    ltd.qubit.commons.dao.mapper.ListableMapper<Organization>,
    GettableByNameMapper<T>,
    ltd.qubit.commons.dao.mapper.AddableMapper<Organization>,
    ltd.qubit.commons.dao.mapper.UpdatableByNameMapper<Organization> {

  long updatePrincipal(@Param("id") Long id,
      @Param("principal") PersonInfo principal,
      @Param("modifyTime") Instant modifyTime)
      throws DataAccessException;

  long updatePrincipalByCode(@Param("code") String code,
      @Param("principal") PersonInfo principal,
      @Param("modifyTime") Instant modifyTime)
      throws DataAccessException;
}
