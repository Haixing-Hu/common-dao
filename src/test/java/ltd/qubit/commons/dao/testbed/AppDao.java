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

import ltd.qubit.commons.dao.BasicHasStatefulInfoByCodeDao;
import ltd.qubit.commons.dao.CommentUpdatableByCodeDao;
import ltd.qubit.commons.dao.CommentUpdatableDao;
import ltd.qubit.commons.dao.OrganizationUpdatableByCodeDao;
import ltd.qubit.commons.dao.OrganizationUpdatableDao;
import ltd.qubit.commons.dao.SecurityKeyGettableByCodeDao;
import ltd.qubit.commons.dao.SecurityKeyGettableDao;
import ltd.qubit.commons.dao.SecurityKeyUpdatableByCodeDao;
import ltd.qubit.commons.dao.SecurityKeyUpdatableDao;
import ltd.qubit.commons.dao.TokenGettableByCodeDao;
import ltd.qubit.commons.dao.TokenGettableDao;
import ltd.qubit.commons.dao.TokenUpdatableByCodeDao;
import ltd.qubit.commons.dao.TokenUpdatableDao;
import ltd.qubit.commons.dao.testbed.mapper.AppMapper;

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.annotation.Modified;
import ltd.qubit.model.commons.App;
import ltd.qubit.model.commons.AuthorizeRecord;

/**
 * 对{@link App}对象进行数据库存取的DAO接口。
 *
 * @author 胡海星
 */
public interface AppDao extends BasicHasStatefulInfoByCodeDao<App>,
    TokenGettableDao<App>, TokenGettableByCodeDao<App>, TokenUpdatableDao<App>,
    TokenUpdatableByCodeDao<App>, SecurityKeyGettableDao<App>,
    SecurityKeyGettableByCodeDao<App>, SecurityKeyUpdatableDao<App>,
    SecurityKeyUpdatableByCodeDao<App>, CommentUpdatableDao<App>,
    CommentUpdatableByCodeDao<App>, OrganizationUpdatableDao<App>,
    OrganizationUpdatableByCodeDao<App> {

  @Override
  AppMapper getMapper();

  @Modified({"id", "lastAuthorize", "createTime", "modifyTime", "deleteTime"})
  Instant add(App obj) throws DataAccessException;

  @Modified({"name", "category", "icon", "url", "description", "modifyTime"})
  Instant update(App obj) throws DataAccessException;

  @Modified({"name", "category", "icon", "url", "description", "modifyTime"})
  Instant updateByCode(App obj) throws DataAccessException;

  @Modified({"lastAuthorize", "modifyTime"})
  Instant updateLastAuthorize(Long id, AuthorizeRecord lastAuthorize)
      throws DataAccessException;

  @Modified({"lastAuthorize", "modifyTime"})
  Instant updateLastAuthorizeByCode(String code, AuthorizeRecord lastAuthorize)
      throws DataAccessException;
}
