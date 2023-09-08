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

import ltd.qubit.commons.dao.testbed.DeviceDao;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import ltd.qubit.model.device.Device;

/**
 * 对{@link DeviceDao}的集成测试。
 *
 * @author 胡海星
 */
public class DeviceDaoIT extends DaoTestBase {

  @TestFactory
  public List<DynamicNode> test() throws Exception {
    return registry.generate(Device.class);
  }
}
