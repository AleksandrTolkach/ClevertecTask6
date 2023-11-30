package by.toukach.employeeservice.mapper;

import by.toukach.employeeservice.dao.Employee;
import by.toukach.employeeservice.dto.EmployeeDto;
import by.toukach.employeeservice.dto.InfoEmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Интерфейс для преобразования Employee в InfoEmployeeDto, EmployeeDto в Employee и наоборот.
 */
@Mapper
public interface EmployeeMapper {

  /**
   * Метод для преобразования Employee в EmployeeDto.
   *
   * @param employee Employee для преобразования.
   * @return преобразованный InfoEmployeeDto.
   */
  InfoEmployeeDto employeeToInfoEmployeeDto(Employee employee);

  /**
   * Метод для преобразования EmployeeDto в Employee.
   *
   * @param employeeDto EmployeeDto для преобразования.
   * @return преобразованный Employee.
   */
  Employee employeeDtoToEmployee(EmployeeDto employeeDto);

  /**
   * Метод для сливания существующего сотрудника с информацией из DTO.
   *
   * @param employee сотрудник для сливания.
   * @param employeeDto информация для обновления.
   * @return обновленный сотрудник.
   */
  Employee merge(@MappingTarget Employee employee, EmployeeDto employeeDto);
}
