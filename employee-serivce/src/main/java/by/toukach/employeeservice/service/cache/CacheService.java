package by.toukach.employeeservice.service.cache;

import java.util.Map;
import java.util.Optional;

/**
 * Интерфейс для работы с кэшем.
 *
 * @param <I> сущность для обработки.
 * @param <C> объект сравнения сущностей.
 */
public interface CacheService<I, C> {

  Optional<I> read(Long id);

  void create(I item);

  void update(I item);

  void delete(Long id);

  Map<I, C> getCacheContent();

  void clearCache();
}
