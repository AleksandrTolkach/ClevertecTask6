package by.toukach.employeeservice.controller.view;

import by.toukach.employeeservice.enumiration.CacheAlgorithm;
import by.toukach.employeeservice.service.cache.CacheService;
import by.toukach.employeeservice.service.cache.CacheServiceFactory;
import by.toukach.employeeservice.service.cache.impl.CacheServiceFactoryImpl;
import by.toukach.employeeservice.util.property.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс для вывода формы с контентом кэша.
 */
@Slf4j
public class CacheContentChain extends ActionViewChain {

  private final CacheService<?, ?> cacheService;

  /**
   * Конструктор для создания формы с контентом кэша.
   */
  public CacheContentChain() {
    CacheServiceFactory cacheServiceFactory = CacheServiceFactoryImpl.getInstance();
    cacheService = cacheServiceFactory.getCashService(CacheAlgorithm.valueOf(
        ApplicationProperties.CACHE_ALGORITHM));
  }

  @Override
  public void handle() {
    cacheService.getCacheContent().entrySet().stream()
            .forEach(c -> log.info(c.toString()));

    setNextViewChain(new ActionListViewChain());
  }
}
