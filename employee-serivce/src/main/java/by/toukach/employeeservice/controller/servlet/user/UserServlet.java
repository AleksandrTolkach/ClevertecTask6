package by.toukach.employeeservice.controller.servlet.user;

import by.toukach.employeeservice.dto.InfoUserDto;
import by.toukach.employeeservice.dto.UserDto;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.JsonMapperException;
import by.toukach.employeeservice.exception.ValidationExceptionList;
import by.toukach.employeeservice.service.user.UserService;
import by.toukach.employeeservice.service.user.impl.UserServiceImpl;
import by.toukach.employeeservice.util.JsonUtil;
import by.toukach.employeeservice.util.ServletUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Сервлет для обработки HTTP запросов по работе с пользователями.
 */
@WebServlet("/v1/users")
public class UserServlet extends HttpServlet {

  private final UserService userService;

  public UserServlet() {
    userService = UserServiceImpl.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    List<InfoUserDto> infoUserDtoList = userService.getAll();

    String infoUserDtoListAsJson = JsonUtil.mapToJson(infoUserDtoList);

    resp.getWriter().println(infoUserDtoListAsJson);

    ServletUtil.prepareHeader(resp, HttpServletResponse.SC_OK);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String requestString =
        req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

    try {
      UserDto userDto = JsonUtil.mapToPojo(requestString, UserDto.class);

      InfoUserDto infoUserDto = userService.create(userDto);

      String infoUserDtoAsJson = JsonUtil.mapToJson(infoUserDto);

      resp.getWriter().println(infoUserDtoAsJson);

    } catch (ValidationExceptionList | JsonMapperException e) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
      return;
    }

    ServletUtil.prepareHeader(resp, HttpServletResponse.SC_CREATED);
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    Long id = ServletUtil.getIdFromRequest(req, resp);

    if (Objects.isNull(id)) {
      return;
    }

    String requestString =
        req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

    try {
      UserDto userDto = JsonUtil.mapToPojo(requestString, UserDto.class);

      InfoUserDto infoUserDto = userService.update(id, userDto);

      String infoUserDtoAsString = JsonUtil.mapToJson(infoUserDto);

      resp.getWriter().println(infoUserDtoAsString);

    } catch (ValidationExceptionList | JsonMapperException e) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
      return;
    } catch (EntityNotFoundException e) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
      return;
    }

    ServletUtil.prepareHeader(resp, HttpServletResponse.SC_OK);
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    Long id = ServletUtil.getIdFromRequest(req, resp);

    if (Objects.isNull(id)) {
      return;
    }

    userService.delete(id);

    ServletUtil.prepareHeader(resp, HttpServletResponse.SC_NO_CONTENT);
  }
}
