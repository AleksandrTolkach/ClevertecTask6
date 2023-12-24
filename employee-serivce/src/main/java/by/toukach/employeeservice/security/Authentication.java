package by.toukach.employeeservice.security;

import by.toukach.employeeservice.enumiration.UserRole;
import lombok.Builder;
import lombok.Data;

/**
 * Класс представляющий объект аутентификации пользователя.
 */
@Data
@Builder
public class Authentication {

  private String username;
  private String token;
  private UserRole authority;
  private boolean authenticated;
}
