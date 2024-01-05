package by.toukach.employeeservice.aspect;

import by.toukach.employeeservice.config.ApplicationProperty;
import by.toukach.employeeservice.dao.Employee;
import by.toukach.employeeservice.enumiration.CacheAlgorithm;
import by.toukach.employeeservice.service.cache.CacheService;
import by.toukach.employeeservice.service.cache.CacheServiceFactory;
import java.util.Arrays;
import java.util.Optional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Аспект для синхронизации работы с кэшем и БД.
 */
@Aspect
public class CacheableAspect {

  private CacheService<Employee, ?> cacheService;
  private ApplicationProperty applicationProperty;

  @Autowired
  public void setApplicationProperty(ApplicationProperty applicationProperty) {
    this.applicationProperty = applicationProperty;
  }

  @Autowired
  public void setCacheService(CacheServiceFactory cacheServiceFactory) {
    cacheService = cacheServiceFactory
        .getCashService(CacheAlgorithm.valueOf(applicationProperty.getCacheAlgorithm()));
  }

  @Pointcut("@annotation(by.toukach.employeeservice.aspect.annotation.CacheableCreate)")
  public void annotatedByCacheableCreate() {
  }

  @Pointcut("@annotation(by.toukach.employeeservice.aspect.annotation.CacheableRead)")
  public void annotatedByCacheableRead() {
  }

  @Pointcut("@annotation(by.toukach.employeeservice.aspect.annotation.CacheableUpdate)")
  public void annotatedByCacheableUpdate() {
  }

  @Pointcut("@annotation(by.toukach.employeeservice.aspect.annotation.CacheableDelete)")
  public void annotatedByCacheableDelete() {
  }

  /**
   * Совет для обработки метода по созданию объекта в приложении.
   *
   * @param proceedingJoinPoint точка привязки.
   * @return стандартный для выполненного метода возвращаемый объект.
   * @throws Throwable стандартное исключение.
   */
  @Around("annotatedByCacheableCreate()")
  public Object create(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

    Employee employee = (Employee) proceedingJoinPoint.proceed();
    cacheService.create(employee);

    return employee;
  }

  /**
   * Совет для обработки метода по чтению объекта из приложения.
   *
   * @param proceedingJoinPoint точка привязки.
   * @return стандартный для выполненного метода возвращаемый объект.
   * @throws Throwable стандартное исключение.
   */
  @Around("annotatedByCacheableRead()")
  public Object read(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

    Long id = (Long) Arrays.stream(proceedingJoinPoint.getArgs())
        .findFirst()
        .orElseThrow();

    Optional<Employee> optionalEmployee = cacheService.read(id);

    if (optionalEmployee.isPresent()) {
      return optionalEmployee;

    } else {

      Optional<Employee> result = (Optional<Employee>) proceedingJoinPoint.proceed();
      result.ifPresent(cacheService::create);

      return result;
    }
  }

  /**
   * Совет для обработки метода по обновлению объекта в приложении.
   *
   * @param proceedingJoinPoint точка привязки.
   * @return стандартный для выполненного метода возвращаемый объект.
   * @throws Throwable стандартное исключение.
   */
  @Around("annotatedByCacheableUpdate()")
  public Object update(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

    Optional<Employee> optionalEmployee = (Optional<Employee>) proceedingJoinPoint.proceed();
    optionalEmployee.ifPresent(cacheService::update);

    return optionalEmployee;
  }

  /**
   * Совет для обработки метода по удалению объекта в приложении.
   *
   * @param proceedingJoinPoint точка привязки.
   * @return стандартный для выполненного метода возвращаемый объект.
   * @throws Throwable стандартное исключение.
   */
  @Around("annotatedByCacheableDelete()")
  public Object delete(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

    Arrays.stream(proceedingJoinPoint.getArgs())
        .findFirst()
        .ifPresent(id -> cacheService.delete((Long) id));

    return proceedingJoinPoint.proceed();
  }
}
