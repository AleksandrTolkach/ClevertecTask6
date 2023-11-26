package by.toukach.employeeservice.util.property;

import java.io.InputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * Утилитарный класс предоставляющий доступ к конфигурационным параметрам для базы данных.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationProperties {

  private static final String CONFIGURATION_FILE_PATH = "application.yml";

  public static final String DB_URL;
  public static final String DB_NAME;
  public static final String DB_USERNAME;
  public static final String DB_PASSWORD;
  public static final String DB_DRIVER;
  public static final String DB_CHANGE_LOG_FILE;
  public static final String DB_LIQUIBASE_SCHEMA;
  public static final String CACHE_SIZE;
  public static final String CACHE_ALGORITHM;
  public static final String PRINT_OUT_DIRECTORY;
  public static final String PRINT_FONT_FILE_PATH;
  public static final String PRINT_TEMPLATE_FILE_PATH;

  static {
    InputStream resourceAsStream = ApplicationProperties.class.getClassLoader()
        .getResourceAsStream(CONFIGURATION_FILE_PATH);

    Yaml configFile = new Yaml(new Constructor(EmployeeServicePropertiesWrapper.class,
        new LoaderOptions()));
    EmployeeServicePropertiesWrapper employeeServicePropertiesWrapper =
        configFile.load(resourceAsStream);

    DB_URL = employeeServicePropertiesWrapper.getDatabase().getUrl();
    DB_NAME = employeeServicePropertiesWrapper.getDatabase().getName();
    DB_USERNAME = employeeServicePropertiesWrapper.getDatabase().getUsername();
    DB_PASSWORD = employeeServicePropertiesWrapper.getDatabase().getPassword();
    DB_DRIVER = employeeServicePropertiesWrapper.getDatabase().getDriver();
    DB_CHANGE_LOG_FILE = employeeServicePropertiesWrapper.getDatabase().getChangeLogFile();
    DB_LIQUIBASE_SCHEMA = employeeServicePropertiesWrapper.getDatabase().getLiquibaseSchema();
    CACHE_SIZE = employeeServicePropertiesWrapper.getCache().getSize();
    CACHE_ALGORITHM = employeeServicePropertiesWrapper.getCache().getAlgorithm();
    PRINT_OUT_DIRECTORY = employeeServicePropertiesWrapper.getPrint().getOutDirectory();
    PRINT_FONT_FILE_PATH = employeeServicePropertiesWrapper.getPrint().getFontFilePath();
    PRINT_TEMPLATE_FILE_PATH = employeeServicePropertiesWrapper.getPrint().getTemplateFilePath();
  }
}
