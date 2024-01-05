package by.toukach.employeeservice.config;

import by.toukach.employeeservice.aspect.CacheableAspect;
import by.toukach.employeeservice.aspect.ValidatedAspect;
import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Конфигурационный класс для аспектов.
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectConfig {

  /**
   * Метод для создания бина {@link CacheableAspect}.
   *
   * @return запрашиваемый {@link CacheableAspect}.
   */
  @Bean
  public CacheableAspect cacheableAspect() {
    return Aspects.aspectOf(CacheableAspect.class);
  }

  /**
   * Метод для создания бина {@link ValidatedAspect}.
   *
   * @return запрашиваемый {@link ValidatedAspect}.
   */
  @Bean
  public ValidatedAspect validatedAspect() {
    return Aspects.aspectOf(ValidatedAspect.class);
  }
}
