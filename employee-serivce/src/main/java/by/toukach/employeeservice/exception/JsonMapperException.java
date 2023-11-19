package by.toukach.employeeservice.exception;

/**
 * Класс представляющий исключение, выбрасываемое при сериализации или десериализации
 * объектов с использованием ObjectMapper.
 */
public class JsonMapperException extends RuntimeException {

  public JsonMapperException(String message, Throwable cause) {
    super(message, cause);
  }
}
