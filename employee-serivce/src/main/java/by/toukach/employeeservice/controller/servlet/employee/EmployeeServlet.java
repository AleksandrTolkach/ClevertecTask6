package by.toukach.employeeservice.controller.servlet.employee;

import by.toukach.employeeservice.dto.EmployeeDto;
import by.toukach.employeeservice.dto.InfoEmployeeDto;
import by.toukach.employeeservice.dto.Page;
import by.toukach.employeeservice.dto.Pageable;
import by.toukach.employeeservice.dto.Pageable.Fields;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.ValidationExceptionList;
import by.toukach.employeeservice.service.employee.EmployeeService;
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
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Сервлет для обработки HTTP запросов по работе с сотрудниками.
 */
@WebServlet("/v1/employees")
public class EmployeeServlet extends HttpServlet {

  private EmployeeService employeeService;
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

    String pageNumber = req.getParameter(Fields.pageNumber);
    String pageSize = req.getParameter(Fields.pageSize);

    Page<InfoEmployeeDto> infoEmployeeDtoPage = employeeService.getAll(Pageable.builder()
        .pageNumber(pageNumber != null ? Integer.parseInt(pageNumber) : 0)
        .pageSize(pageSize != null ? Integer.parseInt(pageSize) : 20)
        .build());

    String pageAsJson = objectMapper.writeValueAsString(infoEmployeeDtoPage);

    resp.getWriter().println(pageAsJson);

    ServletUtil.prepareHeader(resp, HttpServletResponse.SC_OK);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String requestString =
        req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

    try {
      EmployeeDto employeeDto = objectMapper.readValue(requestString, EmployeeDto.class);

      InfoEmployeeDto infoEmployeeDto = employeeService.create(employeeDto);

      String infoEmployeeDtoAsJson = objectMapper.writeValueAsString(infoEmployeeDto);

      resp.getWriter().println(infoEmployeeDtoAsJson);

    } catch (ValidationExceptionList | JsonMappingException e) {
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
      EmployeeDto employeeDto = objectMapper.readValue(requestString, EmployeeDto.class);

      InfoEmployeeDto infoEmployeeDto = employeeService.update(id, employeeDto);

      String infoEmployeeDtoAsJson = objectMapper.writeValueAsString(infoEmployeeDto);

      resp.getWriter().println(infoEmployeeDtoAsJson);

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

    employeeService.delete(id);

    ServletUtil.prepareHeader(resp, HttpServletResponse.SC_NO_CONTENT);
  }

  /**
   * Метод для внедрения бина {@link EmployeeService}.
   *
   * @param employeeService бин для внедрения.
   */
  @Autowired
  public void setEmployeeService(EmployeeService employeeService) {
    this.employeeService = employeeService;
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
