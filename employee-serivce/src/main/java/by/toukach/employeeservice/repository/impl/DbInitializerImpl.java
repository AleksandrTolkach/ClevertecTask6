package by.toukach.employeeservice.repository.impl;

import by.toukach.employeeservice.exception.DbException;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.repository.DbInitializer;
import by.toukach.employeeservice.repository.SqlRequest;
import by.toukach.employeeservice.util.property.ApplicationProperties;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Класс для настройки соединений к базе и предоставляющий Connection.
 */
@Slf4j
public class DbInitializerImpl implements DbInitializer {

  private static DbInitializer instance = new DbInitializerImpl();

  private final JdbcTemplate jdbcTemplate;
  private final DriverManagerDataSource dataSource = new DriverManagerDataSource();


  private DbInitializerImpl() {
    String driver = ApplicationProperties.DB_DRIVER;
    String url = ApplicationProperties.DB_URL;
    String dbName = ApplicationProperties.DB_NAME;
    String username = ApplicationProperties.DB_USERNAME;
    String password = ApplicationProperties.DB_PASSWORD;

    dataSource.setDriverClassName(driver);
    dataSource.setUrl(String.format(url, username));
    dataSource.setUsername(username);
    dataSource.setPassword(password);

    jdbcTemplate = new JdbcTemplate(dataSource);

    try {
      jdbcTemplate.update(SqlRequest.CREATE_DB_SQL);
    } catch (Exception e) {
      log.info(ExceptionMessage.DB_EXISTS);
    }

    dataSource.setUrl(String.format(url, dbName));

    try {
      jdbcTemplate.update(SqlRequest.CREATE_APPLICATION_SCHEMA_SQL);
      jdbcTemplate.update(SqlRequest.CREATE_LIQUIBASE_SCHEMA_SQL);
    } catch (Exception e) {
      log.info(ExceptionMessage.SCHEMES_EXISTS);
    }
  }

  /**
   * Метод предоставляющий доступ к настроенному JdbcTemplate.
   *
   * @return настроенный JdbcTemplate.
   */
  @Override
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  /**
   * Метод предоставляющий доступ к настроенному Connection.
   *
   * @return настроенный Connection.
   */
  @Override
  public Connection getConnection() {
    try {
      return dataSource.getConnection();

    } catch (SQLException e) {
      throw new DbException(ExceptionMessage.CONNECT_TO_DB, e);
    }
  }

  public static DbInitializer getInstance() {
    return instance;
  }
}
