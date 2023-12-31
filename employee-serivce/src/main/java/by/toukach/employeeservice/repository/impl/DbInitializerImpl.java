package by.toukach.employeeservice.repository.impl;

import by.toukach.employeeservice.config.ApplicationProperty;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.repository.DbInitializer;
import by.toukach.employeeservice.repository.SqlRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

/**
 * Класс для настройки соединений к базе и предоставляющий Connection.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DbInitializerImpl implements DbInitializer {

  private final ApplicationProperty applicationProperty;
  private final JdbcTemplate jdbcTemplate;
  private final DriverManagerDataSource dataSource;

  @Override
  public void prepareDb() {
    String url = applicationProperty.getDataBaseUrl();
    String dbName = applicationProperty.getDataBaseName();

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
}
