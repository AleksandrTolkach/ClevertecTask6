package by.toukach.employeeservice.controller.servlet.auth;

import by.toukach.employeeservice.service.authentication.AuthService;
import by.toukach.employeeservice.service.authentication.impl.AuthServiceImpl;
import by.toukach.employeeservice.util.CookieUtil;
import by.toukach.employeeservice.util.JwtUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * Сервлет для обработки HTTP запросов для выхода из приложения.
 */
@WebServlet("/v1/auth/logout")
public class LogOutServlet extends HttpServlet {

  private static final String CONTENT_TYPE = "application/json; charset=UTF-8";

  private final AuthService authService;

  public LogOutServlet() {
    authService = AuthServiceImpl.getInstance();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

    String accessToken = CookieUtil.getAccessTokenFromCookies(req);
    String login = JwtUtil.getUsernameFromJwtToken(accessToken);
    authService.logOut(login);

    Cookie cookie = CookieUtil.getCleanAccessCookie();
    resp.addCookie(cookie);
    resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
    resp.setContentType(CONTENT_TYPE);

    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }
}
