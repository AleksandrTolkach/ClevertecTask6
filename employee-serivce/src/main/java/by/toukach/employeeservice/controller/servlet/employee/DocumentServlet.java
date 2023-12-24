package by.toukach.employeeservice.controller.servlet.employee;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Интерфейс для работы с запросами по работе с документами.
 */
public interface DocumentServlet {

  /**
   * Метод для обработки запроса.
   *
   * @param req HTTP запрос.
   * @param resp HTTP ответ.
   */
  void proceed(HttpServletRequest req, HttpServletResponse resp);
}
