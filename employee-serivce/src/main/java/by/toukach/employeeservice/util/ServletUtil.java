package by.toukach.employeeservice.util;

import by.toukach.employeeservice.dao.Employee;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс для сервлетов.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServletUtil {

  private static final String CONTENT_TYPE = "application/json";

  /**
   * Метод возвращает id из запроса и проверяет его.
   *
   * @param req HTTP запрос.
   * @param resp HTTP ответ.
   * @return полученный id.
   * @throws IOException выбрасывается при некорректной работе метода по отправке ошибок.
   */
  public static Long getIdFromRequest(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String idParam = req.getParameter(Employee.Fields.id);
    Long id = Objects.nonNull(idParam) && !idParam.isBlank() ? Long.parseLong(idParam) : null;

    if (Objects.isNull(id)) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
          ValidationMessage.NULL_PARAM.formatted(Employee.Fields.id));
    }

    return id;
  }

  /**
   * Подготавливает заголовок для ответа.
   *
   * @param resp HTTP ответ.
   * @param statusCode код ответа.
   */
  public static void prepareHeader(HttpServletResponse resp, int statusCode) {
    resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
    resp.setContentType(CONTENT_TYPE);
    resp.setStatus(statusCode);
  }
}