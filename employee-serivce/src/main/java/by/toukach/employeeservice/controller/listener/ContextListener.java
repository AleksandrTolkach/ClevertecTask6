package by.toukach.employeeservice.controller.listener;

import by.toukach.employeeservice.config.ApplicationProperty;
import by.toukach.employeeservice.repository.DbInitializer;
import by.toukach.employeeservice.repository.Migration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Слушатель состояния контекста.
 */
@Component
@RequiredArgsConstructor
public class ContextListener {

  private final Migration migration;
  private final DbInitializer dbInitializer;
  private final ApplicationProperty applicationProperty;

  /**
   * Метод для прослушивания события по обновлению контекста.
   */
  @EventListener(ContextRefreshedEvent.class)
  public void contextInitialized() {
    dbInitializer.prepareDb();
    if (Boolean.valueOf(applicationProperty.getDbMigrationEnableStatus())) {
      migration.migrate();
    }
  }
}
