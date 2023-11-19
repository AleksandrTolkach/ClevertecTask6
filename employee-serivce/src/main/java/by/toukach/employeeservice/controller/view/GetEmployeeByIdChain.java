package by.toukach.employeeservice.controller.view;

import by.toukach.employeeservice.service.employee.EmployeeService;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс для вывода формы с информацией о сотруднике.
 */
@Slf4j
public class GetEmployeeByIdChain extends ActionViewChain {

  @Override
  public void handle() {
    log.info(ViewMessage.EMPLOYEE_ID);
    Scanner scanner = getScanner();

    long id = scanner.nextLong();
    scanner.nextLine();

    EmployeeService employeeService = getEmployeeService();
    log.info(employeeService.getById(id).toString());

    setNextViewChain(new ActionListViewChain());
  }
}
