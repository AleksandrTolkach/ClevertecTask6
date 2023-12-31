package by.toukach.employeeservice.security;

import by.toukach.employeeservice.dao.User;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Класс, реализующий UsedDetails,
 * который служит для предоставления данных о пользователе в SecurityContext.
 */
@Getter
public class UserDetailsImpl implements UserDetails {

  private static final String ROLE_PREFIX = "ROLE_";

  private final Long id;
  private final String username;
  private final String password;
  private final GrantedAuthority grantedAuthority;

  /**
   * Конструктор для создания UserDetailsImpl.
   *
   * @param user пользователь с данными.
   */
  public UserDetailsImpl(User user) {
    id = user.getId();
    username = user.getName();
    password = user.getPassword();
    grantedAuthority = new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().name());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(grantedAuthority);
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
