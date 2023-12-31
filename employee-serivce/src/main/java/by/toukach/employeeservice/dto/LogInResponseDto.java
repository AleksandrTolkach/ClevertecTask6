package by.toukach.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * Класс представляющий DTO для ответа на LogIn запрос.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class LogInResponseDto {

  private String accessToken;
  private InfoUserDto infoUserDto;
}
