package by.toukach.employeeservice.controller.filter;

import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.util.CookieUtil;
import by.toukach.employeeservice.util.JwtUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Класс для ограничения доступа к приложению не авторизованным пользователям.
 */
@WebFilter(filterName = "filter1")
public class SecurityFilter implements Filter {

  private UserDetailsService userDetailsService;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    Filter.super.init(filterConfig);

    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
        filterConfig.getServletContext());
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) servletRequest;
    HttpServletResponse resp = (HttpServletResponse) servletResponse;

    String accessToken = CookieUtil.getAccessTokenFromCookies(req);

    if (accessToken != null && JwtUtil.validateJwtToken(accessToken)) {
      String username = JwtUtil.getUsernameFromJwtToken(accessToken);
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userDetails, null,
          userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);

      filterChain.doFilter(req, resp);

    } else {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ExceptionMessage.LOGIN_OFFER);
    }
  }

  /**
   * Метод для внедрения бина {@link UserDetailsService}.
   *
   * @param userDetailsService бин для внедрения.
   */
  @Autowired
  public void setUserDetailsService(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }
}
