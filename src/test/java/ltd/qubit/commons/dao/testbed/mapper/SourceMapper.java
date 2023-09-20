////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.testbed.mapper;

import ltd.qubit.commons.dao.testbed.SourceDao;
import ltd.qubit.model.commons.Source;

/**
 * 实现{@link SourceDao}的MyBatis Mapper。
 *
 * @author 胡海星
 */
public interface SourceMapper extends
    ltd.qubit.commons.dao.mapper.BasicByCodeMapper<Source>,
    ltd.qubit.commons.dao.mapper.InfoGettableMapper<Source>,
    ltd.qubit.commons.dao.mapper.InfoGettableByCodeMapper<Source> {

  //  empty
}
