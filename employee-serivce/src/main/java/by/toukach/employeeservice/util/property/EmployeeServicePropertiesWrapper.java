package by.toukach.employeeservice.util.property;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс отражающий параметры приложения в конфигурационном файле.
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeServicePropertiesWrapper {

  private DataBasePropertiesWrapper database;
  private CachePropertiesWrapper cache;
}
