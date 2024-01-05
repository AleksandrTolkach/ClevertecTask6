package by.toukach.employeeservice.controller.servlet.employee;

import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.exception.ServletException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Сервлет для обработки HTTP запросов по работе с документами по сотрудникам.
 */
@WebServlet("/v1/documents/employees/*")
public class DocumentFrontControllerServlet extends HttpServlet {

  private static final String CONTENT_TYPE = "application/pdf";
  private static final String DISPOSITION = "Content-Disposition";
  private static final String ATTACHMENT = "attachment; filename=employee.pdf";

  private Map<Predicate<String>, DocumentServlet> servletMap = new HashMap<>();

  @Override
  public void init(ServletConfig config) throws jakarta.servlet.ServletException {
    super.init(config);

    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
        config.getServletContext());
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

  /**
   * Метод для внедрения бинов в servletMap.
   *
   * @param documentServletList бины для внедрения.
   */
  @Autowired
  public void setDocumentServlets(List<DocumentServlet> documentServletList) {
    servletMap = documentServletList.stream()
        .collect(Collectors.toMap(DocumentServlet::checkUrl, d -> d));
  }
}
