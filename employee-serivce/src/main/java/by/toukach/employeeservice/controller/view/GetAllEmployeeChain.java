package by.toukach.employeeservice.controller.view;

import by.toukach.employeeservice.dto.Pageable;
import by.toukach.employeeservice.service.employee.EmployeeService;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс для вывода формы со списком всех сотрудников.
 */
@Slf4j
public class GetAllEmployeeChain extends ActionViewChain {

  @Override
  public void handle() {
    Pageable pageable = Pageable.builder()
        .pageNumber(0)
        .pageSize(20)
        .build();

    EmployeeService employeeService = getEmployeeService();
    employeeService.getAll(pageable).getContent().forEach(e -> log.info(e.toString()));

    log.info(ViewMessage.EMPLOYEE_ACTION_LIST);
    Scanner scanner = getScanner();
    int answer = scanner.nextInt();
    scanner.nextLine();

    if (answer == 1) {
      setNextViewChain(new ListEmployeeDocumentChain());
    } else {
      setNextViewChain(new ActionListViewChain());
    }
  }
}
