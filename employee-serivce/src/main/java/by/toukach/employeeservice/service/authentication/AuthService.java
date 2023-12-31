package by.toukach.employeeservice.service.authentication;

import by.toukach.employeeservice.dto.LogInDto;
import by.toukach.employeeservice.dto.LogInResponseDto;
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
  LogInResponseDto logIn(LogInDto logInDto);

  /**
   * Метод для регистрации пользователя в приложении.
   *
   * @param signUpDto данные пользователя.
   * @return зарегистрированный пользователь и объект аутентификации.
   */
  LogInResponseDto signUp(SignUpDto signUpDto);


  /**
   * Метод для регистрации выхода пользователя из приложения.
   *
   * @return вышедший пользователь и объект аутентификации.
   */
  LogInResponseDto logOut();
}
