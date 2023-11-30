package by.toukach.employeeservice.exception;

/**
 * Класс, представляющий исключение, которое выбрасывается при некорректной работе с методом.
 */
public class ReflectionException extends RuntimeException {

  public ReflectionException(String message, Throwable cause) {
    super(message, cause);
  }
}
