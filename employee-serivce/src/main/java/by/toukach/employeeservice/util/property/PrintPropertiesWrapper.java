package by.toukach.employeeservice.util.property;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс отражающий параметры печати в конфигурационном файле.
 */
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PrintPropertiesWrapper {

  private String outDirectory;
  private String fontFilePath;
  private String templateFilePath;
}
