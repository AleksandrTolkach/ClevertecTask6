package by.toukach.employeeservice.repository;

/**
 * Интерфейс для работы с инструментами миграции БД.
 */
public interface Migration {

  /**
   * Метод для миграции БД.
   */
  void migrate();

  /**
   * Откат внесенных изменений до определенного тега.
   *
   * @param tag тег, то которого необходимо откатить БД.
   */
  void rollback(String tag);
}
