package by.toukach.employeeservice.exception;

/**
 * Класс представляющий исключение,
 * выбрасываемое при некорректном аргументе, переданном в метод.
 * */
public class IllegalArgumentException extends RuntimeException {

  public IllegalArgumentException(String message) {
    super(message);
  }
}
