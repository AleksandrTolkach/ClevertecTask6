package by.toukach.employeeservice.repository.impl;

import by.toukach.employeeservice.dao.User;
import by.toukach.employeeservice.repository.SqlRequest;
import by.toukach.employeeservice.repository.UserRepository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Класс для работы с сущностью пользователя в БД.
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<User> rowMapper;

  /**
   * Сохранение пользователя в БД.
   *
   * @param user пользователь для сохранения.
   * @return сохраненный пользователь.
   */
  @Override
  public User save(User user) {

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement statement = connection.prepareStatement(SqlRequest.CREATE_USER_SQL,
          Statement.RETURN_GENERATED_KEYS);

      statement.setString(1, user.getName());
      statement.setObject(2, user.getCreatedAt());
      statement.setString(3, user.getPassword());
      statement.setString(4, user.getRole().name());

      return statement;
    }, keyHolder);

    Long id = keyHolder.getKey() != null ? keyHolder.getKeyAs(Long.class) : null;
    user.setId(id);

    return user;
  }

  /**
   * Метод для получения списка пользователей из БД.
   *
   * @return запрашиваемый список.
   */
  @Override
  public List<User> findAll() {
    return jdbcTemplate.query(SqlRequest.SELECT_USERS_SQL, rowMapper);
  }

  /**
   * Метод для получения пользователя по ID из БД.
   *
   * @param id id пользователя.
   * @return Optional с запрашиваемым пользователем.
   */
  @Override
  public Optional<User> findById(Long id) {

    List<User> query =
        jdbcTemplate.query(SqlRequest.SELECT_USER_BY_ID_SQL.formatted(id), rowMapper);

    return query.stream()
        .findFirst();
  }

  /**
   * Метод для получения пользователя по username из БД.
   *
   * @param username имя пользователя.
   * @return Optional с запрашиваемым пользователем.
   */
  @Override
  public Optional<User> findByUsername(String username) {

    List<User> query =
        jdbcTemplate.query(SqlRequest.SELECT_USER_BY_USERNAME_SQL.formatted(username), rowMapper);

    return query.stream()
        .findFirst();
  }

  /**
   * Метод для обновления пользователя в БД.
   *
   * @param id id пользователя для обновления.
   * @param user пользователь для обновления.
   * @return обновленный пользователь.
   */
  @Override
  public User update(Long id, User user) {

    int updatedRowCount = jdbcTemplate.update(connection -> {
      PreparedStatement statement = connection.prepareStatement(SqlRequest.UPDATE_USER_SQL);

      statement.setString(1, user.getName());
      statement.setString(2, user.getPassword());
      statement.setString(3, user.getRole().name());
      statement.setLong(4, user.getId());

      return statement;
    });

    return updatedRowCount != 0 ? findById(id).orElseGet(() -> user) : user;
  }

  /**
   * Метод для удаления пользователя из БД.
   *
   * @param id id пользователя.
   */
  @Override
  public void delete(Long id) {

    jdbcTemplate.update(connection -> {
      PreparedStatement statement = connection.prepareStatement(SqlRequest.DELETE_USER_SQL);

      statement.setLong(1, id);

      return statement;
    });
  }
}
