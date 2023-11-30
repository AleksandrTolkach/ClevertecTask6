package by.toukach.employeeservice.controller.view;

import by.toukach.employeeservice.dto.EmployeeDto;
import by.toukach.employeeservice.service.employee.EmployeeService;

/**
 * Класс для вывода формы по созданию сотрудника в формате XML.
 */
public class JsonCreateEmployeeChain extends ActionViewChain {

  @Override
  public void handle() {
    EmployeeDto employeeDto = prepareEmployeeDtoFromJson();

    EmployeeService employeeService = getEmployeeService();
    employeeService.create(employeeDto);

    setNextViewChain(new ActionListViewChain());
  }
}
