package by.toukach.employeeservice.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Класс, содержащий сообщения об ошибках.
 * */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessage {

  public static final String EMPLOYEE_BY_ID_NOT_FOUND = "Сотрудник с id %s не найден.";
  public static final String CONNECT_TO_DB = "Не удалось подключиться к базе данных.";
  public static final String DB_EXISTS = "База данных уже существует";
  public static final String SCHEMES_EXISTS = "Схемы уже существуют";
  public static final String MIGRATION = "В процессе миграции базы данных произошла ошибка.";
  public static final String INVOKE_METHOD = "Не удалось вызвать метод %s";
  public static final String FILED_ACCESS = "Не удалось получить данные из поля %s";
  public static final String JSON_POJO_MAP = "Не удалось преобразовать json в объект";
  public static final String POJO_JSON_MAP = "Не удалось преобразовать объект в json";
  public static final String XML_POJO_MAP = "Не удалось преобразовать xml в объект";
  public static final String POJO_XML_MAP = "Не удалось преобразовать объект в xml";
  public static final String CREATE_FILE = "Не удалось создать файл";
  public static final String SEND_ERROR = "Не удалось вернуть ошибку";
  public static final String SEND_FILE = "Не удалось отправить файл";
  public static final String USER_BY_ID_NOT_FOUND = "Пользователь с id %s не найден";
  public static final String USER_BY_USERNAME_NOT_FOUND = "Неверный логин или пароль";
  public static final String LOGIN_OFFER =
      "Пожалуйста, зарегистрируйтесь или выполните вход в приложение";
  public static final String SESSION_EXPIRED =
      "Сессия истекла. Пожалуйста, выполните вход в приложение";
  public static final String WRONG_OBJECT = "Не передан объект %s";
  public static final String WRONG_SIGN_UP = String.format(WRONG_OBJECT, "signUp");
  public static final String USER_DUPLICATE = "Пользователь с именем %s уже существует.";
  public static final String ACCESS_DENIED = "Недостаточно прав";
}
