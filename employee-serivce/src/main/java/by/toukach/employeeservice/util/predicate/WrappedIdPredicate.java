package by.toukach.employeeservice.util.predicate;

import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.exception.MethodException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map.Entry;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Класс представляющий предикат для сравнения ID сущности с переданным в конструктор ID.
 *
 * @param <I> сущность кэширования.
 */
@Data
@Builder
@AllArgsConstructor
public class WrappedIdPredicate<I> implements Predicate<Entry<I, ?>>  {

  private static final String GET_ID_METHOD_FIELD = "getId";

  private Long objectId;

  /**
   * Метод для сравнения id сущности в Entry с objectId
   * и реверсирующий полученный результат сравнения.
   *
   * @param entry Entry с сущностью.
   * @return возвращает true, если ID сущности совпадает с objectId, false если нет.
   */
  @Override
  public boolean test(Entry<I, ?> entry) {
    try {
      Object id = entry.getKey().getClass().getDeclaredMethod(GET_ID_METHOD_FIELD)
          .invoke(entry.getKey());

      return id.equals(objectId);

    } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException ex) {
      throw new MethodException(
          String.format(ExceptionMessage.INVOKE_METHOD, GET_ID_METHOD_FIELD), ex);
    }
  }
}
