////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed;

import ltd.qubit.commons.dao.BasicHasInfoWithEntityByCodeByEntityNameDao;
import ltd.qubit.commons.dao.testbed.mapper.CategoryMapper;
import ltd.qubit.commons.model.Identifiable;
import ltd.qubit.commons.model.InfoWithEntity;
import ltd.qubit.commons.model.WithCode;
import ltd.qubit.commons.model.WithEntity;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.sql.SortRequest;
import ltd.qubit.model.commons.Category;

/**
 * 对{@link Category}对象进行数据库存取的DAO接口。
 *
 * <p>此接口实现了以下DAO操作：</p>
 * <ul>
 * <li>{@link #count(Criterion)}</li>
 * <li>{@link #list(Criterion, SortRequest, Integer, Long)}</li>
 * <li>{@link #exist(Long)}</li>
 * <li>{@link #existCode(String)}</li>
 * <li>{@link #existName(String, String)}</li>
 * <li>{@link #get(Long)}</li>
 * <li>{@link #getByCode(String)}</li>
 * <li>{@link #getByName(String, String)}</li>
 * <li>{@link #getInfo(Long)}，此函数返回一个{@link InfoWithEntity}对象</li>
 * <li>{@link #getInfoByCode(String)}，此函数返回一个{@link InfoWithEntity}对象</li>
 * <li>{@link #getInfoByName(String, String)}，此函数返回一个{@link InfoWithEntity}对象</li>
 * <li>{@link #add(Identifiable)}</li>
 * <li>{@link #update(Identifiable)}</li>
 * <li>{@link #updateByCode(WithCode)}</li>
 * <li>{@link #updateByName(WithEntity)} </li>
 * <li>{@link #addOrUpdateByCode(Identifiable)}</li>
 * <li>{@link #addOrUpdateByName(Identifiable)}</li>
 * <li>{@link #delete(Long)}</li>
 * <li>{@link #deleteByCode(String)}</li>
 * <li>{@link #deleteByName(String, String)}</li>
 * <li>{@link #restore(Long)}</li>
 * <li>{@link #restoreByCode(String)}</li>
 * <li>{@link #restoreByName(String, String)}</li>
 * <li>{@link #purge(Long)}</li>
 * <li>{@link #purgeByCode(String)}</li>
 * <li>{@link #purgeByName(String, String)}</li>
 * <li>{@link #purgeAll()}</li>
 * <li>{@link #erase(Long)}</li>
 * <li>{@link #eraseByCode(String)} </li>
 * <li>{@link #eraseByName(String, String)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 *
 * @author 胡海星
 */
public interface CategoryDao extends BasicHasInfoWithEntityByCodeByEntityNameDao<Category> {

  @Override
  CategoryMapper getMapper();

}
