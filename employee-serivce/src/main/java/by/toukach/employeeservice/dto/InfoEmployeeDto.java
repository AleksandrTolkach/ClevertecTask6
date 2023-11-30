package by.toukach.employeeservice.dto;

import by.toukach.employeeservice.config.objectmapper.LocalDateCustomSerializer;
import by.toukach.employeeservice.config.objectmapper.LocalDateTimeCustomSerializer;
import by.toukach.employeeservice.enumiration.Specialization;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * Класс, представляющий краткую информацию о Employee.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class InfoEmployeeDto {

  private Long id;

  @JsonSerialize(using = LocalDateTimeCustomSerializer.class)
  private LocalDateTime createdAt;

  private String name;

  @JsonSerialize(using = LocalDateCustomSerializer.class)
  private LocalDate dateOfBirth;

  private Specialization specialization;
  private Boolean active;
}
