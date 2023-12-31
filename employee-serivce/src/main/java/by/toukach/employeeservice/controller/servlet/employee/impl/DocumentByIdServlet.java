package by.toukach.employeeservice.controller.servlet.employee.impl;

import by.toukach.employeeservice.controller.servlet.employee.DocumentServlet;
import by.toukach.employeeservice.enumiration.DocumentType;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.exception.ServletException;
import by.toukach.employeeservice.service.document.DocumentHandler;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Класс для работы с запросами по работе с документом по сотруднику по его id.
 */
@Component
@RequiredArgsConstructor
public class DocumentByIdServlet implements DocumentServlet {

  private static final String DOCUMENT_TYPE_PARAM = "documentType";

  private final DocumentHandler documentHandler;

  /**
   * Метод для обработки запроса.
   *
   * @param req HTTP запрос.
   * @param resp HTTP ответ.
   */
  @Override
  public void proceed(HttpServletRequest req, HttpServletResponse resp) {

    String id = req.getRequestURI().replaceAll(".*/", "");

    String documentTypeAsString = req.getParameter(DOCUMENT_TYPE_PARAM);

    DocumentType documentType = Arrays.stream(DocumentType.values())
        .filter(v -> v.name().equals(documentTypeAsString))
        .findFirst()
        .orElseGet(() -> DocumentType.PDF);

    ByteArrayOutputStream document = documentHandler.handle(Long.parseLong(id), documentType);

    try {
      ServletOutputStream outputStream = resp.getOutputStream();
      document.writeTo(outputStream);

    } catch (IOException e) {
      throw new ServletException(ExceptionMessage.SEND_FILE, e);
    }
  }

  /**
   * Метод для генерации предиката для проверки возможности обработки URL.
   *
   * @return возвращает предикат.
   */
  @Override
  public Predicate<String> checkUrl() {
    return url -> !url.endsWith("employees");
  }
}
