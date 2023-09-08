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

import ltd.qubit.commons.dao.mapper.BasicHasStatefulInfoByCodeMapper;
import ltd.qubit.commons.dao.mapper.CommentUpdatableByCodeMapper;
import ltd.qubit.commons.dao.mapper.CommentUpdatableMapper;
import ltd.qubit.commons.dao.mapper.OrganizationUpdatableByCodeMapper;
import ltd.qubit.commons.dao.mapper.OrganizationUpdatableMapper;
import ltd.qubit.commons.dao.mapper.SecurityKeyGettableByCodeMapper;
import ltd.qubit.commons.dao.mapper.SecurityKeyGettableMapper;
import ltd.qubit.commons.dao.mapper.SecurityKeyUpdatableByCodeMapper;
import ltd.qubit.commons.dao.mapper.SecurityKeyUpdatableMapper;
import ltd.qubit.commons.dao.mapper.TokenGettableByCodeMapper;
import ltd.qubit.commons.dao.mapper.TokenGettableMapper;
import ltd.qubit.commons.dao.mapper.TokenUpdatableByCodeMapper;
import ltd.qubit.commons.dao.mapper.TokenUpdatableMapper;
import ltd.qubit.commons.dao.testbed.AppDao;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import ltd.qubit.model.commons.App;
import ltd.qubit.model.commons.AuthorizeRecord;

/**
 * 实现{@link AppDao}的MyBatis Mapper。
 *
 * @author 胡海星
 */
public interface AppMapper extends BasicHasStatefulInfoByCodeMapper<App>,
    TokenGettableMapper<App>, TokenGettableByCodeMapper<App>,
    TokenUpdatableMapper<App>, TokenUpdatableByCodeMapper<App>,
    SecurityKeyGettableMapper<App>, SecurityKeyGettableByCodeMapper<App>,
    SecurityKeyUpdatableMapper<App>, SecurityKeyUpdatableByCodeMapper<App>,
    CommentUpdatableMapper<App>, CommentUpdatableByCodeMapper<App>,
    OrganizationUpdatableMapper<App>, OrganizationUpdatableByCodeMapper<App> {

  long updateLastAuthorize(@Param("id") Long id,
      @Param("lastAuthorize") AuthorizeRecord lastAuthorize,
      @Param("modifyTime") Instant modifyTime)
      throws DataAccessException;

  long updateLastAuthorizeByCode(@Param("code") String code,
      @Param("lastAuthorize") AuthorizeRecord lastAuthorize,
      @Param("modifyTime") Instant modifyTime)
      throws DataAccessException;
}
