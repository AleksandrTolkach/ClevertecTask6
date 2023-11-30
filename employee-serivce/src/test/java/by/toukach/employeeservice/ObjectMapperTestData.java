package by.toukach.employeeservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperTestData {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.findAndRegisterModules();
  }

  public static ObjectMapper getMapper() {
    return objectMapper;
  }
}
