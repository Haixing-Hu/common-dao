////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.impl;

import ltd.qubit.commons.model.Info;

public abstract class InfoDaoImpl<T extends Info> extends DaoImpl<T>
    implements InfoDao<T> {

  public InfoDaoImpl(final Class<T> entityClass) {
    super(entityClass);
  }

  @Override
  public abstract InfoMapper<T> getMapper();

  //  empty
}
