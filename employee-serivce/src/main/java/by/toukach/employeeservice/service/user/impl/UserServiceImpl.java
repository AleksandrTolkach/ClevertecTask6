package by.toukach.employeeservice.service.user.impl;

import by.toukach.employeeservice.aspect.annotation.Validated;
import by.toukach.employeeservice.dao.User;
import by.toukach.employeeservice.dto.InfoUserDto;
import by.toukach.employeeservice.dto.UserDto;
import by.toukach.employeeservice.enumiration.UserRole;
import by.toukach.employeeservice.exception.EntityDuplicateException;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.mapper.UserMapper;
import by.toukach.employeeservice.repository.UserRepository;
import by.toukach.employeeservice.service.user.UserService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Класс для работы с сотрудниками.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  /**
   * Метод для создания пользователя в приложении.
   *
   * @param userDto пользователь для создания.
   * @return информация о созданном пользователе.
   */
  @Override
  @Validated
  @Transactional
  public InfoUserDto create(UserDto userDto) {

    userRepository.findByUsername(userDto.getName())
        .ifPresent(user -> {
          throw new EntityDuplicateException(ExceptionMessage.USER_DUPLICATE.formatted(
            user.getName()));
        });

    User user = userMapper.toUser(userDto);
    user.setCreatedAt(LocalDateTime.now());
    user.setRole(user.getRole() != null ? user.getRole() : UserRole.USER);
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));

    return userMapper.toInfoUserDto(userRepository.save(user));
  }

  /**
   * Метод для получения списка информации о пользователях.
   *
   * @return запрашиваемый список.
   */
  @Override
  public List<InfoUserDto> getAll() {
    List<User> userList = userRepository.findAll();
    return userMapper.toInfoUserDtoList(userList);
  }

  /**
   * Метод для получения информации о пользователе по его id.
   *
   * @param id id пользователя.
   * @return запрашиваемая информация.
   */
  @Override
  public InfoUserDto getById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            ExceptionMessage.USER_BY_ID_NOT_FOUND.formatted(id)));
    return userMapper.toInfoUserDto(user);
  }

  /**
   * Метод для получения информации о пользователе по его имени.
   *
   * @param username имя пользователя.
   * @return запрашиваемая информация.
   */
  @Override
  public InfoUserDto getByUsername(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new EntityNotFoundException(ExceptionMessage.USER_BY_USERNAME_NOT_FOUND));
    return userMapper.toInfoUserDto(user);
  }

  /**
   * Метод для обновления информации о сотруднике в приложении.
   *
   * @param id id пользователя для обновления.
   * @param userDto пользователь для обновления.
   * @return обновленный пользователь.
   */
  @Override
  @Validated
  @Transactional
  public InfoUserDto update(Long id, UserDto userDto) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            ExceptionMessage.USER_BY_ID_NOT_FOUND.formatted(id)));

    user = userMapper.merge(user, userDto);
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user = userRepository.update(id, user);

    return userMapper.toInfoUserDto(user);
  }

  /**
   * Метод для удаления пользователя из приложения.
   *
   * @param id id пользователя.
   */
  @Override
  public void delete(Long id) {
    userRepository.delete(id);
  }
}
