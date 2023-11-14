package by.toukach.employeeservice.controller.view;

import by.toukach.employeeservice.dto.EmployeeDto;
import by.toukach.employeeservice.service.employee.EmployeeService;
import by.toukach.employeeservice.service.employee.impl.EmployeeServiceImpl;
import by.toukach.employeeservice.util.JsonUtil;
import by.toukach.employeeservice.util.XmlUtil;
import java.util.Scanner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс для вывода формы со списком действий над сотрудниками.
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public abstract class ActionViewChain extends ViewChain {

  private final EmployeeService employeeService;

  protected ActionViewChain() {
    employeeService = EmployeeServiceImpl.getInstance();
  }

  /**
   * Метод для чтения сотрудника из консоли и преобразования в POJO.
   *
   * @return переданный сотрудник.
   */
  protected EmployeeDto prepareEmployeeDtoFromJson() {
    log.info(ViewMessage.EMPLOYEE_JSON);

    Scanner scanner = getScanner();
    StringBuilder employeeDtoAsJson = new StringBuilder();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.isEmpty()) {
        break;
      }
      employeeDtoAsJson.append(line);
    }

    return JsonUtil.mapToPojo(employeeDtoAsJson.toString(), EmployeeDto.class);
  }

  protected EmployeeDto prepareEmployeeDtoFromXml() {
    log.info(ViewMessage.EMPLOYEE_XML);

    Scanner scanner = getScanner();
    StringBuilder employeeDtoAsXml = new StringBuilder();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.isEmpty()) {
        break;
      }
      employeeDtoAsXml.append(line);
    }

    return XmlUtil.mapToPojo(employeeDtoAsXml.toString(), EmployeeDto.class);
  }
}
