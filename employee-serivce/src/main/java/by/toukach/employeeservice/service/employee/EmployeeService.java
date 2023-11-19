package by.toukach.employeeservice.service.employee;

import by.toukach.employeeservice.dto.EmployeeDto;
import by.toukach.employeeservice.dto.InfoEmployeeDto;
import java.util.List;

/**
 * Интерфейс для работы с сотрудниками.
 */
public interface EmployeeService {

  InfoEmployeeDto create(EmployeeDto employeeDto);

  InfoEmployeeDto getById(Long id);

  List<InfoEmployeeDto> getAll();

  InfoEmployeeDto update(Long id, EmployeeDto employeeDto);

  void delete(Long id);
}
