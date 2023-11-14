package by.toukach.employeeservice.aspect;

import by.toukach.employeeservice.exception.ValidationError;
import by.toukach.employeeservice.exception.ValidationExceptionList;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Аспект для валидации объектов.
 */
@Aspect
public class ValidatedAspect {

  private static final ValidatorFactory validatorFactory =
      Validation.buildDefaultValidatorFactory();

  @Pointcut("@annotation(by.toukach.employeeservice.aspect.annotation.Validated)")
  public void annotatedByValidated() {
  }

  /**
   * Совет для выполнения валидации аргументов метода.
   *
   * @param proceedingJoinPoint точка привязки.
   * @return стандартный для выполненного метода возвращаемый объект.
   * @throws Throwable стандартное исключение.
   */
  @Around("annotatedByValidated()")
  public Object validate(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

    Validator validator = validatorFactory.getValidator();

    ValidationExceptionList validationExceptionList = new ValidationExceptionList();

    Arrays.stream(proceedingJoinPoint.getArgs())
        .map(validator::validate)
        .filter(s -> !s.isEmpty())
        .forEach(s -> s.stream()
            .map(a -> new ValidationError(a.getPropertyPath().toString(), a.getMessage()))
            .forEach(validationExceptionList::addError));

    if (!validationExceptionList.isEmpty()) {
      throw validationExceptionList;
    }

    return proceedingJoinPoint.proceed();
  }
}
