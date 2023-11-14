package by.toukach.employeeservice.dao;

import by.toukach.employeeservice.enumiration.Specialization;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * Класс, представляющий DTO для сотрудника.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Employee {

  private Long id;
  private LocalDateTime createdAt;
  private String name;
  private LocalDate dateOfBirth;
  private Specialization specialization;
  private Boolean active;
}
