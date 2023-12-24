package by.toukach.employeeservice.dto;

import by.toukach.employeeservice.security.Authentication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Класс представляющий DTO для ответа на LogIn запрос.
 */
@Data
@Builder
@AllArgsConstructor
public class LogInDtoResponse {

  private Authentication authentication;
  private InfoUserDto infoUserDto;
}
