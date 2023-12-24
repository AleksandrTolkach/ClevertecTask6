package by.toukach.employeeservice.security.impl;

import by.toukach.employeeservice.dto.InfoUserDto;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.security.Authentication;
import by.toukach.employeeservice.security.AuthenticationManager;
import by.toukach.employeeservice.security.SecurityContext;
import by.toukach.employeeservice.service.user.UserService;
import by.toukach.employeeservice.service.user.impl.UserServiceImpl;
import by.toukach.employeeservice.util.JwtUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для выполнения аутентификации пользователей.
 */
public class AuthenticationManagerImpl implements AuthenticationManager {

  private static final AuthenticationManager instance = new AuthenticationManagerImpl();
  private static final String GUEST = "guest";

  private final UserService useService;
  private Map<String, Authentication> authenticationMap = new HashMap<>();

  private AuthenticationManagerImpl() {
    useService = UserServiceImpl.getInstance();
  }

  /**
   * Метод для возврата объекта аутентификации пользователя.
   *
   * @param username username аутентифицированного пользователя.
   * @return запрашиваемая аутентификация.
   */
  @Override
  public Authentication getAuthentication(String username) {
    Authentication authentication = authenticationMap.get(username);
    if (authentication != null) {
      boolean isAuthenticated = JwtUtil.validateJwtToken(authentication.getToken());
      authentication.setAuthenticated(isAuthenticated);
    } else {
      authentication = Authentication.builder()
          .username(username)
          .authenticated(false)
          .build();
    }

    InfoUserDto infoUserDto = useService.getByUsername(username);
    SecurityContext.setCurrentUser(infoUserDto);
    return authentication;
  }

  /**
   * Метод для аутентификации пользователя в приложении.
   *
   * @param login login пользователя.
   * @param password пароль пользователя.
   * @return объект аутентификации.
   */
  @Override
  public Authentication authenticate(String login, String password) {

    Authentication authentication = Authentication.builder()
        .username(GUEST)
        .authenticated(false)
        .build();

    if (login == null || password == null) {
      return authentication;
    }

    InfoUserDto infoUserDto;

    try {
      infoUserDto = useService.getByUsername(login);
    } catch (EntityNotFoundException e) {
      return authentication;
    }

    if (infoUserDto.getPassword().equals(password)) {
      String jwtToken = JwtUtil.generateTokenFromUsername(login);

      authentication.setUsername(login);
      authentication.setToken(jwtToken);
      authentication.setAuthority(infoUserDto.getRole());
      authentication.setAuthenticated(true);

      authenticationMap.put(login, authentication);

      SecurityContext.setCurrentUser(infoUserDto);

    }

    return authentication;
  }

  /**
   * Удалить записи об аутентификации пользователя.
   *
   * @param username логин пользователя.
   */
  @Override
  public void clearAuthentication(String username) {
    authenticationMap.remove(username);
  }

  public static AuthenticationManager getInstance() {
    return instance;
  }
}
