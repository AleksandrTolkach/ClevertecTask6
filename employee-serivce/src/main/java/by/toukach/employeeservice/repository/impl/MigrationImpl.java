package by.toukach.employeeservice.repository.impl;

import by.toukach.employeeservice.exception.DbException;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.repository.DbInitializer;
import by.toukach.employeeservice.repository.Migration;
import by.toukach.employeeservice.util.property.ApplicationProperties;
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

/**
 * Класс для работы с инструментами миграции БД.
 */
public class MigrationImpl implements Migration {

  private static final Migration instance = new MigrationImpl();
  private final DbInitializer dbInitializer;
  private static final String CHANGE_LOG_FILE_PATH =
      ApplicationProperties.DB_CHANGE_LOG_FILE;

  private MigrationImpl() {
    dbInitializer = DbInitializerImpl.getInstance();
  }

  /**
   * Метод для миграции БД.
   */
  @Override
  public void migrate() {

    try (Connection connection = dbInitializer.getConnection()) {

      Database database = DatabaseFactory.getInstance()
          .findCorrectDatabaseImplementation(new JdbcConnection(connection));
      database.setLiquibaseSchemaName(ApplicationProperties.DB_LIQUIBASE_SCHEMA);

      CommandScope updateCommand = new CommandScope(UpdateCommandStep.COMMAND_NAME);
      updateCommand.addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database);
      updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, CHANGE_LOG_FILE_PATH);
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

    try (Connection connection = dbInitializer.getConnection()) {
      Database database = DatabaseFactory.getInstance()
          .findCorrectDatabaseImplementation(new JdbcConnection(connection));
      database.setLiquibaseSchemaName(ApplicationProperties.DB_LIQUIBASE_SCHEMA);

      Liquibase liquibase = new Liquibase(CHANGE_LOG_FILE_PATH, new ClassLoaderResourceAccessor(),
          database);

      liquibase.rollback(tag, null, new Contexts(),
          new LabelExpression());

    } catch (SQLException | LiquibaseException e) {
      throw new DbException(ExceptionMessage.MIGRATION, e);
    }
  }

  public static Migration getInstance() {
    return instance;
  }
}
