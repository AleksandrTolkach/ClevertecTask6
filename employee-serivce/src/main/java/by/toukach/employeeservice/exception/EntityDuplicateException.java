package by.toukach.employeeservice.exception;

/**
 * Класс представляющий исключение,
 * выбрасываемое при попытке записать в память дублирующие значения.
 * */
public class EntityDuplicateException extends RuntimeException {

  public EntityDuplicateException(String message) {
    super(message);
  }
}
