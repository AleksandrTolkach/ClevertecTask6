package by.toukach.employeeservice.dao.rowmapper.impl;

import by.toukach.employeeservice.dao.User;
import by.toukach.employeeservice.dao.User.Fields;
import by.toukach.employeeservice.enumiration.UserRole;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

/**
 * Класс для создания User из ResultSet.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRowMapper implements RowMapper<User> {

  private static final UserRowMapper instance = new UserRowMapper();
  private static final String CREATED_AT_FIELD = "created_at";

  @Override
  public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return User.builder()
        .id(resultSet.getLong(Fields.id))
        .createdAt(resultSet.getObject(CREATED_AT_FIELD, LocalDateTime.class))
        .name(resultSet.getString(Fields.name))
        .password(resultSet.getString(Fields.password))
        .role(UserRole.valueOf(resultSet.getString(Fields.role)))
        .build();
  }

  public static RowMapper<User> getInstance() {
    return instance;
  }
}
