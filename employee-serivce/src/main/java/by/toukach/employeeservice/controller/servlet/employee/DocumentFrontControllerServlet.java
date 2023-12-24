package by.toukach.employeeservice.controller.servlet.employee;

import by.toukach.employeeservice.controller.servlet.employee.impl.DocumentByIdServlet;
import by.toukach.employeeservice.controller.servlet.employee.impl.DocumentListServlet;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.exception.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Сервлет для обработки HTTP запросов по работе с документами по сотрудникам.
 */
@WebServlet("/v1/documents/employees/*")
public class DocumentFrontControllerServlet extends HttpServlet {

  private static final String CONTENT_TYPE = "application/pdf";
  private static final String DISPOSITION = "Content-Disposition";
  private static final String ATTACHMENT = "attachment; filename=employee.pdf";

  private final Map<Predicate<String>, DocumentServlet> servletMap = new HashMap<>();

  public DocumentFrontControllerServlet() {
    servletMap.put(k -> !k.endsWith("employees"), new DocumentByIdServlet());
    servletMap.put(k -> k.endsWith("employees"), new DocumentListServlet());
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    String uri = req.getRequestURI();

    servletMap.entrySet().stream()
        .filter(e -> e.getKey().test(uri))
        .findFirst()
        .ifPresentOrElse(entry -> {
          try {
            entry.getValue().proceed(req, resp);

          } catch (EntityNotFoundException e) {
            try {
              resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());

            } catch (IOException ex) {
              throw new ServletException(ExceptionMessage.SEND_ERROR, e);
            }
          }

          resp.setContentType(CONTENT_TYPE);
          resp.setHeader(DISPOSITION, ATTACHMENT);
          resp.setStatus(HttpServletResponse.SC_OK);

        }, () -> {
          try {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);

          } catch (IOException e) {
            throw new ServletException(ExceptionMessage.SEND_ERROR, e);
          }
        });
  }
}
