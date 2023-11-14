package by.toukach.employeeservice.service.cache.impl;

import by.toukach.employeeservice.enumiration.CacheAlgorithm;
import by.toukach.employeeservice.service.cache.CacheService;
import by.toukach.employeeservice.service.cache.CacheServiceFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, представляющий фабрику для создания CacheService.
 */
public class CacheServiceFactoryImpl implements CacheServiceFactory {

  private static final CacheServiceFactory instance = new CacheServiceFactoryImpl();

  private final Map<CacheAlgorithm, CacheService<?, ?>> cacheServiceMap = new HashMap<>();

  private CacheServiceFactoryImpl() {
    cacheServiceMap.put(CacheAlgorithm.LRU, LruCacheService.getInstance());
    cacheServiceMap.put(CacheAlgorithm.LFU, LfuCacheService.getInstance());
  }

  /**
   * Метод для получения CacheService по алгоритму кэширования.
   *
   * @param cacheAlgorithm используемый кэш алгоритм.
   * @param <I> сущность кэширования.
   * @param <C> объект сравнения сущностей в кэше.
   * @return запрашиваемый CacheService.
   */
  @Override
  public <I, C> CacheService<I, C> getCashService(CacheAlgorithm cacheAlgorithm) {
    return (CacheService<I, C>) cacheServiceMap.get(cacheAlgorithm);
  }

  /**
   * Метод для получения фабрики.
   *
   * @return запрашиваемая фабрика.
   */
  public static CacheServiceFactory getInstance() {
    return instance;
  }
}
