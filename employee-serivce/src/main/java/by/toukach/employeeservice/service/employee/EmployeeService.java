package by.toukach.employeeservice.service.employee;

import by.toukach.employeeservice.dto.EmployeeDto;
import by.toukach.employeeservice.dto.InfoEmployeeDto;
import by.toukach.employeeservice.dto.Page;
import by.toukach.employeeservice.dto.Pageable;

/**
 * Интерфейс для работы с сотрудниками.
 */
public interface EmployeeService {

  InfoEmployeeDto create(EmployeeDto employeeDto);

  InfoEmployeeDto getById(Long id);

  Page<InfoEmployeeDto> getAll(Pageable pageable);

  InfoEmployeeDto update(Long id, EmployeeDto employeeDto);

  void delete(Long id);
}
