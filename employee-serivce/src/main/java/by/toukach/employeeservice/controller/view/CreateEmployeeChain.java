package by.toukach.employeeservice.controller.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс для вывода формы по созданию сотрудника.
 */
@Slf4j
public class CreateEmployeeChain extends ActionViewChain {

  private Map<Integer, ViewChain> viewChainMap = new HashMap<>();

  public CreateEmployeeChain() {
    viewChainMap.put(1, new JsonCreateEmployeeChain());
    viewChainMap.put(2, new XmlCreateEmployeeChain());
  }

  @Override
  public void handle() {
    log.info(ViewMessage.EMPLOYEE_DTO_FORMAT_CHOOSE);

    Scanner scanner = getScanner();
    int answer = scanner.nextInt();
    scanner.nextLine();

    ViewChain nextViewChain = viewChainMap.get(answer);
    setNextViewChain(
        Objects.requireNonNullElseGet(nextViewChain, () -> new UnknownViewChain(this)));
  }
}
