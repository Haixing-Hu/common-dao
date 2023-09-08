////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import java.util.List;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import ltd.qubit.model.commons.Source;

/**
 * 对{@link SourceDao}的集成测试。
 *
 * @author 胡海星
 */
public class SourceDaoIT extends DaoTestBase {

  @TestFactory
  public List<DynamicNode> test() throws Exception {
    return registry.generate(Source.class);
  }
}
