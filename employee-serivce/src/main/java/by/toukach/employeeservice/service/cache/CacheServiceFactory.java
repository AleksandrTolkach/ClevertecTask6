package by.toukach.employeeservice.service.cache;

import by.toukach.employeeservice.enumiration.CacheAlgorithm;

/**
 * Интерфейс для работы с фабрикой кэша.
 */
public interface CacheServiceFactory {

  <I, C> CacheService<I, C> getCashService(CacheAlgorithm cacheAlgorithm);
}
