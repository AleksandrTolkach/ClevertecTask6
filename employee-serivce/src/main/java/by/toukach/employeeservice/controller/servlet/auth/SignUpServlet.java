package by.toukach.employeeservice.controller.servlet.auth;

import by.toukach.employeeservice.dto.InfoUserDto;
import by.toukach.employeeservice.dto.LogInResponseDto;
import by.toukach.employeeservice.dto.SignUpDto;
import by.toukach.employeeservice.exception.DbException;
import by.toukach.employeeservice.exception.EntityDuplicateException;
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
 * Сервлет для обработки HTTP запросов регистрации в приложении.
 */
@WebServlet("/v1/auth/sign-up")
public class SignUpServlet extends HttpServlet {

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

    SignUpDto signUpDto = null;
    try {
      signUpDto = objectMapper.readValue(requestString, SignUpDto.class);
    } catch (JsonMappingException e) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, ExceptionMessage.WRONG_SIGN_UP);
      return;
    }

    LogInResponseDto logInResponseDto;
    try {
      logInResponseDto = authService.signUp(signUpDto);
    } catch (ValidationExceptionList e) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
      return;
    } catch (EntityDuplicateException e) {
      resp.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
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

    resp.setStatus(HttpServletResponse.SC_CREATED);
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
