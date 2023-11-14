package by.toukach.employeeservice.repository;

import java.sql.Connection;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Интерфейс для настройки соединения к базе и предоставляющий Connection.
 * */
public interface DbInitializer {

  /**
   * Метод предоставляющий доступ к настроенному JdbcTemplate.
   *
   * @return настроенный JdbcTemplate.
   */
  JdbcTemplate getJdbcTemplate();

  /**
   * Метод предоставляющий доступ к настроенному Connection.
   *
   * @return настроенный Connection.
   */
  Connection getConnection();
}
