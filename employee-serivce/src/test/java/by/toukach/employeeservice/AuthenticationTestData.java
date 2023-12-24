package by.toukach.employeeservice;

import by.toukach.employeeservice.enumiration.UserRole;
import by.toukach.employeeservice.security.Authentication;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Builder(setterPrefix = "with")
@Accessors(fluent = true, chain = true)
@FieldNameConstants
public class AuthenticationTestData {

  @Builder.Default
  private String username = "name";

  @Builder.Default
  private String token = "token";

  @Builder.Default
  private UserRole authority = UserRole.USER;

  @Builder.Default
  private boolean authenticated = true;

  public Authentication buildAuthentication() {
    return Authentication.builder()
        .username(username)
        .token(token)
        .authority(authority)
        .authenticated(authenticated)
        .build();
  }
}
