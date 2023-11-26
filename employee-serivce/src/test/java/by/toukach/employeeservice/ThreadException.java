package by.toukach.employeeservice;

/**
 * Исключение, выбрасываемой при некорректной работе с Thread.
 */
public class ThreadException extends RuntimeException {

  public ThreadException(String message, Throwable cause) {
    super(message, cause);
  }
}
