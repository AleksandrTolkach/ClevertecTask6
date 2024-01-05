package by.toukach.employeeservice.dao.rowmapper.impl;

import by.toukach.employeeservice.dao.Employee;
import by.toukach.employeeservice.dao.Employee.Fields;
import by.toukach.employeeservice.enumiration.Specialization;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * Класс для создания Employee из ResultSet.
 */
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeRowMapper implements RowMapper<Employee> {

  private static final String CREATED_AT_FIELD = "created_at";
  private static final String DATE_OF_BIRTH_FIELD = "date_of_birth";

  @Override
  public Employee mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return Employee.builder()
        .id(resultSet.getLong(Fields.id))
        .createdAt(resultSet.getObject(CREATED_AT_FIELD, LocalDateTime.class))
        .name(resultSet.getString(Fields.name))
        .dateOfBirth(resultSet.getObject(DATE_OF_BIRTH_FIELD, LocalDate.class))
        .specialization(Specialization.valueOf(resultSet.getString(Fields.specialization)))
        .active(resultSet.getBoolean(Fields.active))
        .build();
  }
}
