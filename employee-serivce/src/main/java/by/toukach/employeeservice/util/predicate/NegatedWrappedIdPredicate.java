package by.toukach.employeeservice.util.predicate;

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
public class NegatedWrappedIdPredicate<I> implements Predicate<Entry<I, ?>>  {

  private static final String GET_ID_METHOD_FIELD = "getId";

  private Long objectId;

  /**
   * Метод для сравнения id сущности в Entry с objectId
   * и реверсирующий полученный результат сравнения.
   *
   * @param entry Entry с сущностью.
   * @return возвращает false, если ID сущности совпадает с objectId, true если нет.
   */
  @Override
  public boolean test(Entry<I, ?> entry) {
    return !WrappedIdPredicate.builder()
        .objectId(objectId)
        .build()
        .test((Entry<Object, ?>) entry);
  }
}
