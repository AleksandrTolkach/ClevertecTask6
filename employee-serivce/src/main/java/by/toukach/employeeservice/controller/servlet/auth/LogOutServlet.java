package by.toukach.employeeservice.controller.servlet.auth;

import by.toukach.employeeservice.service.authentication.AuthService;
import by.toukach.employeeservice.util.CookieUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Сервлет для обработки HTTP запросов для выхода из приложения.
 */
@WebServlet("/v1/auth/logout")
public class LogOutServlet extends HttpServlet {

  private static final String CONTENT_TYPE = "application/json; charset=UTF-8";

  private AuthService authService;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
        config.getServletContext());
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

    authService.logOut();

    Cookie cookie = CookieUtil.getCleanAccessCookie();
    resp.addCookie(cookie);
    resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
    resp.setContentType(CONTENT_TYPE);

    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }

  /**
   * Метод для внедрения бина {@link AuthService}.
   *
   * @param authService бин для внедрения.
   */
  @Autowired
  public void setAuthService(AuthService authService) {
    this.authService = authService;
  }
}
