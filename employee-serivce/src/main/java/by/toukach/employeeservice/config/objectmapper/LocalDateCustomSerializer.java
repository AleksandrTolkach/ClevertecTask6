package by.toukach.employeeservice.config.objectmapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Класс для сериализации ObjectMapper типа localDateTime.
 */
public class LocalDateCustomSerializer extends StdSerializer<LocalDate> {

  private static final String DATE_FORMAT = "yyyy-MM-dd";

  public LocalDateCustomSerializer() {
    super(LocalDate.class);
  }

  @Override
  public void serialize(LocalDate localDate, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(localDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
  }
}
