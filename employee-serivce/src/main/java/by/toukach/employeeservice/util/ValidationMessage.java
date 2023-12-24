package by.toukach.employeeservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Класс, содержащий сообщения валидации.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationMessage {

  public static final String INCORRECT_DATE_OF_BIRTH_FORM = "Дата должна быть прошедшей";
  public static final String NAME_REGEX = "(^[А-Я][\\p{IsCyrillic}+| ]{1,10}$)";
  public static final String INCORRECT_NAME_FORM =
      "Имя должно начинаться с большой буквы, содержать только буквы из Кириллицы. "
          + "Длина не меньше 2 и не больше 11";
  public static final String NULL_PARAM = "Не передан параметр %s";
}
