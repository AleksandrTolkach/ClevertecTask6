package by.toukach.employeeservice.config;

import by.toukach.employeeservice.config.objectmapper.LocalDateCustomDeserializer;
import by.toukach.employeeservice.config.objectmapper.LocalDateCustomSerializer;
import by.toukach.employeeservice.config.objectmapper.LocalDateTimeCustomSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Конфигурационный класс для приложения.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final ApplicationProperty applicationProperty;

  /**
   * Метод для создания бина {@link JdbcTemplate}.
   *
   * @param dataSource {@link DriverManagerDataSource}.
   * @return запрашиваемый {@link JdbcTemplate}.
   */
  @Bean
  public JdbcTemplate jdbcTemplate(DriverManagerDataSource dataSource) {
    String driver = applicationProperty.getDataBaseDriver();
    String url = applicationProperty.getDataBaseUrl();
    String username = applicationProperty.getDataBaseUsername();
    String password = applicationProperty.getDataBasePassword();

    dataSource.setDriverClassName(driver);
    dataSource.setUrl(String.format(url, username));
    dataSource.setUsername(username);
    dataSource.setPassword(password);

    return new JdbcTemplate(dataSource);
  }

  /**
   * Метод для создания бина {@link DriverManagerDataSource}.
   *
   * @return запрашиваемый {@link DriverManagerDataSource}.
   */
  @Bean
  public DriverManagerDataSource dataSource() {
    return new DriverManagerDataSource();
  }

  /**
   * Метод для создания бина ObjectMapper, который выполняет преобразование JSON в POJO и обратно.
   *
   * @return запрашиваемый ObjectMapper.
   */
  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
        .registerModule(new SimpleModule().addSerializer(LocalDateTime.class,
            new LocalDateTimeCustomSerializer()))
        .registerModule(new SimpleModule().addSerializer(LocalDate.class,
            new LocalDateCustomSerializer()))
        .registerModule(new SimpleModule().addDeserializer(LocalDate.class,
            new LocalDateCustomDeserializer()))
        .findAndRegisterModules()
        .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);
  }
}
