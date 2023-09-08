////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import ltd.qubit.commons.sql.ComposedCriterion;
import ltd.qubit.commons.sql.SimpleCriterion;
import ltd.qubit.commons.util.MapUtils;
import ltd.qubit.model.system.Setting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static ltd.qubit.commons.sql.CriterionBuilder.allEqual;
import static ltd.qubit.commons.sql.CriterionBuilder.and;
import static ltd.qubit.commons.sql.CriterionBuilder.equal;
import static ltd.qubit.commons.sql.CriterionBuilder.in;
import static ltd.qubit.commons.sql.CriterionBuilder.isNull;
import static ltd.qubit.commons.sql.CriterionBuilder.like;
import static ltd.qubit.commons.sql.SortBuilder.asc;
import static ltd.qubit.commons.sql.SortBuilder.desc;

/**
 * 对{@link SettingDao}的集成测试。
 *
 * @author 胡海星
 */
public class SettingDaoIT extends DaoTestBase {

  @TestFactory
  public List<DynamicNode> test() throws Exception {
    return registry.generate(Setting.class);
  }

  private Setting s1, s2, s3, s4;

  private void setUp() {
    settingDao.clear();
    s1 = new Setting();
    s1.setName("setting-1");
    s1.setString("value-1");
    s1.setReadonly(false);
    s1.setNullable(false);
    s1.setMultiple(true);
    settingDao.add(s1);

    s2 = new Setting();
    s2.setName("setting-2");
    s2.setString("value-2");
    s2.setReadonly(true);
    s2.setNullable(true);
    s2.setMultiple(false);
    settingDao.add(s2);

    s3 = new Setting();
    s3.setName("setting-3");
    s3.setInt(3);
    s3.setReadonly(false);
    s3.setNullable(false);
    s3.setMultiple(false);
    settingDao.add(s3);

    s4 = generator.nextObject(Setting.class);
    s4.setName("aaaaaaaaaa-4");
    s4.setBool(true);
    s4.setReadonly(true);
    s4.setNullable(false);
    s4.setMultiple(false);
  }

  @Test
  public void testCount() {
    setUp();
    assertEquals(3, settingDao.count(null));
    settingDao.add(s4);
    assertEquals(4, settingDao.count(null));
    assertEquals(0, settingDao.count(isNull(Setting.class, Setting::getName)));
    assertEquals(1, settingDao.count(equal(Setting.class, Setting::getName, s1.getName())));
    assertEquals(0, settingDao.count(equal(Setting.class, Setting::getName, "setting")));
    assertEquals(3, settingDao.count(like(Setting.class, Setting::getName, "setting")));
    assertEquals(3, settingDao.count(like(Setting.class, Setting::getName, "tt")));
    assertEquals(4, settingDao.count(like(Setting.class, Setting::getName, "")));
    assertEquals(1, settingDao.count(like(Setting.class, Setting::getName, "aaaaaaaaaa")));
    assertEquals(0, settingDao.count(like(Setting.class, Setting::getName, "xx")));
    assertEquals(2, settingDao.count(in(Setting.class, Setting::getName, "setting-1", "setting-2")));
    assertEquals(2, settingDao.count(equal(Setting.class, Setting::isReadonly, true)));
    assertEquals(3, settingDao.count(equal(Setting.class, Setting::isNullable, false)));

//    assertEquals(4, settingDao.count(equal(Setting.class, Setting::getName, Setting::getName)));
//    assertEquals(0, settingDao.count(notEqual(Setting.class, Setting::getName, Setting::getName)));

    assertEquals(1, settingDao.count(allEqual(Setting.class,
        MapUtils.fromArray(new Object[][]{ {"name", "setting-2"}, {"readonly", true} }))));

    final SimpleCriterion<Setting> f1 = like(Setting.class, Setting::getName, "setting");
    final SimpleCriterion<Setting> f2 = equal(Setting.class, Setting::isReadonly, false);
    final ComposedCriterion<Setting> f3 = and(Setting.class, f1, f2);
    assertEquals(2, settingDao.count(f3));
  }

  @Test
  public void testList() {
    setUp();
    assertEquals(
        Arrays.asList(s1, s2, s3), settingDao.list(null, null, null, null));
    assertEquals(Arrays.asList(s1, s2, s3),
        settingDao.list(null, asc(Setting.class, Setting::getName), null, null));
    assertEquals(Arrays.asList(s3, s2, s1),
        settingDao.list(null, desc(Setting.class, Setting::getName), null, null));

    settingDao.add(s4);
    assertEquals(Arrays.asList(s1, s3), settingDao.list(equal(Setting.class, Setting::isReadonly, false),
        asc(Setting.class, Setting::getName), null, null));

    assertEquals(Collections.singletonList(s1), settingDao.list(equal(Setting.class, Setting::isReadonly, false),
        asc(Setting.class, Setting::getName), 1, null));

    assertEquals(Collections.singletonList(s3), settingDao.list(equal(Setting.class, Setting::isReadonly, false),
        asc(Setting.class, Setting::getName), 1, 1L));

    assertEquals(Collections.singletonList(s3), settingDao.list(equal(Setting.class, Setting::isReadonly, false),
        asc(Setting.class, Setting::getName), null, 1L));
  }
}
