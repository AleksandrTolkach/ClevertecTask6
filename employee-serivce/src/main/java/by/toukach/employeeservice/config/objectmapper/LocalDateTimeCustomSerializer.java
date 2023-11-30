package by.toukach.employeeservice.config.objectmapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс для сериализации ObjectMapper типа localDateTime.
 */
public class LocalDateTimeCustomSerializer extends StdSerializer<LocalDateTime> {

  private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  public LocalDateTimeCustomSerializer() {
    super(LocalDateTime.class);
  }

  @Override
  public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(localDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
  }
}
