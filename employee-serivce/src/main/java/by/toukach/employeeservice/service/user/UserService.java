package by.toukach.employeeservice.service.user;

import by.toukach.employeeservice.dto.InfoUserDto;
import by.toukach.employeeservice.dto.UserDto;
import java.util.List;

/**
 * Интерфейс для работы с пользователями.
 */
public interface UserService {

  InfoUserDto create(UserDto userDto);

  List<InfoUserDto> getAll();

  InfoUserDto getById(Long id);

  InfoUserDto getByUsername(String username);

  InfoUserDto update(Long id, UserDto userDto);

  void delete(Long id);
}
