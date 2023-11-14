package by.toukach.employeeservice.util;

import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.exception.JsonMapperException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс для работы с XML.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XmlUtil {

  private static final XmlMapper xmlMapper = new XmlMapper();

  /**
   * Метод для преобразования XML в POJO.
   *
   * @param xml XML для преобразования.
   * @param metaData данные о классе.
   * @param <T> тип в который необходимо преобразовать.
   * @return преобразованный объект.
   */
  public static <T> T mapToPojo(String xml, Class<T> metaData) {
    try {
      return xmlMapper.readValue(xml, metaData);
    } catch (JsonProcessingException e) {
      throw new JsonMapperException(ExceptionMessage.XML_POJO_MAP, e);
    }
  }

  /**
   * Метод для преобразования POJO в XML.
   *
   * @param item объект для преобразования.
   * @param <I> тип объекта.
   * @return преобразованный XML.
   */
  public static <I> String mapToXml(I item) {
    try {
      return xmlMapper.writeValueAsString(item);
    } catch (JsonProcessingException e) {
      throw new JsonMapperException(ExceptionMessage.POJO_XML_MAP, e);
    }
  }
}
