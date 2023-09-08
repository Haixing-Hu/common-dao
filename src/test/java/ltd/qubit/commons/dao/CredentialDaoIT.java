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

import ltd.qubit.commons.dao.testbed.CredentialDao;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import ltd.qubit.model.commons.Credential;

/**
 * 对{@link CredentialDao}的集成测试。
 *
 * @author 胡海星
 */
public class CredentialDaoIT extends DaoTestBase {

  @TestFactory
  public List<DynamicNode> test() throws Exception {
    return registry.generate(Credential.class);
  }
}
