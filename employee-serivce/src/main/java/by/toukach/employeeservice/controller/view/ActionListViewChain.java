package by.toukach.employeeservice.controller.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс для вывода формы со списком действий в консоль.
 * */
@Slf4j
public class ActionListViewChain extends ViewChain {

  private final Map<Integer, ViewChain> viewChainMap = new HashMap<>();

  /**
   * Конструктор для создания формы.
   */
  public ActionListViewChain() {
    viewChainMap.put(1, new CreateEmployeeChain());
    viewChainMap.put(2, new GetEmployeeByIdChain());
    viewChainMap.put(3, new GetAllEmployeeChain());
    viewChainMap.put(4, new UpdateEmployeeChain());
    viewChainMap.put(5, new DeleteEmployeeByChain());
    viewChainMap.put(6, new CacheContentChain());
    viewChainMap.put(7, new ClearCacheContentChain());
    viewChainMap.put(8, new ExitChain());
  }

  @Override
  public void handle() {
    log.info(ViewMessage.ACTION_CHOOSE);
    Scanner scanner = new Scanner(System.in);

    int answer = scanner.nextInt();
    scanner.nextLine();

    ViewChain viewChain = viewChainMap.get(answer);

    setNextViewChain(viewChain != null ? viewChain : new UnknownViewChain(this));
  }
}
