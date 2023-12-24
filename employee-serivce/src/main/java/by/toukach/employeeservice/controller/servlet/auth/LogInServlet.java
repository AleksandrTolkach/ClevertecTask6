package by.toukach.employeeservice.controller.servlet.auth;

import by.toukach.employeeservice.dto.InfoUserDto;
import by.toukach.employeeservice.dto.LogInDto;
import by.toukach.employeeservice.dto.LogInDtoResponse;
import by.toukach.employeeservice.exception.DbException;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.exception.JsonMapperException;
import by.toukach.employeeservice.exception.ValidationExceptionList;
import by.toukach.employeeservice.service.authentication.AuthService;
import by.toukach.employeeservice.service.authentication.impl.AuthServiceImpl;
import by.toukach.employeeservice.util.CookieUtil;
import by.toukach.employeeservice.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Сервлет для обработки HTTP запросов для входа из приложения.
 */
@WebServlet("/v1/auth/login")
public class LogInServlet extends HttpServlet {

  private static final String CONTENT_TYPE = "application/json; charset=UTF-8";

  private final AuthService authService;

  public LogInServlet() {
    authService = AuthServiceImpl.getInstance();
  }


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String requestString =
        req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

    LogInDto logInDto = null;
    try {
      logInDto = JsonUtil.mapToPojo(requestString, LogInDto.class);
    } catch (JsonMapperException e) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
          ExceptionMessage.USER_BY_USERNAME_NOT_FOUND);
      return;
    }

    LogInDtoResponse logInDtoResponse;
    try {
      logInDtoResponse = authService.logIn(logInDto);
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

    InfoUserDto infoUserDto = logInDtoResponse.getInfoUserDto();

    PrintWriter writer = resp.getWriter();
    writer.println(JsonUtil.mapToJson(infoUserDto));

    Cookie cookie = CookieUtil.generateAccessCookie(
        logInDtoResponse.getAuthentication().getToken());
    resp.addCookie(cookie);
    resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
    resp.setContentType(CONTENT_TYPE);

    resp.setStatus(HttpServletResponse.SC_OK);
  }
}
