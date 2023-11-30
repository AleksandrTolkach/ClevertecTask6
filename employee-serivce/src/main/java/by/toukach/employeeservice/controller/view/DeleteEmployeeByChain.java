package by.toukach.employeeservice.controller.view;

import by.toukach.employeeservice.service.employee.EmployeeService;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс для вывода формы по удалению сотрудника.
 */
@Slf4j
public class DeleteEmployeeByChain extends ActionViewChain {

  @Override
  public void handle() {
    log.info(ViewMessage.EMPLOYEE_ID);
    Scanner scanner = getScanner();

    long id = scanner.nextLong();
    scanner.nextLine();

    EmployeeService employeeService = getEmployeeService();
    employeeService.delete(id);

    setNextViewChain(new ActionListViewChain());
  }
}
