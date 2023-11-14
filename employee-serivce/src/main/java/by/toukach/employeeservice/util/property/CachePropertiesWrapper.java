package by.toukach.employeeservice.util.property;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс отражающий параметры кэша в конфигурационном файле.
 */
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CachePropertiesWrapper {

  private String size;
  private String algorithm;
}
