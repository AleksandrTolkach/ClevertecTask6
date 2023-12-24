package by.toukach.employeeservice.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс для работы с токенами.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtil {

  private static final int TOKEN_EXPIRATION_TIME = 15;
  private static final String SECRET = "secret";
  private static final String INVALID_JWT = "Некорректная JWT сигнатура: %s\n";

  /**
   * Метод для валидации токена.
   *
   * @param token токен для валидации.
   * @return возвращает true, если токен валидны, и false если нет.
   */
  public static boolean validateJwtToken(String token) {
    try {
      Date expiration = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody()
          .getExpiration();

      return expiration.after(new Date(new Date().getTime()));
    } catch (MalformedJwtException | ExpiredJwtException
        | UnsupportedJwtException | IllegalArgumentException e) {
      System.err.printf(INVALID_JWT, e.getMessage());
    }
    return false;
  }

  /**
   * Метод для создания токена по логину пользователя.
   *
   * @param username логин пользователя.
   * @return сгенерированный токен.
   */
  public static String generateTokenFromUsername(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime()
            + TimeUnit.MINUTES.toMillis(TOKEN_EXPIRATION_TIME)))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
  }

  /**
   * Метод для извлечения логина из токена.
   *
   * @param token токен для извлечения.
   * @return извлеченный логин.
   */
  public static String getUsernameFromJwtToken(String token) {
    return token != null
        ? Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject()
        : null;
  }
}
