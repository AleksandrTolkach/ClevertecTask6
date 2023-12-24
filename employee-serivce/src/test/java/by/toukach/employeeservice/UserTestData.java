package by.toukach.employeeservice;

import by.toukach.employeeservice.dao.User;
import by.toukach.employeeservice.dto.InfoUserDto;
import by.toukach.employeeservice.dto.UserDto;
import by.toukach.employeeservice.enumiration.UserRole;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Builder(setterPrefix = "with")
@Accessors(fluent = true, chain = true)
@FieldNameConstants
public class UserTestData {

  @Builder.Default
  private Long id = 1L;

  @Builder.Default
  private LocalDateTime createdAt =
      LocalDateTime.of(1970, 10, 10, 10, 10, 10);

  @Builder.Default
  private String name = "name";

  @Builder.Default
  private String password = "password";

  @Builder.Default
  private UserRole role = UserRole.USER;

  public User buildUser() {
    return User.builder()
        .id(id)
        .createdAt(createdAt)
        .name(name)
        .password(password)
        .role(role)
        .build();
  }

  public UserDto buildUserDto() {
    return UserDto.builder()
        .name(name)
        .password(password)
        .role(role)
        .build();
  }

  public InfoUserDto buildInfoUserDto() {
    return InfoUserDto.builder()
        .id(id)
        .createdAt(createdAt)
        .name(name)
        .password(password)
        .role(role)
        .build();
  }
}
