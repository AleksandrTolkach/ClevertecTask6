package by.toukach.employeeservice.service.cache.impl;

import static org.assertj.core.api.Assertions.assertThat;

import by.toukach.employeeservice.EmployeeTestData;
import by.toukach.employeeservice.dao.Employee;
import by.toukach.employeeservice.enumiration.Specialization;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LfuCacheServiceTest {

  private LfuCacheService<Employee> cacheService;

  @BeforeEach
  public void setUp()
      throws NoSuchMethodException, InvocationTargetException, InstantiationException,
      IllegalAccessException {

    Constructor<LfuCacheService> privateConstructor = LfuCacheService.class
        .getDeclaredConstructor();
    privateConstructor.setAccessible(true);

    cacheService = privateConstructor.newInstance();
  }

  @Test
  public void readTestShouldReturnOptionalEmployee() {
    // given
    Employee employee = EmployeeTestData.builder()
        .build()
        .buildEmployee();
    cacheService.create(employee);

    // when
    Optional<Employee> expected = Optional.of(employee);
    Optional<Employee> actual = cacheService.read(1L);

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void readTestShouldReturnEmptyOptionalWhenEmployeeNotExist() {
    // given
    Optional<Employee> expected = Optional.empty();

    // when
    Optional<Employee> actual = cacheService.read(1L);

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void createTestShouldCreateEmployee() {
    // given
    Employee employee = EmployeeTestData.builder()
        .build()
        .buildEmployee();

    Map<Employee, Long> expected = new HashMap<>();
    expected.put(employee, 1L);

    // when
    cacheService.create(employee);
    Map<Employee, Long> actual = cacheService.getCacheContent();

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void createTestShouldRemoveNotPopularEmployeeFromCache() {
    // given
    Map<Employee, Long> expected = fillInCacheMap();

    Employee employeeToDelete = expected.entrySet().stream()
        .min((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
        .map(Entry::getKey)
        .get();

    expected.remove(employeeToDelete);

    Employee newEmployee = EmployeeTestData.builder()
        .withId(4)
        .build()
        .buildEmployee();
    expected.put(newEmployee, 1L);

    // when
    cacheService.create(newEmployee);
    Map<Employee, Long> actual = cacheService.getCacheContent();

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void updateTestShouldUpdateEmployee() {
    // given
    Map<Employee, Long> expected = fillInCacheMap();

    Employee employeeToUpdate = expected.keySet().stream()
        .filter(e -> e.getId().equals(1L))
        .findFirst()
        .get();

    employeeToUpdate.setName(EmployeeTestData.ANOTHER_NAME);
    employeeToUpdate.setDateOfBirth(EmployeeTestData.ANOTHER_DATE_OF_BIRTH);
    employeeToUpdate.setSpecialization(Specialization.BUSINESS_ANALYST);
    employeeToUpdate.setActive(false);

    // when
    cacheService.update(employeeToUpdate);
    Map<Employee, Long> actual = cacheService.getCacheContent();

    // then
    assertThat(actual.keySet())
        .containsAll(expected.keySet());
  }

  @Test
  public void updateTestShouldCreateEmployeeIfNotExist() {
    // given
    Employee employee = EmployeeTestData.builder().build().buildEmployee();

    Map<Employee, Long> expected = new HashMap<>();
    expected.put(employee, 1L);

    // when
    cacheService.update(employee);
    Map<Employee, Long> actual = cacheService.getCacheContent();

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void deleteTestShouldDeleteEmployee() {
    // given
    Map<Employee, Long> expected = fillInCacheMap();

    expected = expected.entrySet().stream()
        .filter(e -> !e.getKey().getId().equals(1L))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

    // when
    cacheService.delete(1L);
    Map<Employee, Long> actual = cacheService.getCacheContent();

    // then
    assertThat(actual)
        .isEqualTo(expected);

  }

  @Test
  public void getCacheContentTestShouldReturnCacheContent() {
    // given
    Map<Employee, Long> expected = fillInCacheMap();

    // when
    Map<Employee, Long> actual = cacheService.getCacheContent();

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void clearCacheTestShouldRemoveAllEntitiesInCache() {
    // given
    fillInCacheMap();
    Map<Employee, Long> expected = new HashMap<>();

    // when
    cacheService.clearCache();
    Map<Employee, Long> actual = cacheService.getCacheContent();

    //then
    assertThat(actual)
        .isEqualTo(expected);
  }

  private Map<Employee, Long> fillInCacheMap() {
    Map<Employee, Long> employeeMap = Stream.of(
            EmployeeTestData.builder().withId(1).build().buildEmployee(),
            EmployeeTestData.builder().withId(2).build().buildEmployee(),
            EmployeeTestData.builder().withId(3).build().buildEmployee())
        .collect(Collectors.toMap(e -> e, Employee::getId));

    employeeMap.forEach((k, v) -> {
      cacheService.create(k);
      for (long i = 0; i < v - 1; i++) {
        cacheService.read(k.getId());
      }
    });

    return employeeMap;
  }
}
