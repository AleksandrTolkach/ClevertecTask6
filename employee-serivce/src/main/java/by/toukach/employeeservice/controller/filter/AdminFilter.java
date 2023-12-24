package by.toukach.employeeservice.controller.filter;

import by.toukach.employeeservice.enumiration.UserRole;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.security.Authentication;
import by.toukach.employeeservice.security.AuthenticationManager;
import by.toukach.employeeservice.security.impl.AuthenticationManagerImpl;
import by.toukach.employeeservice.util.CookieUtil;
import by.toukach.employeeservice.util.JwtUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Класс для ограничения доступа к странице с пользователями.
 */
@WebFilter(filterName = "filter2")
public class AdminFilter implements Filter {

  private final AuthenticationManager authenticationManager;

  public AdminFilter() {
    authenticationManager = AuthenticationManagerImpl.getInstance();
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) servletRequest;
    HttpServletResponse resp = (HttpServletResponse) servletResponse;

    String accessToken = CookieUtil.getAccessTokenFromCookies(req);

    String login = JwtUtil.getUsernameFromJwtToken(accessToken);

    Authentication authentication = authenticationManager.getAuthentication(login);
    UserRole authority = authentication.getAuthority();

    if (authority != null && authority.equals(UserRole.ADMIN)) {
      filterChain.doFilter(servletRequest, servletResponse);
    } else {
      resp.sendError(HttpServletResponse.SC_FORBIDDEN, ExceptionMessage.ACCESS_DENIED);
    }
  }
}
