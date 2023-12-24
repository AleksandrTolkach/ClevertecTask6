package by.toukach.employeeservice.repository;

import by.toukach.employeeservice.dao.User;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для работы с сущностью пользователя в БД.
 */
public interface UserRepository {

  /**
   * Сохранение пользователя в БД.
   *
   * @param user пользователь для сохранения.
   * @return сохраненный пользователь.
   */
  User save(User user);

  /**
   * Метод для получения списка пользователей из БД.
   *
   * @return запрашиваемый список.
   */
  List<User> findAll();

  /**
   * Метод для получения пользователя по ID из БД.
   *
   * @param id id пользователя.
   * @return Optional с запрашиваемым пользователем.
   */
  Optional<User> findById(Long id);

  /**
   * Метод для получения пользователя по username из БД.
   *
   * @param username имя пользователя.
   * @return Optional с запрашиваемым пользователем.
   */
  Optional<User> findByUsername(String username);

  /**
   * Метод для обновления пользователя в БД.
   *
   * @param id id пользователя для обновления.
   * @param user пользователь для обновления.
   * @return обновленный пользователь.
   */
  User update(Long id, User user);

  /**
   * Метод для удаления пользователя из БД.
   *
   * @param id id пользователя.
   */
  void delete(Long id);
}
