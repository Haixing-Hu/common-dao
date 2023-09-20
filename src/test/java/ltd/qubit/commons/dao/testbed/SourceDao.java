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

import org.springframework.dao.DataAccessException;

import ltd.qubit.commons.annotation.Modified;
import ltd.qubit.commons.dao.testbed.mapper.SourceMapper;
import ltd.qubit.model.commons.Source;

/**
 * 对{@link Source}对象进行数据库存取的DAO接口。
 *
 * @author 胡海星
 */
public interface SourceDao extends BasicHasInfoByCodeDao<Source> {

  SourceMapper getMapper();

  @Modified({"name", "category", "description", "providerApp",
      "providerOrg", "modifyTime"})
  Instant update(Source obj) throws DataAccessException;

  @Modified({"name", "category", "description", "providerApp",
      "providerOrg", "modifyTime"})
  Instant updateByCode(Source obj) throws DataAccessException;
}
