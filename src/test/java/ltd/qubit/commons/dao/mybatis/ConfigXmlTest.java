////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.Date;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.junit.jupiter.api.Test;

import ltd.qubit.commons.datastructure.list.StringList;
import ltd.qubit.commons.reflect.FieldUtils;
import ltd.qubit.commons.sql.mybatis.InstantHandler;
import ltd.qubit.commons.sql.mybatis.KeyValuePairListHandler;
import ltd.qubit.commons.sql.mybatis.LocalDateHandler;
import ltd.qubit.commons.sql.mybatis.LocalDateTimeHandler;
import ltd.qubit.commons.sql.mybatis.LocalTimeHandler;
import ltd.qubit.commons.sql.mybatis.LocalTimeRangeListHandler;
import ltd.qubit.commons.sql.mybatis.PeriodHandler;
import ltd.qubit.commons.sql.mybatis.StringArrayHandler;
import ltd.qubit.commons.sql.mybatis.StringListHandler;
import ltd.qubit.commons.sql.mybatis.UtcDateHandler;
import ltd.qubit.commons.util.pair.KeyValuePairList;
import ltd.qubit.commons.util.range.LocalTimeRangeList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 对通用配置文件 "src/main/resources/mybatis/commons/config.xml" 的单元测试。
 *
 * @author 胡海星
 */
public class ConfigXmlTest {

  private static final String RESOURCE = "mybatis/config.xml";

  private static final Class<?>[][] TYPE_HANDLER_TEST_DATA = {
      { Enum.class, EnumTypeHandler.class },
      { Date.class, UtcDateHandler.class },
      { Instant.class, InstantHandler.class },
      { LocalDate.class, LocalDateHandler.class },
      { LocalTime.class, LocalTimeHandler.class },
      { LocalDateTime.class, LocalDateTimeHandler.class },
      { Period.class, PeriodHandler.class },
      { String[].class, StringArrayHandler.class },
      { LocalTimeRangeList.class, LocalTimeRangeListHandler.class },
      { KeyValuePairList.class, KeyValuePairListHandler.class },
      { StringList.class, StringListHandler.class },
  };

  @Test
  public void testTypeHandlers() throws IOException {
    try (final InputStream inputStream = Resources.getResourceAsStream(RESOURCE)) {
      final XMLConfigBuilder builder = new XMLConfigBuilder(inputStream);
      final Configuration config = builder.parse();
      final TypeHandlerRegistry handlers = config.getTypeHandlerRegistry();
      assertNotNull(handlers);
      for (int i = 0; i < TYPE_HANDLER_TEST_DATA.length; ++i) {
        final Class<?> type = TYPE_HANDLER_TEST_DATA[i][0];
        final Class<?> expectedHandlerType = TYPE_HANDLER_TEST_DATA[i][1];
        final TypeHandler<?> handler = handlers.getTypeHandler(type);
        assertNotNull(handler);
        assertEquals(expectedHandlerType, handler.getClass());
      }
    }
  }

  private static final String[][] SETTINGS_TEST_DATA = {
      { "logImpl", "class org.apache.ibatis.logging.slf4j.Slf4jImpl" },
      { "logPrefix", "" },
      { "cacheEnabled", "true" },
      { "defaultExecutorType", "SIMPLE" },
      { "defaultStatementTimeout", "600" },
      //  It's very important to call setters on null column values
      { "callSettersOnNulls", "true" },
      { "jdbcTypeForNull", "NULL" },
      { "cacheEnabled", "true" },
      { "cacheEnabled", "true" },
  };

  @Test
  public void testSettings() throws IOException, IllegalAccessException {
    try (final InputStream inputStream = Resources.getResourceAsStream(RESOURCE)) {
      final XMLConfigBuilder builder = new XMLConfigBuilder(inputStream);
      final Configuration config = builder.parse();
      for (int i = 0; i < SETTINGS_TEST_DATA.length; ++i) {
        final String key = SETTINGS_TEST_DATA[i][0];
        final String expectedValue = SETTINGS_TEST_DATA[i][1];
        final Field field = FieldUtils.getField(Configuration.class, key);
        final Object value = FieldUtils.readField(field, config);
        assertNotNull(value);
        assertEquals(expectedValue, value.toString());
      }
    }
  }
}
