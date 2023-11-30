package by.toukach.employeeservice.util;

import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.exception.JsonMapperException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс для работы с JSON.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.findAndRegisterModules();
  }

  /**
   * Метод для преобразования Json в объект.
   *
   * @param json json для преобразования.
   * @param metaData данные о классе.
   * @param <T> тип, в который необходимо преобразовать.
   * @return преобразованный объект.
   */
  public static <T> T mapToPojo(String json, Class<T> metaData) {
    try {
      return objectMapper.readValue(json, metaData);
    } catch (JsonProcessingException e) {
      throw new JsonMapperException(ExceptionMessage.JSON_POJO_MAP, e);
    }
  }

  /**
   * Метод для преобразования объекта в Json.
   *
   * @param item объект для преобразования.
   * @param <I> тип объекта.
   * @return преобразованный Json.
   */
  public static <I> String mapToJson(I item) {
    try {
      return objectMapper.writeValueAsString(item);
    } catch (JsonProcessingException e) {
      throw new JsonMapperException(ExceptionMessage.POJO_JSON_MAP, e);
    }
  }
}
