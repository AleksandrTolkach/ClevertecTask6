package by.toukach.employeeservice.repository.impl;

import by.toukach.employeeservice.aspect.annotation.CacheableCreate;
import by.toukach.employeeservice.aspect.annotation.CacheableDelete;
import by.toukach.employeeservice.aspect.annotation.CacheableRead;
import by.toukach.employeeservice.aspect.annotation.CacheableUpdate;
import by.toukach.employeeservice.dao.Employee;
import by.toukach.employeeservice.dao.rowmapper.impl.EmployeeRowMapper;
import by.toukach.employeeservice.dto.Page;
import by.toukach.employeeservice.dto.Pageable;
import by.toukach.employeeservice.repository.DbInitializer;
import by.toukach.employeeservice.repository.EmployeeRepository;
import by.toukach.employeeservice.repository.SqlRequest;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Класс для работы с сущностью сотрудника в БД.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {

  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<Employee> rowMapper;

  /**
   * Метод для сохранения сотрудника в БД.
   *
   * @param employee сотрудник для сохранения.
   * @return сохраненный сотрудник.
   */
  @Override
  @CacheableCreate
  public Employee save(Employee employee) {

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement statement = connection.prepareStatement(SqlRequest.CREATE_EMPLOYEE_SQL,
          Statement.RETURN_GENERATED_KEYS);

      statement.setObject(1, employee.getCreatedAt());
      statement.setString(2, employee.getName());
      statement.setObject(3, employee.getDateOfBirth());
      statement.setString(4, employee.getSpecialization().name());
      statement.setBoolean(5, employee.getActive());

      return statement;
    }, keyHolder);

    Long id = keyHolder.getKey() != null ? keyHolder.getKeyAs(Long.class) : null;
    employee.setId(id);

    return employee;

  }

  /**
   * Метод для поиска сотрудника в БД по его id.
   *
   * @param id id запрашиваемого сотрудника.
   * @return Optional с запрашиваемым сотрудником.
   */
  @Override
  @CacheableRead
  public Optional<Employee> findById(Long id) {

    List<Employee> query = jdbcTemplate.query(
        String.format(SqlRequest.SELECT_EMPLOYEE_BY_ID_SQL, id), rowMapper);

    return query.stream()
        .findFirst();
  }

  /**
   * Метод для поиска всех сотрудников в БД.
   *
   * @return список всех сотрудников.
   */
  @Override
  public Page<Employee> findAll(Pageable pageable) {

    List<Employee> content = jdbcTemplate.query(
        SqlRequest.SELECT_EMPLOYEE_SQL.formatted(pageable.getPageNumber() * pageable.getPageSize(),
            pageable.getPageSize()), rowMapper);

    Integer totalElements = jdbcTemplate.queryForObject(SqlRequest.EMPLOYEE_COUNT, int.class);

    return Page.of(pageable, content, totalElements);

  }

  /**
   * Метод для обновления сотрудника в БД по его id.
   *
   * @param id id обновляемого сотрудника.
   * @param employee информация для обновления.
   * @return Optional с обновленным сотрудником.
   */
  @Override
  @CacheableUpdate
  public Optional<Employee> update(Long id, Employee employee) {

    int updatedRows = jdbcTemplate.update(connection -> {
      PreparedStatement statement =
          connection.prepareStatement(SqlRequest.UPDATE_EMPLOYEE_SQL);

      statement.setString(1, employee.getName());
      statement.setObject(2, employee.getDateOfBirth());
      statement.setString(3, employee.getSpecialization().name());
      statement.setBoolean(4, employee.getActive());
      statement.setLong(5, id);

      return statement;
    });

    return updatedRows != 0 ? findById(id) : Optional.empty();
  }

  /**
   * Метод для удаления сотрудника из БД по его id.
   *
   * @param id id удаляемого сотрудника.
   */
  @Override
  @CacheableDelete
  public void delete(Long id) {

    jdbcTemplate.update(connection -> {
      PreparedStatement statement = connection.prepareStatement(SqlRequest.DELETE_EMPLOYEE_SQL);

      statement.setLong(1, id);

      return statement;
    });
  }
}
