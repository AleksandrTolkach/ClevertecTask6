package by.toukach.employeeservice.service.cache.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import by.toukach.employeeservice.enumiration.CacheAlgorithm;
import by.toukach.employeeservice.service.cache.CacheService;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CacheServiceFactoryImplTest {

  private CacheServiceFactoryImpl cacheServiceFactory;

  @Mock
  private LruCacheService<?> lruCacheService;

  @Mock
  private LfuCacheService<?> lfuCacheService;

  @BeforeEach
  public void setUp() {
    when(lfuCacheService.getCacheAlgorithm())
        .thenReturn(CacheAlgorithm.LFU);
    when(lruCacheService.getCacheAlgorithm())
        .thenReturn(CacheAlgorithm.LRU);

    cacheServiceFactory = new CacheServiceFactoryImpl(List.of(lruCacheService, lfuCacheService));
  }

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
