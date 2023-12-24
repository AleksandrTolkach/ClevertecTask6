package by.toukach.employeeservice.controller.listener;

import by.toukach.employeeservice.repository.Migration;
import by.toukach.employeeservice.repository.impl.MigrationImpl;
import by.toukach.employeeservice.util.property.ApplicationProperties;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Слушатель старта приложения.
 */
@WebListener
public class StartupListener implements ServletContextListener {

  private final Migration migration;

  public StartupListener() {
    migration = MigrationImpl.getInstance();
  }

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    if (ApplicationProperties.DB_MIGRATION_ENABLE_STATUS) {
      migration.migrate();
    }
  }
}
