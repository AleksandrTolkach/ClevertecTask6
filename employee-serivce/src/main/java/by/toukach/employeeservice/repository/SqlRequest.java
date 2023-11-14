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
      "INSERT INTO application.employees (created_at, name, date_of_birth, specialization, active) "
          + "VALUES (?, ?, ?, ?, ?) RETURNING ID";
  
  public static final String SELECT_EMPLOYEE_BY_ID_SQL =
      "SELECT id, created_at, name, date_of_birth, specialization, active "
          + "FROM application.employees "
          + "WHERE id = %s ";
  
  public static final String SELECT_EMPLOYEE_SQL =
      "SELECT id, created_at, name, date_of_birth, specialization, active "
          + "FROM application.employees ";
  
  public static String UPDATE_EMPLOYEE_SQL =
      "UPDATE application.employees SET name = ?, date_of_birth = ?, specialization = ?, "
          + "active = ? "
          + "WHERE id = ?";
  
  public static String DELETE_EMPLOYEE_SQL =
      "DELETE FROM application.employees WHERE id = ?";
}
