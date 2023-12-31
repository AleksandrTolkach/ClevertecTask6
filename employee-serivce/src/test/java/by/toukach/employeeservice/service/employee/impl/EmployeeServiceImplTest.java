package by.toukach.employeeservice.service.employee.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.toukach.employeeservice.EmployeeTestData;
import by.toukach.employeeservice.dao.Employee;
import by.toukach.employeeservice.dto.EmployeeDto;
import by.toukach.employeeservice.dto.EmployeeDto.Fields;
import by.toukach.employeeservice.dto.InfoEmployeeDto;
import by.toukach.employeeservice.dto.Page;
import by.toukach.employeeservice.dto.Pageable;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.exception.ValidationExceptionList;
import by.toukach.employeeservice.mapper.EmployeeMapper;
import by.toukach.employeeservice.repository.EmployeeRepository;
import by.toukach.employeeservice.repository.impl.EmployeeRepositoryImpl;
import by.toukach.employeeservice.util.ValidationMessage;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EmployeeServiceImplTest {

  @InjectMocks
  private EmployeeServiceImpl employeeService;

  @Mock
  private EmployeeRepository employeeRepository;

  @Mock
  private EmployeeMapper employeeMapper;

  @Test
  public void createTestShouldCreateEmployee() {
    // given
    EmployeeDto employeeDto = EmployeeTestData.builder()
        .build()
        .buildEmployeeDto();
    Employee employee = EmployeeTestData.builder()
        .build()
        .buildEmployee();
    InfoEmployeeDto expected = EmployeeTestData.builder()
        .build()
        .buildInfoEmployeeDto();

    when(employeeMapper.employeeDtoToEmployee(employeeDto))
        .thenReturn(employee);
    when(employeeRepository.save(any()))
        .thenReturn(employee);
    when(employeeMapper.employeeToInfoEmployeeDto(employee))
        .thenReturn(expected);

    // when
    InfoEmployeeDto actual = employeeService.create(employeeDto);

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void createTestShouldThrowExceptionWhenEmployeeFieldsInCorrect() {
    // given
    EmployeeDto employeeDto = EmployeeTestData.builder()
        .withName(Fields.name)
        .withDateOfBirth(LocalDate.MAX)
        .build()
        .buildEmployeeDto();

    // when, then
    assertThatThrownBy(() -> employeeService.create(employeeDto))
        .isInstanceOf(ValidationExceptionList.class)
        .hasMessageContaining(ValidationMessage.INCORRECT_NAME_FORM)
        .hasMessageContaining(ValidationMessage.INCORRECT_DATE_OF_BIRTH_FORM);
  }

  @Test
  public void getByIdTestShouldReturnInfoEmployeeDto() {
    // given
    Employee employee = EmployeeTestData.builder()
        .build()
        .buildEmployee();

    Long id = employee.getId();

    InfoEmployeeDto expected = EmployeeTestData.builder()
        .build()
        .buildInfoEmployeeDto();

    when(employeeRepository.findById(id))
        .thenReturn(Optional.of(employee));
    when(employeeMapper.employeeToInfoEmployeeDto(employee)).thenReturn(expected);

    // when
    InfoEmployeeDto actual = employeeService.getById(id);

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void getByIdTestShouldThrowExceptionWhenEmployeeNotExist() {
    // given
    when(employeeRepository.findById(Long.MAX_VALUE))
        .thenReturn(Optional.empty());

    String expected =
        String.format(ExceptionMessage.EMPLOYEE_BY_ID_NOT_FOUND, Long.MAX_VALUE);

    // when, then
    assertThatThrownBy(() -> employeeService.getById(Long.MAX_VALUE))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(expected);
  }

  @Test
  public void getAllTestShouldReturnEmployeeList() {
    // given
    Employee employee = EmployeeTestData.builder()
        .build()
        .buildEmployee();
    InfoEmployeeDto infoEmployeeDto = EmployeeTestData.builder()
        .build()
        .buildInfoEmployeeDto();

    List<Employee> content = List.of(employee);
    List<InfoEmployeeDto> expectedContent = List.of(infoEmployeeDto);

    Pageable pageable = Pageable.builder()
        .pageNumber(0)
        .pageSize(1)
        .build();

    Page<Employee> employeePage = Page.of(pageable, content, content.size());
    Page<InfoEmployeeDto> expected = Page.of(pageable, expectedContent, expectedContent.size());

    when(employeeRepository.findAll(pageable))
        .thenReturn(employeePage);
    when(employeeMapper.toInfoEmployeeDtoPage(employeePage))
        .thenReturn(expected);

    // when
    Page<InfoEmployeeDto> actual = employeeService.getAll(pageable);

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void updateTestShouldUpdateEmployee() {
    // given
    EmployeeDto employeeDto = EmployeeTestData.builder()
        .build()
        .buildEmployeeDto();
    InfoEmployeeDto expected = EmployeeTestData.builder()
        .build()
        .buildInfoEmployeeDto();
    Employee employee = EmployeeTestData.builder()
        .build()
        .buildEmployee();

    Long id = employee.getId();

    when(employeeRepository.findById(id))
        .thenReturn(Optional.of(employee));
    when(employeeMapper.merge(employee, employeeDto))
        .thenReturn(employee);
    when(employeeRepository.update(id, employee))
        .thenReturn(Optional.of(employee));
    when(employeeMapper.employeeToInfoEmployeeDto(employee))
        .thenReturn(expected);

    // when
    InfoEmployeeDto actual = employeeService.update(id, employeeDto);

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void updateTestShouldThrowExceptionWhenEmployeeNotExist() {
    // given
    EmployeeDto employeeDto = EmployeeTestData.builder()
        .build()
        .buildEmployeeDto();

    when(employeeRepository.findById(Long.MAX_VALUE))
        .thenReturn(Optional.empty());

    String expected =
        String.format(ExceptionMessage.EMPLOYEE_BY_ID_NOT_FOUND, Long.MAX_VALUE);

    // when, then
    assertThatThrownBy(() -> employeeService.update(Long.MAX_VALUE, employeeDto))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(expected);
  }

  @Test
  public void updateTestShouldThrowExceptionWhenEmployeeFieldsInCorrect() {
    // given
    EmployeeDto employeeDto = EmployeeTestData.builder()
        .withName(Fields.name)
        .withDateOfBirth(LocalDate.MAX)
        .build()
        .buildEmployeeDto();

    // when, then
    assertThatThrownBy(() -> employeeService.update(Long.MAX_VALUE, employeeDto))
        .isInstanceOf(ValidationExceptionList.class)
        .hasMessageContaining(ValidationMessage.INCORRECT_NAME_FORM)
        .hasMessageContaining(ValidationMessage.INCORRECT_DATE_OF_BIRTH_FORM);
  }

  @Test
  public void deleteTestShouldDeleteEmployee() {
    // given
    doNothing()
        .when(employeeRepository)
        .delete(Long.MAX_VALUE);

    // when
    employeeRepository.delete(Long.MAX_VALUE);

    // then
    verify(employeeRepository)
        .delete(Long.MAX_VALUE);
  }
}
