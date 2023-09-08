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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import ltd.qubit.model.system.Host;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 对{@link HostDao}的集成测试。
 *
 * @author 胡海星
 */
public class HostDaoIT extends DaoTestBase {

  @TestFactory
  public List<DynamicNode> test() throws Exception {
    return registry.generate(Host.class);
  }

  @Test
  public void testGeneratedId() throws Exception {
    hostDao.clear();
    for (int i = 0; i < TEST_LOOPS; ++i) {
      final Host host = generator.nextObject(Host.class);
      hostDao.add(host);
      assertEquals(i + 1, host.getId());
    }
    hostDao.clear();
    for (int i = 0; i < TEST_LOOPS; ++i) {
      final Host host = generator.nextObject(Host.class);
      hostDao.add(host);
      assertEquals(i + 1, host.getId());
    }
  }
}
