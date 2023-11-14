package by.toukach.employeeservice.controller.view;

import lombok.extern.slf4j.Slf4j;

/**
 * Класс для вывода формы о завершении работы приложения.
 */
@Slf4j
public class ExitChain extends ActionViewChain {

  @Override
  public void handle() {
    log.info(ViewMessage.BYE);

    setNextViewChain(null);
  }
}
