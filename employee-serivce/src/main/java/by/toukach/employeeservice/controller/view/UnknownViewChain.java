package by.toukach.employeeservice.controller.view;

import lombok.extern.slf4j.Slf4j;

/**
 * Класс для вывода формы о некорректности вводных данных в консоль.
 * */
@Slf4j
public class UnknownViewChain extends ViewChain {

  public UnknownViewChain(ViewChain viewChain) {
    setNextViewChain(viewChain);
  }

  @Override
  public void handle() {
    log.info(ViewMessage.WRONG_OPTION);
  }
}