package by.toukach.employeeservice.repository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Класс, содержащий в себе SQL запросы.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlRequest {

  public static final String CREATE_DB_SQL = "CREATE DATABASE employee";

  public static final String CREATE_APPLICATION_SCHEMA_SQL =
      "CREATE SCHEMA application AUTHORIZATION toukach";

  public static final String CREATE_LIQUIBASE_SCHEMA_SQL =
      "CREATE SCHEMA liquibase AUTHORIZATION toukach";

  public static final String CREATE_EMPLOYEE_SQL =
      """
          INSERT INTO application.employees (created_at, name, date_of_birth, specialization,
          active)
          VALUES (?, ?, ?, ?, ?) RETURNING ID
          """;

  public static final String SELECT_EMPLOYEE_BY_ID_SQL =
      """
          SELECT id, created_at, name, date_of_birth, specialization, active
          FROM application.employees
          WHERE id = %s
          """;

  public static final String SELECT_EMPLOYEE_SQL =
      """
          SELECT id, created_at, name, date_of_birth, specialization, active
          FROM application.employees
          OFFSET %s
          LIMIT %s
          """;

  public static final String UPDATE_EMPLOYEE_SQL =
      """
          UPDATE application.employees SET name = ?, date_of_birth = ?, specialization = ?,
          active = ?
          WHERE id = ?
          """;

  public static final String DELETE_EMPLOYEE_SQL =
      "DELETE FROM application.employees WHERE id = ?";

  public static final String EMPLOYEE_COUNT = "SELECT COUNT(*) FROM application.employees";

  public static final String CREATE_USER_SQL =
      """
          INSERT INTO application.users(name, created_at, password, role)
          VALUES (?, ?, ?, ?) RETURNING ID
          """;

  public static final String SELECT_USERS_SQL =
      """
          SELECT * FROM application.users
          """;

  public static final String SELECT_USER_BY_ID_SQL =
      """
          SELECT id, created_at, name, password, role
          FROM application.users
          WHERE id = %s
          """;

  public static final String SELECT_USER_BY_USERNAME_SQL =
      """
          SELECT id, created_at, name, password, role
          FROM application.users
          WHERE name = '%s'
          """;

  public static final String UPDATE_USER_SQL =
      """
          UPDATE application.users
          SET name = ?, password = ?, role = ?
          WHERE id = ?
          """;

  public static final String DELETE_USER_SQL =
      """
          DELETE FROM application.users
          WHERE id = ?
          """;
}
