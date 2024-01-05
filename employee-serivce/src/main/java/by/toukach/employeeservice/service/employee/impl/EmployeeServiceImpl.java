package by.toukach.employeeservice.service.employee.impl;

import by.toukach.employeeservice.aspect.annotation.Validated;
import by.toukach.employeeservice.dao.Employee;
import by.toukach.employeeservice.dto.EmployeeDto;
import by.toukach.employeeservice.dto.InfoEmployeeDto;
import by.toukach.employeeservice.dto.Page;
import by.toukach.employeeservice.dto.Pageable;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.mapper.EmployeeMapper;
import by.toukach.employeeservice.repository.EmployeeRepository;
import by.toukach.employeeservice.service.employee.EmployeeService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Класс для работы с сотрудниками.
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;

  /**
   * Метод для создания сотрудника в приложении.
   *
   * @param employeeDto сотрудник для создания.
   * @return информаци о созданном сотруднике.
   */
  @Override
  @Validated
  @Transactional
  public InfoEmployeeDto create(EmployeeDto employeeDto) {

    Employee employee = employeeMapper.employeeDtoToEmployee(employeeDto);
    employee.setCreatedAt(LocalDateTime.now());

    return employeeMapper.employeeToInfoEmployeeDto(employeeRepository.save(employee));
  }

  /**
   * Метод для поиска информации о сотруднике в приложении по его id.
   *
   * @param id id запрашиваемого сотрудника.
   * @return запрашиваемая информация о сотруднике.
   */
  @Override
  @Validated
  public InfoEmployeeDto getById(Long id) {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format(ExceptionMessage.EMPLOYEE_BY_ID_NOT_FOUND, id)));
    return employeeMapper.employeeToInfoEmployeeDto(employee);
  }

  /**
   * Метод для поиска информации о всех сотрудников в приложении.
   *
   * @return запрашиваемая информация о сотрудниках.
   */
  @Override
  public Page<InfoEmployeeDto> getAll(Pageable pageable) {
    return employeeMapper.toInfoEmployeeDtoPage(employeeRepository.findAll(pageable));
  }

  /**
   * Метод для обновления сотрудника в приложении по его id.
   *
   * @param id id обновляемого пользователя.
   * @param employeeDto обновляемая информация о сотруднике.
   * @return обновленная информация о сотруднике.
   */
  @Override
  @Validated
  @Transactional
  public InfoEmployeeDto update(Long id, EmployeeDto employeeDto) {

    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format(ExceptionMessage.EMPLOYEE_BY_ID_NOT_FOUND, id)));

    employee = employeeMapper.merge(employee, employeeDto);

    employee = employeeRepository.update(id, employee)
        .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.EMPLOYEE_BY_ID_NOT_FOUND));

    return employeeMapper.employeeToInfoEmployeeDto(employee);
  }

  /**
   * Метод для удаления сотрудника из приложения по его id.
   *
   * @param id id удаляемого пользователя.
   */
  @Override
  @Validated
  public void delete(Long id) {
    employeeRepository.delete(id);
  }
}
