package by.toukach.employeeservice.dto;

import by.toukach.employeeservice.config.objectmapper.LocalDateTimeCustomSerializer;
import by.toukach.employeeservice.enumiration.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * Класс, представляющий краткую информацию о User.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@JsonIgnoreProperties({"password"})
public class InfoUserDto {

  private Long id;

  @JsonSerialize(using = LocalDateTimeCustomSerializer.class)
  private LocalDateTime createdAt;
  private String name;
  private String password;
  private UserRole role;
}
