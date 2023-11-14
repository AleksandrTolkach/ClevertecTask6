package by.toukach.employeeservice.controller.view;

import java.util.Scanner;
import lombok.Data;

/**
 * Класс для вывода формы с данными в консоль.
 * */
@Data
public abstract class ViewChain {

  private ViewChain nextViewChain;
  private Scanner scanner = new Scanner(System.in);

  /**
   * Метод для обработки формы с данными.
   * */
  public abstract void handle();
}
