package by.toukach.employeeservice.dto;

import by.toukach.employeeservice.config.objectmapper.LocalDateCustomDeserializer;
import by.toukach.employeeservice.enumiration.Specialization;
import by.toukach.employeeservice.util.ValidationMessage;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
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
public class EmployeeDto {

  @NotBlank
  @Pattern(regexp = ValidationMessage.NAME_REGEX,
      message = ValidationMessage.INCORRECT_NAME_FORM)
  private String name;

  @NotNull
  @Past(message = ValidationMessage.INCORRECT_DATE_OF_BIRTH_FORM)
  @JsonDeserialize(using = LocalDateCustomDeserializer.class)
  private LocalDate dateOfBirth;

  @NotNull
  private Specialization specialization;

  @NotNull
  private Boolean active;
}
