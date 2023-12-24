package by.toukach.employeeservice.exception;

/**
 * Класс представляющий исключение,
 * выбрасываемое в случае отсутствия запрашиваемой сущности в памяти.
 * */
public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException() {
  }

  public EntityNotFoundException(String message) {
    super(message);
  }
}
