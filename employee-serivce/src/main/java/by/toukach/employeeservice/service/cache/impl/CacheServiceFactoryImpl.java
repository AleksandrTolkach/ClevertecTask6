package by.toukach.employeeservice.service.cache.impl;

import by.toukach.employeeservice.enumiration.CacheAlgorithm;
import by.toukach.employeeservice.service.cache.CacheService;
import by.toukach.employeeservice.service.cache.CacheServiceFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Класс, представляющий фабрику для создания CacheService.
 */
@Service
public class CacheServiceFactoryImpl implements CacheServiceFactory {

  private final Map<CacheAlgorithm, CacheService<?, ?>> cacheServiceMap;

  public CacheServiceFactoryImpl(List<CacheService<?, ?>> cacheServices) {
    cacheServiceMap = cacheServices.stream()
        .collect(Collectors.toMap(CacheService::getCacheAlgorithm, c -> c));
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
}
