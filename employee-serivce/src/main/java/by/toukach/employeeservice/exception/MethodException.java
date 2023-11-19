package by.toukach.employeeservice.exception;

/**
 * Класс, представляющий исключение, которое выбрасывается при некорректной работе с методом.
 */
public class MethodException extends RuntimeException {

  public MethodException(String message, Throwable cause) {
    super(message, cause);
  }
}
