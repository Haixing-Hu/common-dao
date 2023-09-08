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

import ltd.qubit.commons.dao.mapper.BasicHasStatefulInfoByCodeByNameMapper;
import ltd.qubit.commons.dao.mapper.CommentUpdatableByCodeMapper;
import ltd.qubit.commons.dao.mapper.CommentUpdatableMapper;
import ltd.qubit.commons.dao.mapper.ContactGettableMapper;
import ltd.qubit.commons.dao.mapper.ContactUpdatableByCodeMapper;
import ltd.qubit.commons.dao.mapper.ContactUpdatableMapper;
import ltd.qubit.commons.dao.testbed.OrganizationDao;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import ltd.qubit.model.organization.Organization;
import ltd.qubit.model.person.PersonInfo;

/**
 * 实现{@link OrganizationDao}的MyBatis Mapper。
 *
 * @author 胡海星
 */
public interface OrganizationMapper extends
    BasicHasStatefulInfoByCodeByNameMapper<Organization>,
    CommentUpdatableMapper<Organization>,
    CommentUpdatableByCodeMapper<Organization>,
    ContactGettableMapper<Organization>, ContactUpdatableMapper<Organization>,
    ContactUpdatableByCodeMapper<Organization> {

  long updatePrincipal(@Param("id") Long id,
      @Param("principal") PersonInfo principal,
      @Param("modifyTime") Instant modifyTime)
      throws DataAccessException;

  long updatePrincipalByCode(@Param("code") String code,
      @Param("principal") PersonInfo principal,
      @Param("modifyTime") Instant modifyTime)
      throws DataAccessException;
}
