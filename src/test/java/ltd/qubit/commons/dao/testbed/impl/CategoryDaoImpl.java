////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed.impl;

import ltd.qubit.commons.dao.impl.DaoImpl;
import ltd.qubit.commons.dao.testbed.CategoryDao;
import ltd.qubit.commons.dao.testbed.mapper.CategoryMapper;

import org.springframework.beans.factory.annotation.Autowired;

import ltd.qubit.model.commons.Category;

/**
 * 实现{@link CategoryDao}。
 *
 * @author 胡海星
 */
public class CategoryDaoImpl extends DaoImpl<Category> implements CategoryDao {

  @Autowired
  private CategoryMapper mapper;

  public CategoryDaoImpl() {
    super(Category.class);
  }

  @Override
  public CategoryMapper getMapper() {
    return mapper;
  }
}
