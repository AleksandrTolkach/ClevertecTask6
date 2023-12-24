package by.toukach.employeeservice.service.authentication;

import by.toukach.employeeservice.dto.LogInDto;
import by.toukach.employeeservice.dto.LogInDtoResponse;
import by.toukach.employeeservice.dto.SignUpDto;

/**
 * Интерфейс для аутентификации пользователей.
 * */
public interface AuthService {

  /**
   * Метод для входа в приложение.
   *
   * @param logInDto данные пользователя.
   * @return вошедший пользователь и объект аутентификации.
   */
  LogInDtoResponse logIn(LogInDto logInDto);

  /**
   * Метод для регистрации пользователя в приложении.
   *
   * @param signUpDto данные пользователя.
   * @return зарегистрированный пользователь и объект аутентификации.
   */
  LogInDtoResponse signUp(SignUpDto signUpDto);


  /**
   * Метор для регистрации выхода польователя из приложения.
   *
   * @param username username пользователя.
   * @return вышедший пользователь и объект аутентификации.
   */
  LogInDtoResponse logOut(String username);
}
