package by.toukach.employeeservice.repository.impl;

import by.toukach.employeeservice.config.ApplicationProperty;
import by.toukach.employeeservice.exception.DbException;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.repository.Migration;
import java.sql.Connection;
import java.sql.SQLException;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.command.CommandScope;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionCommandStep;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CommandExecutionException;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

/**
 * Класс для работы с инструментами миграции БД.
 */
@Component
@RequiredArgsConstructor
public class MigrationImpl implements Migration {

  private final ApplicationProperty applicationProperty;
  private final DriverManagerDataSource dataSource;

  /**
   * Метод для миграции БД.
   */
  @Override
  public void migrate() {

    try (Connection connection = dataSource.getConnection()) {

      Database database = DatabaseFactory.getInstance()
          .findCorrectDatabaseImplementation(new JdbcConnection(connection));
      database.setLiquibaseSchemaName(applicationProperty.getLiquibaseScheme());

      CommandScope updateCommand = new CommandScope(UpdateCommandStep.COMMAND_NAME);
      updateCommand.addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database);
      updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG,
          applicationProperty.getLiquibaseFile());
      updateCommand.execute();

    } catch (SQLException | DatabaseException | CommandExecutionException e) {
      throw new DbException(ExceptionMessage.MIGRATION, e);
    }
  }

  /**
   * Откат внесенных изменений до определенного тега.
   *
   * @param tag тег, то которого необходимо откатить БД.
   */
  @Override
  public void rollback(String tag) {

    try (Connection connection = dataSource.getConnection()) {
      Database database = DatabaseFactory.getInstance()
          .findCorrectDatabaseImplementation(new JdbcConnection(connection));
      database.setLiquibaseSchemaName(applicationProperty.getLiquibaseScheme());

      Liquibase liquibase = new Liquibase(applicationProperty.getLiquibaseFile(),
          new ClassLoaderResourceAccessor(), database);

      liquibase.rollback(tag, null, new Contexts(),
          new LabelExpression());

    } catch (SQLException | LiquibaseException e) {
      throw new DbException(ExceptionMessage.MIGRATION, e);
    }
  }
}
