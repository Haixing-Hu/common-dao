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

import ltd.qubit.commons.dao.testbed.AppDao;
import ltd.qubit.model.commons.App;
import ltd.qubit.model.commons.AuthorizeRecord;

/**
 * 实现{@link AppDao}的MyBatis Mapper。
 *
 * @author 胡海星
 */
public interface AppMapper extends TokenGettableMapper<App>, TokenGettableByCodeMapper<App>,
    TokenUpdatableMapper<App>, TokenUpdatableByCodeMapper<App>,
    SecurityKeyGettableMapper<App>, SecurityKeyGettableByCodeMapper<App>,
    SecurityKeyUpdatableMapper<App>, SecurityKeyUpdatableByCodeMapper<App>,
    CommentUpdatableMapper<App>, CommentUpdatableByCodeMapper<App>,
    OrganizationUpdatableMapper<App>, OrganizationUpdatableByCodeMapper<App>,
    ltd.qubit.commons.dao.mapper.StatefulInfoGettableMapper<App>,
    ltd.qubit.commons.dao.mapper.StatefulInfoGettableByCodeMapper<App>,
    ltd.qubit.commons.dao.mapper.StateUpdatableMapper<App>,
    ltd.qubit.commons.dao.mapper.StateUpdatableByCodeMapper<App>,
    ltd.qubit.commons.dao.mapper.BasicMapper<App>, ltd.qubit.commons.dao.mapper.DeletableByCodeMapper<App>,
    ltd.qubit.commons.dao.mapper.ErasableByCodeMapper<App>,
    ltd.qubit.commons.dao.mapper.ListableMapper<App>, GettableByCodeMapper<T>,
    ltd.qubit.commons.dao.mapper.AddableMapper<App>,
    ltd.qubit.commons.dao.mapper.UpdatableByCodeMapper<App> {

  long updateLastAuthorize(@Param("id") Long id,
      @Param("lastAuthorize") AuthorizeRecord lastAuthorize,
      @Param("modifyTime") Instant modifyTime)
      throws DataAccessException;

  long updateLastAuthorizeByCode(@Param("code") String code,
      @Param("lastAuthorize") AuthorizeRecord lastAuthorize,
      @Param("modifyTime") Instant modifyTime)
      throws DataAccessException;
}
