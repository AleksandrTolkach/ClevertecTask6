package by.toukach.employeeservice.controller.servlet.employee.impl;

import by.toukach.employeeservice.controller.servlet.employee.DocumentServlet;
import by.toukach.employeeservice.dto.Pageable;
import by.toukach.employeeservice.dto.Pageable.Fields;
import by.toukach.employeeservice.enumiration.DocumentType;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.exception.ServletException;
import by.toukach.employeeservice.service.document.DocumentHandler;
import by.toukach.employeeservice.service.document.impl.EmployeeDocumentHandler;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Класс для работы с запросами по работе с документом по всем сотрудникам.
 */
public class DocumentListServlet implements DocumentServlet {

  private static final String DOCUMENT_TYPE_PARAM = "documentType";

  private final DocumentHandler documentHandler;

  public DocumentListServlet() {
    this.documentHandler = new EmployeeDocumentHandler();
  }

  /**
   * Метод для обработки запроса.
   *
   * @param req HTTP запрос.
   * @param resp HTTP ответ.
   */
  @Override
  public void proceed(HttpServletRequest req, HttpServletResponse resp) {

    String pageNumber = req.getParameter(Fields.pageNumber);
    String pageSize = req.getParameter(Fields.pageSize);
    String documentTypeAsString = req.getParameter(DOCUMENT_TYPE_PARAM);

    Pageable pageable = Pageable.builder()
        .pageNumber(pageNumber != null ? Integer.parseInt(pageNumber) : 0)
        .pageSize(pageSize != null ? Integer.parseInt(pageSize) : 20)
        .build();

    DocumentType documentType = Arrays.stream(DocumentType.values())
        .filter(v -> v.name().equals(documentTypeAsString))
        .findFirst()
        .orElseGet(() -> DocumentType.PDF);

    ByteArrayOutputStream document = documentHandler.handle(pageable, documentType);

    try {
      ServletOutputStream outputStream = resp.getOutputStream();
      document.writeTo(outputStream);

    } catch (IOException e) {
      throw new ServletException(ExceptionMessage.SEND_FILE, e);
    }
  }
}
