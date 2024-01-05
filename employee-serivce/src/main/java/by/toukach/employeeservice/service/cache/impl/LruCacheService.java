package by.toukach.employeeservice.service.cache.impl;

import by.toukach.employeeservice.config.ApplicationProperty;
import by.toukach.employeeservice.enumiration.CacheAlgorithm;
import by.toukach.employeeservice.service.cache.CacheService;
import by.toukach.employeeservice.util.predicate.NegatedWrappedIdPredicate;
import by.toukach.employeeservice.util.predicate.WrappedIdPredicate;
import by.toukach.employeeservice.util.predicate.WrappedItemPredicate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Класс для кэширования сущностей.
 *
 * @param <I> сущность для кэширования.
 */
@Service
@RequiredArgsConstructor
public class LruCacheService<I> implements CacheService<I, LocalDateTime> {

  private Map<I, LocalDateTime> cacheMap = new HashMap<>();
  private final ApplicationProperty applicationProperty;

  /**
   * Метод для получения закэшированной сущности.
   *
   * @param id id запрашиваемой сущности.
   * @return Optional с сущностью.
   */
  @Override
  public Optional<I> read(Long id) {

    Optional<Entry<I, LocalDateTime>> cacheEntryOptional = cacheMap.entrySet().stream()
        .filter(new WrappedIdPredicate<>(id))
        .findFirst();

    cacheEntryOptional.ifPresent(e -> e.setValue(LocalDateTime.now()));

    return cacheEntryOptional.map(Entry::getKey);
  }

  /**
   * Метод для сохранения сущности в кэше.
   *
   * @param item сущность для сохранения.
   */
  @Override
  public void create(I item) {
    Integer cacheSize = Integer.valueOf(applicationProperty.getCacheSize());

    if (cacheSize.compareTo(cacheMap.size()) <= 0) {
      cacheMap.entrySet().stream()
          .max(Entry.comparingByValue(Comparator.reverseOrder()))
          .map(Entry::getKey)
          .ifPresent(cacheMap::remove);
    }

    cacheMap.put(item, LocalDateTime.now());
  }

  /**
   * Метод для обновления сущности в кэше.
   *
   * @param item обновляемая сущность.
   */
  @Override
  public void update(I item) {

    cacheMap.entrySet().stream()
        .filter(new WrappedItemPredicate<>(item))
        .findFirst()
        .ifPresentOrElse(e -> cacheMap.computeIfPresent(e.getKey(),
                (key, value) -> {
                  key = item;
                  return LocalDateTime.now();
                }),
            () -> create(item));
  }

  /**
   * Метод для удаления сущности из кэша.
   *
   * @param id id удаляемой сущности.
   */
  @Override
  public void delete(Long id) {

    cacheMap = cacheMap.entrySet().stream()
        .filter(new NegatedWrappedIdPredicate<>(id))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }

  /**
   * Метод для получения содержимого кэша.
   *
   * @return запрашиваемое содержимое.
   */
  @Override
  public Map<I, LocalDateTime> getCacheContent() {
    return Collections.unmodifiableMap(cacheMap);
  }

  /**
   * Метод для очистки содержимого кэша.
   */
  @Override
  public void clearCache() {
    cacheMap.clear();
  }

  /**
   * Метод для получения типа алгоритма, по которому работает сервис.
   *
   * @return запрашиваемый тип.
   */
  @Override
  public CacheAlgorithm getCacheAlgorithm() {
    return CacheAlgorithm.LRU;
  }
}
