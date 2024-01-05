package by.toukach.employeeservice.controller.filter;

import by.toukach.employeeservice.enumiration.UserRole;
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
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Класс для ограничения доступа к странице с пользователями.
 */
@WebFilter(filterName = "filter2")
public class AdminFilter implements Filter {

  private static final String ROLE_PREFIX = "ROLE_%s";

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

    String username = JwtUtil.getUsernameFromJwtToken(accessToken);

    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
    String userRole = authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .findFirst()
        .orElse(ROLE_PREFIX.formatted(UserRole.USER.name()));

    if (userRole.equals(ROLE_PREFIX.formatted(UserRole.ADMIN.name()))) {
      filterChain.doFilter(servletRequest, servletResponse);
    } else {
      resp.sendError(HttpServletResponse.SC_FORBIDDEN, ExceptionMessage.ACCESS_DENIED);
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
