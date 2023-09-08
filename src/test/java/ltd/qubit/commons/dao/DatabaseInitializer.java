////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 * 提供初始化数据库的方法。
 *
 * @author 胡海星
 */
public class DatabaseInitializer {

  public static void init() throws Exception {
    final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);
    logger.info("Initialize the database ...");
    final ApplicationContext context =
        new ClassPathXmlApplicationContext("classpath:profile.xml",
            "classpath:spring/common-dao/data-source.test.xml");
    final DataSource dataSource = context.getBean(DataSource.class);
    try (final Connection con = dataSource.getConnection()) {
      createDatabase(con);
    }
  }

  public static void createDatabase(final Connection connection) throws SQLException {
    final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);
    logger.info("Creating the database...");
    final DataSource dataSource = new SingleConnectionDataSource(connection, true);
    final Flyway flyway = Flyway.configure().dataSource(dataSource).load();
    flyway.clean();
    flyway.migrate();
  }
}
