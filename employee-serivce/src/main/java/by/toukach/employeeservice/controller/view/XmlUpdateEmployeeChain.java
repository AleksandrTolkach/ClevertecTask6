package by.toukach.employeeservice.controller.view;

import by.toukach.employeeservice.dto.EmployeeDto;
import by.toukach.employeeservice.service.employee.EmployeeService;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс для вывода формы по обновлению сотрудника в формате XML.
 */
@Slf4j
public class XmlUpdateEmployeeChain extends ActionViewChain {

  @Override
  public void handle() {
    log.info(ViewMessage.EMPLOYEE_ID);
    Scanner scanner = getScanner();
    long id = scanner.nextLong();
    scanner.nextLine();

    EmployeeDto employeeDto = prepareEmployeeDtoFromXml();

    EmployeeService employeeService = getEmployeeService();
    employeeService.update(id, employeeDto);

    setNextViewChain(new ActionListViewChain());
  }
}
