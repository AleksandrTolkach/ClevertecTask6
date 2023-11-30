package by.toukach.employeeservice.service.cache.impl;

import static org.assertj.core.api.Assertions.assertThat;

import by.toukach.employeeservice.enumiration.CacheAlgorithm;
import by.toukach.employeeservice.service.cache.CacheService;
import by.toukach.employeeservice.service.cache.CacheServiceFactory;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CacheServiceFactoryImplTest {

  private final CacheServiceFactory cacheServiceFactory = CacheServiceFactoryImpl.getInstance();

  @ParameterizedTest
  @MethodSource("cacheAlgorithmProvider")
  public void getCacheServiceTest(CacheAlgorithm cacheAlgorithm, Class<?> expected) {
    // given, when
    CacheService<Object, Object> actual = cacheServiceFactory.getCashService(cacheAlgorithm);

    // then
    assertThat(actual)
        .isInstanceOf(expected);
  }

  public static Stream<Arguments> cacheAlgorithmProvider() {
    return Stream.of(
        Arguments.arguments(CacheAlgorithm.LFU, LfuCacheService.class),
        Arguments.arguments(CacheAlgorithm.LRU, LruCacheService.class)
    );
  }
}
