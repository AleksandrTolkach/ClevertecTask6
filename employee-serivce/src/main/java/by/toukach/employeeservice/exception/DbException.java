package by.toukach.employeeservice.exception;

/**
 * Класс представляющий исключение,
 * выбрасываемое при выполнении соединения к базе, отправки запросов.
 * */
public class DbException extends RuntimeException {

  public DbException(String message, Throwable cause) {
    super(message, cause);
  }
}
