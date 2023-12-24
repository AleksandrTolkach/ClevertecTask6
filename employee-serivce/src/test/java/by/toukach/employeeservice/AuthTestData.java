package by.toukach.employeeservice;

import by.toukach.employeeservice.dto.LogInDto;
import by.toukach.employeeservice.dto.SignUpDto;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Builder(setterPrefix = "with")
@Accessors(fluent = true, chain = true)
@FieldNameConstants
public class AuthTestData {

  @Builder.Default
  private String username = "name";

  @Builder.Default
  private String password = "password";

  public LogInDto buildLoginDto() {
    return LogInDto.builder()
        .username(username)
        .password(password)
        .build();
  }

  public SignUpDto buildSignUpDto() {
    return SignUpDto.builder()
        .username(username)
        .password(password)
        .build();
  }
}
