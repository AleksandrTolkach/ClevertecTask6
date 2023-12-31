package by.toukach.employeeservice.repository;

/**
 * Интерфейс для настройки соединения к базе и предоставляющий Connection.
 * */
public interface DbInitializer {

  void prepareDb();
}
