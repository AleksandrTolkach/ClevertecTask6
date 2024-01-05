package by.toukach.employeeservice.service.authentication.impl;

import by.toukach.employeeservice.aspect.annotation.Validated;
import by.toukach.employeeservice.dto.InfoUserDto;
import by.toukach.employeeservice.dto.LogInDto;
import by.toukach.employeeservice.dto.LogInResponseDto;
import by.toukach.employeeservice.dto.SignUpDto;
import by.toukach.employeeservice.dto.UserDto;
import by.toukach.employeeservice.enumiration.UserRole;
import by.toukach.employeeservice.service.authentication.AuthService;
import by.toukach.employeeservice.service.user.UserService;
import by.toukach.employeeservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Класс для аутентификации пользователей.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserService userService;
  private final AuthenticationManager authenticationManager;

  /**
   * Метод для входа в приложение.
   *
   * @param logInDto данные пользователя.
   * @return вошедший пользователь и объект аутентификации.
   */
  @Override
  @Validated
  public LogInResponseDto logIn(LogInDto logInDto) {

    String username = logInDto.getUsername();
    InfoUserDto infoUserDto = userService.getByUsername(username);
    setAuthentication(username, logInDto.getPassword());

    return authenticateUser(infoUserDto);
  }

  /**
   * Метод для регистрации пользователя в приложении.
   *
   * @param signUpDto данные пользователя.
   * @return зарегистрированный пользователь и объект аутентификации.
   */
  @Override
  @Validated
  public LogInResponseDto signUp(SignUpDto signUpDto) {

    String login = signUpDto.getUsername();
    String password = signUpDto.getPassword();

    UserDto userDto = UserDto.builder()
        .name(login)
        .password(password)
        .role(UserRole.USER)
        .build();

    InfoUserDto infoUserDto = userService.create(userDto);

    setAuthentication(login, password);

    return authenticateUser(infoUserDto);
  }

  /**
   * Метод для регистрации выхода пользователя из приложения.
   *
   * @return вышедший пользователь и объект аутентификации.
   */
  @Override
  public LogInResponseDto logOut() {
    return LogInResponseDto.builder()
        .build();
  }

  private void setAuthentication(String login, String password) {
    Authentication authenticate = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(login, password));

    SecurityContext context = SecurityContextHolder.getContext();
    context.setAuthentication(authenticate);
  }

  private LogInResponseDto authenticateUser(InfoUserDto infoUserDto) {
    String accessToken = JwtUtil.generateTokenFromUsername(infoUserDto.getName());
    return LogInResponseDto.builder()
        .accessToken(accessToken)
        .infoUserDto(infoUserDto)
        .build();
  }
}
