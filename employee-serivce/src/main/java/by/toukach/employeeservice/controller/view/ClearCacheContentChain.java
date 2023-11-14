package by.toukach.employeeservice.controller.view;

import by.toukach.employeeservice.enumiration.CacheAlgorithm;
import by.toukach.employeeservice.service.cache.CacheService;
import by.toukach.employeeservice.service.cache.CacheServiceFactory;
import by.toukach.employeeservice.service.cache.impl.CacheServiceFactoryImpl;
import by.toukach.employeeservice.util.property.ApplicationProperties;

/**
 * Класс для отправки запроса на очистку кэша.
 */
public class ClearCacheContentChain extends ActionViewChain {

  private final CacheService cacheService;

  /**
   * Конструктор для создания класса. Инициализирует cacheService.
   */
  public ClearCacheContentChain() {
    CacheServiceFactory cacheServiceFactory = CacheServiceFactoryImpl.getInstance();
    cacheService = cacheServiceFactory.getCashService(CacheAlgorithm.valueOf(
        ApplicationProperties.CACHE_ALGORITHM));
  }

  @Override
  public void handle() {
    cacheService.clearCache();

    setNextViewChain(new ActionListViewChain());
  }
}
