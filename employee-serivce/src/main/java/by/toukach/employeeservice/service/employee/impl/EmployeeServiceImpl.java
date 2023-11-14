package by.toukach.employeeservice.service.employee.impl;

import by.toukach.employeeservice.aspect.annotation.Validated;
import by.toukach.employeeservice.dao.Employee;
import by.toukach.employeeservice.dto.EmployeeDto;
import by.toukach.employeeservice.dto.InfoEmployeeDto;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.mapper.EmployeeMapper;
import by.toukach.employeeservice.repository.EmployeeRepository;
import by.toukach.employeeservice.repository.impl.EmployeeRepositoryImpl;
import by.toukach.employeeservice.service.employee.EmployeeService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;

/**
 * Класс для работы с сотрудниками.
 */
public class EmployeeServiceImpl implements EmployeeService {

  private static final EmployeeService instance = new EmployeeServiceImpl();

  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;

  private EmployeeServiceImpl() {
    employeeRepository = EmployeeRepositoryImpl.getInstance();
    employeeMapper = Mappers.getMapper(EmployeeMapper.class);
  }

  /**
   * Метод для создания сотрудника в приложении.
   *
   * @param employeeDto сотрудник для создания.
   * @return информаци о созданном сотруднике.
   */
  @Override
  @Validated
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
  public List<InfoEmployeeDto> getAll() {
    return employeeRepository.findAll().stream()
        .map(employeeMapper::employeeToInfoEmployeeDto)
        .collect(Collectors.toList());
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

  /**
   * Метод для получения EmployeeService.
   *
   * @return запрашиваемый EmployeeService.
   */
  public static EmployeeService getInstance() {
    return instance;
  }
}
