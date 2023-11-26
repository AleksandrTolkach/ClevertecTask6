package by.toukach.employeeservice.exception;

/**
 * Исключение, выбрасываемое при некорректной работе с файлами.
 */
public class FileException extends RuntimeException {

  public FileException(String message, Throwable cause) {
    super(message, cause);
  }
}
