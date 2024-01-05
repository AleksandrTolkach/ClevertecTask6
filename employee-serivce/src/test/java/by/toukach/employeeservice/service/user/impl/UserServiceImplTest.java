package by.toukach.employeeservice.service.user.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.toukach.employeeservice.UserTestData;
import by.toukach.employeeservice.dao.User;
import by.toukach.employeeservice.dto.InfoUserDto;
import by.toukach.employeeservice.dto.UserDto;
import by.toukach.employeeservice.exception.EntityDuplicateException;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.ValidationExceptionList;
import by.toukach.employeeservice.mapper.UserMapper;
import by.toukach.employeeservice.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Test
  public void createTestShouldCreateUser() {
    // given
    User user = UserTestData.builder()
        .build()
        .buildUser();
    UserDto userDto = UserTestData.builder()
        .build()
        .buildUserDto();
    InfoUserDto expected = UserTestData.builder()
        .build()
        .buildInfoUserDto();

    when(userRepository.findByUsername(user.getName()))
        .thenReturn(Optional.empty());
    when(userMapper.toUser(userDto))
        .thenReturn(user);
    when(passwordEncoder.encode(userDto.getPassword()))
        .thenReturn(userDto.getPassword());
    when(userRepository.save(any(User.class)))
        .thenReturn(user);
    when(userMapper.toInfoUserDto(user))
        .thenReturn(expected);

    // when
    InfoUserDto actual = userService.create(userDto);

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void createTestShouldThrowExceptionWhenUserAlreadyExists() {
    // given
    User user = UserTestData.builder()
        .build()
        .buildUser();
    UserDto userDto = UserTestData.builder()
        .build()
        .buildUserDto();

    when(userRepository.findByUsername(user.getName()))
        .thenReturn(Optional.of(user));

    // when, then
    assertThatThrownBy(() -> userService.create(userDto))
        .isInstanceOf(EntityDuplicateException.class);
  }

  @Test
  public void createTestShouldThrowExceptionWhenUserFieldsIncorrect() {
    // given
    UserDto userDto = UserTestData.builder()
        .withName(null)
        .withPassword(null)
        .build()
        .buildUserDto();

    // when, then
    assertThatThrownBy(() -> userService.create(userDto))
        .isInstanceOf(ValidationExceptionList.class);
  }

  @Test
  public void getAllTestShouldReturnUserList() {
    // given
    User user = UserTestData.builder()
        .build()
        .buildUser();
    InfoUserDto infoUserDto = UserTestData.builder()
        .build()
        .buildInfoUserDto();

    List<InfoUserDto> expected = List.of(infoUserDto);

    when(userRepository.findAll())
        .thenReturn(List.of(user));
    when(userMapper.toInfoUserDtoList(List.of(user)))
        .thenReturn(expected);

    // when
    List<InfoUserDto> actual = userService.getAll();

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void getByIdTestShouldReturnUser() {
    // given
    User user = UserTestData.builder()
        .build()
        .buildUser();
    InfoUserDto expected = UserTestData.builder()
        .build()
        .buildInfoUserDto();
    Long id = user.getId();

    when(userRepository.findById(id))
        .thenReturn(Optional.of(user));
    when(userMapper.toInfoUserDto(user))
        .thenReturn(expected);

    // when
    InfoUserDto actual = userService.getById(id);

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void getByIdTestShouldThrowExceptionWhenUserNotFound() {
    // given
    User user = UserTestData.builder()
        .build()
        .buildUser();
    Long id = user.getId();

    when(userRepository.findById(id))
        .thenReturn(Optional.empty());

    // when, then
    assertThatThrownBy(() -> userService.getById(id))
        .isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  public void getByUsernameShouldReturnUser() {
    // given
    User user = UserTestData.builder()
        .build()
        .buildUser();
    InfoUserDto expected = UserTestData.builder()
        .build()
        .buildInfoUserDto();
    String name = user.getName();

    when(userRepository.findByUsername(name))
        .thenReturn(Optional.of(user));
    when(userMapper.toInfoUserDto(user))
        .thenReturn(expected);

    // when
    InfoUserDto actual = userService.getByUsername(name);

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void getByUsernameShouldThrowExceptionWhenUserNotFound() {
    // given
    User user = UserTestData.builder()
        .build()
        .buildUser();
    String name = user.getName();

    doThrow(EntityNotFoundException.class)
        .when(userRepository).findByUsername(name);

    // when, then
    assertThatThrownBy(() -> userService.getByUsername(name))
        .isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  public void updateTestShouldUpdateUser() {
    // given
    User user = UserTestData.builder()
        .build()
        .buildUser();
    UserDto userDto = UserTestData.builder()
        .build()
        .buildUserDto();
    InfoUserDto expected = UserTestData.builder()
        .build()
        .buildInfoUserDto();
    Long id = user.getId();

    when(userRepository.findById(id))
        .thenReturn(Optional.of(user));
    when(userMapper.merge(user, userDto))
        .thenReturn(user);
    when(passwordEncoder.encode(userDto.getPassword()))
        .thenReturn(userDto.getPassword());
    when(userRepository.update(id, user))
        .thenReturn(user);
    when(userMapper.toInfoUserDto(user))
        .thenReturn(expected);

    // when
    InfoUserDto actual = userService.update(id, userDto);

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void updateTestShouldThrowExceptionWhenUserNotFound() {
    // given
    UserDto userDto = UserTestData.builder()
        .build()
        .buildUserDto();
    User user = UserTestData.builder()
        .build()
        .buildUser();
    Long id = user.getId();

    doThrow(EntityNotFoundException.class)
        .when(userRepository).findById(id);

    // when, then
    assertThatThrownBy(() -> userService.update(id, userDto))
        .isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  public void updateTestShouldThrowExceptionWhenUserFieldsIncorrect() {
    // given
    UserDto userDto = UserTestData.builder()
        .withName(null)
        .withPassword(null)
        .build()
        .buildUserDto();
    User user = UserTestData.builder()
        .build()
        .buildUser();
    Long id = user.getId();

    // given, then
    assertThatThrownBy(() -> userService.update(id, userDto))
        .isInstanceOf(ValidationExceptionList.class);
  }

  @Test
  public void deleteTestShouldDeleteUser() {
    // given
    Long id = UserTestData.builder()
        .build()
        .id();

    doNothing()
        .when(userRepository).delete(id);

    // when
    userService.delete(id);

    // then
    verify(userRepository)
        .delete(id);
  }
}
