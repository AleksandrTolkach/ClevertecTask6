package by.toukach.employeeservice.controller.view;

import by.toukach.employeeservice.dto.EmployeeDto;
import by.toukach.employeeservice.service.employee.EmployeeService;

/**
 * Класс для вывода формы по созданию сотрудника в формате XML.
 */
public class XmlCreateEmployeeChain extends ActionViewChain {

  @Override
  public void handle() {
    EmployeeDto employeeDto = prepareEmployeeDtoFromXml();

    EmployeeService employeeService = getEmployeeService();
    employeeService.create(employeeDto);

    setNextViewChain(new ActionListViewChain());
  }
}
