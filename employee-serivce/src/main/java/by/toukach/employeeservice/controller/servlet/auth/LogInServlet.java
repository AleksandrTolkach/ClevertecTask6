package by.toukach.employeeservice.controller.servlet.auth;

import by.toukach.employeeservice.dto.InfoUserDto;
import by.toukach.employeeservice.dto.LogInDto;
import by.toukach.employeeservice.dto.LogInResponseDto;
import by.toukach.employeeservice.exception.DbException;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.exception.ValidationExceptionList;
import by.toukach.employeeservice.service.authentication.AuthService;
import by.toukach.employeeservice.util.CookieUtil;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Сервлет для обработки HTTP запросов для входа из приложения.
 */
@WebServlet("/v1/auth/login")
public class LogInServlet extends HttpServlet {

  private static final String CONTENT_TYPE = "application/json; charset=UTF-8";

  private AuthService authService;
  private ObjectMapper objectMapper;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
        config.getServletContext());
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String requestString =
        req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

    LogInDto logInDto = null;
    try {
      logInDto = objectMapper.readValue(requestString, LogInDto.class);
    } catch (JsonMappingException e) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
          ExceptionMessage.USER_BY_USERNAME_NOT_FOUND);
      return;
    }

    LogInResponseDto logInResponseDto;
    try {
      logInResponseDto = authService.logIn(logInDto);
    } catch (ValidationExceptionList e) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
      return;
    } catch (EntityNotFoundException e) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
      return;
    } catch (DbException e) {
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
      return;
    }

    InfoUserDto infoUserDto = logInResponseDto.getInfoUserDto();

    PrintWriter writer = resp.getWriter();
    writer.println(objectMapper.writeValueAsString(infoUserDto));

    Cookie cookie = CookieUtil.generateAccessCookie(
        logInResponseDto.getAccessToken());
    resp.addCookie(cookie);
    resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
    resp.setContentType(CONTENT_TYPE);

    resp.setStatus(HttpServletResponse.SC_OK);
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

  /**
   * Метод для внедрения бина {@link ObjectMapper}.
   *
   * @param objectMapper бин для внедрения.
   */
  @Autowired
  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
}
