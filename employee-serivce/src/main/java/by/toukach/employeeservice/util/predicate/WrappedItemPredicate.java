package by.toukach.employeeservice.util.predicate;

import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.exception.ReflectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map.Entry;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Класс представляющий предикат для сравнения сущности с переданной в конструктор сущностью по ID.
 *
 * @param <I> сущность кэширования.
 */
@Data
@Builder
@AllArgsConstructor
public class WrappedItemPredicate<I> implements Predicate<Entry<I, ?>>  {

  private static final String GET_ID_METHOD_FIELD = "getId";

  private I item;

  /**
   * Метод для сравнения сущности в Entry с item по id.
   *
   * @param entry Entry с сущностью.
   * @return возвращает true, если ID сущности совпадает с ID сущности item, false если нет.
   */
  @Override
  public boolean test(Entry<I, ?> entry) {
    try {
      Long id = (Long) item.getClass().getDeclaredMethod(GET_ID_METHOD_FIELD).invoke(item);

      return WrappedIdPredicate.builder()
          .objectId(id)
          .build()
          .test((Entry<Object, Long>) entry);

    } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
      throw new ReflectionException(
          String.format(ExceptionMessage.INVOKE_METHOD, GET_ID_METHOD_FIELD), e);
    }
  }
}
