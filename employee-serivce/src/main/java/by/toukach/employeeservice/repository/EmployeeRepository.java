package by.toukach.employeeservice.repository;

import by.toukach.employeeservice.dao.Employee;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для работы с сущностью сотрудника в БД.
 */
public interface EmployeeRepository {

  /**
   * Метод для сохранения сотрудника в БД.
   *
   * @param employee сотрудник для сохранения.
   * @return сохраненный сотрудник.
   */
  Employee save(Employee employee);

  /**
   * Метод для поиска сотрудника в БД по его id.
   *
   * @param id id запрашиваемого сотрудника.
   * @return Optional с запрашиваемым сотрудником.
   */
  Optional<Employee> findById(Long id);

  /**
   * Метод для поиска всех сотрудников в БД.
   *
   * @return список всех сотрудников.
   */
  List<Employee> findAll();

  /**
   * Метод для обновления сотрудника в БД по его id.
   *
   * @param id id обновляемого сотрудника.
   * @param employee информация для обновления.
   * @return Optional с обновленным сотрудником.
   */
  Optional<Employee> update(Long id, Employee employee);

  /**
   * Метод для удаления сотрудника из БД по его id.
   *
   * @param id id удаляемого сотрудника.
   */
  void delete(Long id);
}
