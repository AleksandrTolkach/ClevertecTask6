package by.toukach.employeeservice.service.cache.impl;

import by.toukach.employeeservice.service.cache.CacheService;
import by.toukach.employeeservice.util.predicate.NegatedWrappedIdPredicate;
import by.toukach.employeeservice.util.predicate.WrappedIdPredicate;
import by.toukach.employeeservice.util.predicate.WrappedItemPredicate;
import by.toukach.employeeservice.util.property.ApplicationProperties;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс для кэширования сущностей.
 *
 * @param <I> сущность для кэширования.
 */
public class LfuCacheService<I> implements CacheService<I, Long> {

  private static final LfuCacheService<?> instance = new LfuCacheService<>();

  private Map<I, Long> cacheMap;
  private final long cacheSize;

  private LfuCacheService() {
    cacheMap = new HashMap<>();
    cacheSize = Long.parseLong(ApplicationProperties.CACHE_SIZE);
  }

  /**
   * Метод для получения закэшированной сущности.
   *
   * @param id id запрашиваемой сущности.
   * @return Optional с сущностью.
   */
  @Override
  public Optional<I> read(Long id) {

    Optional<Entry<I, Long>> cacheEntryOptional = cacheMap.entrySet().stream()
        .filter(new WrappedIdPredicate<>(id))
        .findFirst();

    cacheEntryOptional.ifPresent(e -> e.setValue(e.getValue() + 1));

    return cacheEntryOptional.map(Entry::getKey);
  }

  /**
   * Метод для сохранения сущности в кэше.
   *
   * @param item сущность для сохранения.
   */
  @Override
  public void create(I item) {

    if (cacheMap.size() >= cacheSize) {
      cacheMap.entrySet().stream()
          .max(Entry.comparingByValue(Comparator.reverseOrder()))
          .map(Entry::getKey)
          .ifPresent(cacheMap::remove);
    }

    cacheMap.put(item, 1L);
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
                  return value + 1;
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
  public Map<I, Long> getCacheContent() {
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
   * Метод для получения CacheService.
   *
   * @param <I> сущность кэширования.
   * @return запрашиваемый CacheSerivce.
   */
  public static <I> LfuCacheService<I> getInstance() {
    return (LfuCacheService<I>) instance;
  }
}
