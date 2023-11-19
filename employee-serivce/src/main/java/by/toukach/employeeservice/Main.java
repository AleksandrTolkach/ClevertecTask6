package by.toukach.employeeservice;

import by.toukach.employeeservice.controller.view.ActionListViewChain;
import by.toukach.employeeservice.controller.view.ViewChain;
import by.toukach.employeeservice.controller.view.ViewMessage;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.IllegalArgumentException;
import by.toukach.employeeservice.exception.JsonMapperException;
import by.toukach.employeeservice.exception.ValidationExceptionList;
import by.toukach.employeeservice.repository.impl.DbInitializerImpl;
import by.toukach.employeeservice.repository.impl.MigrationImpl;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс, представляющий точку входа в приложение.
 */
@Slf4j
public class Main {

  /**
   * Метод для запуска приложения.
   *
   * @param args передаваемые аргументы при старте приложения.
   * @throws InterruptedException исключение, выбрасываемое при прерывании приложения.
   */
  public static void main(String[] args) throws InterruptedException {
    DbInitializerImpl.getInstance();
    MigrationImpl.getInstance().migrate();

    Thread.sleep(3000L);

    ViewChain viewChain = new ActionListViewChain();

    while (viewChain != null) {
      try {
        viewChain.handle();

      } catch (JsonMapperException | InputMismatchException | DateTimeParseException
          | IllegalArgumentException e) {
        log.info(ViewMessage.ILLEGAL_ARGUMENT);

        viewChain.setScanner(new Scanner(System.in));
        viewChain.setNextViewChain(viewChain);

      } catch (ValidationExceptionList | EntityNotFoundException e) {
        log.info(e.getMessage());

        viewChain.setScanner(new Scanner(System.in));
        viewChain.setNextViewChain(viewChain);
      }

      viewChain = viewChain.getNextViewChain();
    }
  }
}
