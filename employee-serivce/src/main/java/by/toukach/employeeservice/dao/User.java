package by.toukach.employeeservice.dao;

import by.toukach.employeeservice.enumiration.UserRole;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * Класс, представляющий DTO для пользователя.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class User {

  private Long id;
  private LocalDateTime createdAt;
  private String name;
  private String password;
  private UserRole role;
}
