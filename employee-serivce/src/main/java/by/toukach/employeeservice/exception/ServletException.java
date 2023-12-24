package by.toukach.employeeservice.exception;

/**
 * Класс представляющий исключение,
 * выбрасываемое при работе с сервлетами.
 * */
public class ServletException extends RuntimeException {

  public ServletException(String message, Throwable cause) {
    super(message, cause);
  }
}
