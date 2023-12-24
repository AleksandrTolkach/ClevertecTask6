package by.toukach.employeeservice.service.authentication.impl;

import by.toukach.employeeservice.aspect.annotation.Validated;
import by.toukach.employeeservice.dto.InfoUserDto;
import by.toukach.employeeservice.dto.LogInDto;
import by.toukach.employeeservice.dto.LogInDtoResponse;
import by.toukach.employeeservice.dto.SignUpDto;
import by.toukach.employeeservice.dto.UserDto;
import by.toukach.employeeservice.enumiration.UserRole;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.security.Authentication;
import by.toukach.employeeservice.security.AuthenticationManager;
import by.toukach.employeeservice.security.impl.AuthenticationManagerImpl;
import by.toukach.employeeservice.service.authentication.AuthService;
import by.toukach.employeeservice.service.user.UserService;
import by.toukach.employeeservice.service.user.impl.UserServiceImpl;

/**
 * Класс для аутентификации пользователей.
 */
public class AuthServiceImpl implements AuthService {

  private static final AuthService instance = new AuthServiceImpl();

  private final UserService userService;
  private final AuthenticationManager authenticationManager;

  private AuthServiceImpl() {
    userService = UserServiceImpl.getInstance();
    authenticationManager = AuthenticationManagerImpl.getInstance();
  }

  /**
   * Метод для входа в приложение.
   *
   * @param logInDto данные пользователя.
   * @return вошедший пользователь и объект аутентификации.
   */
  @Override
  @Validated
  public LogInDtoResponse logIn(LogInDto logInDto) {

    try {
      InfoUserDto infoUserDto = userService.getByUsername(logInDto.getUsername());

      Authentication authentication = authenticationManager.authenticate(logInDto.getUsername(),
          logInDto.getPassword());

      if (!authentication.isAuthenticated()) {
        throw new EntityNotFoundException();
      } else {

        return LogInDtoResponse.builder()
            .authentication(authentication)
            .infoUserDto(infoUserDto)
            .build();
      }
    } catch (EntityNotFoundException e) {
      throw new EntityNotFoundException(ExceptionMessage.USER_BY_USERNAME_NOT_FOUND);
    }
  }

  /**
   * Метод для регистрации пользователя в приложении.
   *
   * @param signUpDto данные пользователя.
   * @return зарегистрированный пользователь и объект аутентификации.
   */
  @Override
  @Validated
  public LogInDtoResponse signUp(SignUpDto signUpDto) {

    String username = signUpDto.getUsername();
    String password = signUpDto.getPassword();

    UserDto userDto = UserDto.builder()
        .name(username)
        .password(password)
        .role(UserRole.USER)
        .build();

    InfoUserDto infoUserDto = userService.create(userDto);

    Authentication authentication = authenticationManager.authenticate(username, password);

    return LogInDtoResponse.builder()
        .authentication(authentication)
        .infoUserDto(infoUserDto)
        .build();
  }

  /**
   * Метор для регистрации выхода пользователя из приложения.
   *
   * @param username username пользователя.
   * @return вышедший пользователь и объект аутентификации.
   */
  @Override
  public LogInDtoResponse logOut(String username) {
    InfoUserDto infoUserDto = userService.getByUsername(username);
    authenticationManager.clearAuthentication(username);
    return LogInDtoResponse.builder()
        .infoUserDto(infoUserDto)
        .build();
  }

  public static AuthService getInstance() {
    return instance;
  }


}
