package by.toukach.employeeservice.dto;

import by.toukach.employeeservice.enumiration.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * Класс, представляющий краткую о User при его создании/обновлении.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class UserDto {

  @NotBlank
  private String name;

  @NotBlank
  private String password;
  private UserRole role;
}
