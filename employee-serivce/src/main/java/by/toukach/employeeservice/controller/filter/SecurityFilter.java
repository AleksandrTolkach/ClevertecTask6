package by.toukach.employeeservice.controller.filter;

import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.security.Authentication;
import by.toukach.employeeservice.security.AuthenticationManager;
import by.toukach.employeeservice.security.impl.AuthenticationManagerImpl;
import by.toukach.employeeservice.util.CookieUtil;
import by.toukach.employeeservice.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

/**
 * Класс для ограничения доступа к приложению не авторизованным пользователям.
 */
@WebFilter(filterName = "filter1")
public class SecurityFilter implements Filter {

  private final AuthenticationManager authenticationManager;

  public SecurityFilter() {
    authenticationManager = AuthenticationManagerImpl.getInstance();
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) servletRequest;
    HttpServletResponse resp = (HttpServletResponse) servletResponse;

    String accessToken = CookieUtil.getAccessTokenFromCookies(req);

    String login = null;
    try {
      login = JwtUtil.getUsernameFromJwtToken(accessToken);
    } catch (ExpiredJwtException e) {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ExceptionMessage.SESSION_EXPIRED);
      return;
    }

    if (!StringUtils.isBlank(login)) {
      Authentication authentication = authenticationManager.getAuthentication(login);
      if (authentication.isAuthenticated()) {
        filterChain.doFilter(servletRequest, servletResponse);
      } else {
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ExceptionMessage.LOGIN_OFFER);
      }
    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ExceptionMessage.LOGIN_OFFER);
    }
  }
}
