package by.toukach.employeeservice.controller.view;

import by.toukach.employeeservice.service.employee.EmployeeService;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс для вывода формы со списком всех сотрудников.
 */
@Slf4j
public class GetAllEmployeeChain extends ActionViewChain {

  @Override
  public void handle() {
    EmployeeService employeeService = getEmployeeService();
    employeeService.getAll().forEach(e -> log.info(e.toString()));

    setNextViewChain(new ActionListViewChain());
  }
}
