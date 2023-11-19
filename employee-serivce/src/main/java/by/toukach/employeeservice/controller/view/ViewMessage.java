package by.toukach.employeeservice.controller.view;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Класс, содержащий сообщения для форм.
 * */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ViewMessage {

  public static final String ACTION_CHOOSE = "Выберите действие:\n1.Создать сотрудника\n"
      + "2.Получить сотрудника по id\n3.Получить список всех сотрудников\n"
      + "4.Обновить сотрудника\n5.Удалить сотрудника\n6.Посмотреть сотрудников в кэше\n"
      + "7.Очистить кэш\n8.Завершить приложение";

  public static final String EMPLOYEE_JSON = "Передайте данные о сотруднике в формате json";
  public static final String EMPLOYEE_XML = "Передайте данные о сотруднике в формате xml";
  public static final String WRONG_OPTION = "Выбран неверный вариант.\n";
  public static final String EMPLOYEE_ID = "Введите id сотрудника.\n";
  public static final String ILLEGAL_ARGUMENT = "Введен неверный символ";
  public static final String BYE = "Всего доброго!";
  public static final String EMPLOYEE_DTO_FORMAT_CHOOSE =
      "Выберите формат ввода данных\n1. JSON\n2. XML";
}

