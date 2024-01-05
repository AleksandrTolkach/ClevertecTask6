package by.toukach.employeeservice.config;

import jakarta.servlet.ServletContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * Конфигурационный класс для настройки сервлет контейнера.
 */
public class MainWebAppInitializer implements WebApplicationInitializer {

  public static final String BASE_PACKAGE = "by.toukach.employeeservice";

  @Override
  public void onStartup(ServletContext container) {
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.scan(BASE_PACKAGE);
    container.addListener(new ContextLoaderListener(context));
  }
}
