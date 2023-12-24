package by.toukach.employeeservice.security;

import by.toukach.employeeservice.dto.InfoUserDto;

/**
 * Класс представляющий SecurityContext, в котором хранится информация о текущем пользователе.
 */
public class SecurityContext {

  private static ThreadLocal<InfoUserDto> currentUser = new ThreadLocal<>();

  public static void setCurrentUser(InfoUserDto userDto) {
    currentUser.set(userDto);
  }
}
