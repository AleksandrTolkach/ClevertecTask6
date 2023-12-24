package by.toukach.employeeservice.security;

/**
 * Интерфейс для выполнения аутентификации пользователя.
 */
public interface AuthenticationManager {

  /**
   * Метод для возврата объекта аутентификации пользователя.
   *
   * @param username username аутентифицированного пользователя.
   * @return запрашиваемая аутентификация.
   */
  Authentication getAuthentication(String username);

  /**
   * Метод для аутентификации пользователя в приложении.
   *
   * @param login login пользователя.
   * @param password пароль пользователя.
   * @return объект аутентификации.
   */
  Authentication authenticate(String login, String password);

  /**
   * Удалить записи об аутентификации пользователя.
   *
   * @param username логин пользователя.
   */
  void clearAuthentication(String username);
}
