package by.toukach.employeeservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Класс, содержащий параметры для приложения.
 */
@Data
@Component
@PropertySource("classpath:application.properties")
public class ApplicationProperty {

  @Value("${database.url}")
  private String dataBaseUrl;

  @Value("${database.name}")
  private String dataBaseName;

  @Value("${database.username}")
  private String dataBaseUsername;

  @Value("${database.password}")
  private String dataBasePassword;

  @Value("${database.driver}")
  private String dataBaseDriver;

  @Value("${database.changeLogFile}")
  private String liquibaseFile;

  @Value("${database.liquibaseSchema}")
  private String liquibaseScheme;

  @Value("${database.migrationEnable}")
  private String dbMigrationEnableStatus;

  @Value("${cache.size}")
  private String cacheSize;

  @Value("${cache.algorithm}")
  private String cacheAlgorithm;

  @Value("${print.outDirectory}")
  private String printOutDirectory;

  @Value("${print.fontFilePath}")
  private String printFontFilePath;

  @Value("${print.templateFilePath}")
  private String printTemplateFilePath;
}
