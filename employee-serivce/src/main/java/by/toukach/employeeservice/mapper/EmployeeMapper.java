package by.toukach.employeeservice.mapper;

import by.toukach.employeeservice.dao.Employee;
import by.toukach.employeeservice.dto.EmployeeDto;
import by.toukach.employeeservice.dto.InfoEmployeeDto;
import by.toukach.employeeservice.dto.Page;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Интерфейс для преобразования {@link Employee} в {@link InfoEmployeeDto},
 * {@link EmployeeDto} в {@link Employee} и наоборот.
 */
@Mapper
public interface EmployeeMapper {

  /**
   * Метод для преобразования {@link Employee} в {@link EmployeeDto}.
   *
   * @param employee {@link Employee} для преобразования.
   * @return преобразованный {@link InfoEmployeeDto}.
   */
  InfoEmployeeDto employeeToInfoEmployeeDto(Employee employee);

  /**
   * Метод для преобразования {@link EmployeeDto} в {@link Employee}.
   *
   * @param employeeDto {@link EmployeeDto} для преобразования.
   * @return преобразованный {@link Employee}.
   */
  Employee employeeDtoToEmployee(EmployeeDto employeeDto);

  /**
   * Метод для сливания существующего {@link Employee} с информацией из {@link EmployeeDto}.
   *
   * @param employee {@link Employee} для сливания.
   * @param employeeDto информация для обновления.
   * @return обновленный {@link Employee}.
   */
  Employee merge(@MappingTarget Employee employee, EmployeeDto employeeDto);

  /**
   * Метод для преобразования страницы {@link Employee} в страницу {@link InfoEmployeeDto}.
   *
   * @param employeePage страница {@link Employee} для преобразования.
   * @return преобразованная страница {@link InfoEmployeeDto}.
   */
  Page<InfoEmployeeDto> toInfoEmployeeDtoPage(Page<Employee> employeePage);

}
