package by.toukach.employeeservice.mapper;

import by.toukach.employeeservice.dao.User;
import by.toukach.employeeservice.dto.InfoUserDto;
import by.toukach.employeeservice.dto.UserDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Интерфейс для преобразования {@link User} в {@link InfoUserDto},
 * {@link UserDto} в {@link User} и наоборот.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

  /**
   * Метод для преобразования {@link User} в {@link InfoUserDto}.
   *
   * @param user {@link User} для преобразования.
   * @return преобразованный {@link InfoUserDto}.
   */
  InfoUserDto toInfoUserDto(User user);

  /**
   * Метод для преобразования {@link UserDto} в {@link User}.
   *
   * @param userDto {@link UserDto} для преобразования.
   * @return преобразованный {@link User}.
   */
  User toUser(UserDto userDto);

  /**
   * Метод для преобразования для сливания существующего {@link User}
   * с информацией из {@link UserDto}.
   *
   * @param user {@link User} для сливания.
   * @param userDto информация для обновления.
   * @return обновленный {@link User}.
   */
  User merge(@MappingTarget User user, UserDto userDto);

  /**
   * Метод для преобразования списка {@link User} в список {@link InfoUserDto}.
   *
   * @param userList списка {@link User} для преобразования.
   * @return преобразованный список {@link InfoUserDto}.
   */
  List<InfoUserDto> toInfoUserDtoList(List<User> userList);
}
