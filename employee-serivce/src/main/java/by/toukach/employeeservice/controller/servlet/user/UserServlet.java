package by.toukach.employeeservice.controller.servlet.user;

import by.toukach.employeeservice.dto.InfoUserDto;
import by.toukach.employeeservice.dto.UserDto;
import by.toukach.employeeservice.exception.EntityDuplicateException;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.ValidationExceptionList;
import by.toukach.employeeservice.service.user.UserService;
import by.toukach.employeeservice.util.ServletUtil;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Сервлет для обработки HTTP запросов по работе с пользователями.
 */
@WebServlet("/v1/users")
public class UserServlet extends HttpServlet {

  private UserService userService;
  private ObjectMapper objectMapper;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
        config.getServletContext());
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    List<InfoUserDto> infoUserDtoList = userService.getAll();

    String infoUserDtoListAsJson = objectMapper.writeValueAsString(infoUserDtoList);

    resp.getWriter().println(infoUserDtoListAsJson);

    ServletUtil.prepareHeader(resp, HttpServletResponse.SC_OK);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String requestString =
        req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

    try {
      UserDto userDto = objectMapper.readValue(requestString, UserDto.class);

      InfoUserDto infoUserDto = userService.create(userDto);

      String infoUserDtoAsJson = objectMapper.writeValueAsString(infoUserDto);

      resp.getWriter().println(infoUserDtoAsJson);

    } catch (ValidationExceptionList | JsonMappingException e) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
      return;
    } catch (EntityDuplicateException e) {
      resp.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
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
      UserDto userDto = objectMapper.readValue(requestString, UserDto.class);

      InfoUserDto infoUserDto = userService.update(id, userDto);

      String infoUserDtoAsString = objectMapper.writeValueAsString(infoUserDto);

      resp.getWriter().println(infoUserDtoAsString);

    } catch (ValidationExceptionList | JsonMappingException e) {
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

  /**
   * Метод для внедрения бина {@link UserService}.
   *
   * @param userService бин для внедрения.
   */
  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
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
