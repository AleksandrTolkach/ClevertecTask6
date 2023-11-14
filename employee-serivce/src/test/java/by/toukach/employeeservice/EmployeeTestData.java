package by.toukach.employeeservice;

import by.toukach.employeeservice.dao.Employee;
import by.toukach.employeeservice.dto.EmployeeDto;
import by.toukach.employeeservice.dto.InfoEmployeeDto;
import by.toukach.employeeservice.enumiration.Specialization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Builder(setterPrefix = "with")
@Accessors(fluent = true, chain = true)
@FieldNameConstants
public class EmployeeTestData {

  private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  @Builder.Default
  private long id = 1L;

  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.of(2023, 11, 9, 1, 36, 2);

  @Builder.Default
  private String name = "Олег";

  @Builder.Default
  private LocalDate dateOfBirth = LocalDate.of(1985, 2, 3);

  @Builder.Default
  private Specialization specialization = Specialization.DEVELOPER;

  @Builder.Default
  private boolean active = true;

  public static String ANOTHER_NAME = "Родион";
  public static LocalDate ANOTHER_DATE_OF_BIRTH = LocalDate.of(1992, 3, 4);

  public Employee buildEmployee() {
    return Employee.builder()
        .id(id)
        .createdAt(createdAt)
        .name(name)
        .dateOfBirth(dateOfBirth)
        .specialization(specialization)
        .active(active)
        .build();
  }

  public EmployeeDto buildEmployeeDto() {
    return EmployeeDto.builder()
        .name(name)
        .dateOfBirth(dateOfBirth)
        .specialization(specialization)
        .active(active)
        .build();
  }

  public InfoEmployeeDto buildInfoEmployeeDto() {
    return InfoEmployeeDto.builder()
        .id(id)
        .createdAt(createdAt)
        .name(name)
        .dateOfBirth(dateOfBirth)
        .specialization(specialization)
        .active(active)
        .build();
  }
}
