package by.toukach.employeeservice.util;

import io.jsonwebtoken.lang.Assert;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс для выполнения операции над Cookie.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieUtil {

  private static final String ACCESS_COOKIE_NAME = "accessCookie";
  private static final int ACCESS_COOKIE_DURATION_SEC = 900;
  private static final String PATH = "/";
  private static final String REQUEST_NULL = "Запрос не может быть null";

  /**
   * Метод для создания куки с токеном.
   *
   * @param token передаваемый токен.
   * @return запрашиваемая Cookie.
   */
  public static Cookie generateAccessCookie(String token) {
    Cookie cookie = new Cookie(ACCESS_COOKIE_NAME, token);
    cookie.setPath(PATH);
    cookie.setMaxAge(ACCESS_COOKIE_DURATION_SEC);
    cookie.setHttpOnly(true);
    cookie.setSecure(false);
    return cookie;
  }

  /**
   * Метод для извлечения токена из Cookie.
   *
   * @param request запрос с Cookie.
   * @return извлеченный токен.
   */
  public static String getAccessTokenFromCookies(HttpServletRequest request) {

    Assert.notNull(request, REQUEST_NULL);
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      Cookie[] var3 = cookies;
      int var4 = cookies.length;

      for (int var5 = 0; var5 < var4; ++var5) {
        Cookie cookie = var3[var5];
        if (ACCESS_COOKIE_NAME.equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }

  /**
   * Метод для создания Cookie без токена.
   *
   * @return запрашиваемый Cookie.
   */
  public static Cookie getCleanAccessCookie() {
    Cookie cookie = new Cookie(ACCESS_COOKIE_NAME, null);
    cookie.setPath(PATH);
    cookie.setMaxAge(0);
    return cookie;
  }
}
