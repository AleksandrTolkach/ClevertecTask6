package by.toukach.employeeservice.service.authentication.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import by.toukach.employeeservice.AuthTestData;
import by.toukach.employeeservice.AuthenticationTestData;
import by.toukach.employeeservice.UserTestData;
import by.toukach.employeeservice.dto.InfoUserDto;
import by.toukach.employeeservice.dto.LogInDto;
import by.toukach.employeeservice.dto.LogInDtoResponse;
import by.toukach.employeeservice.dto.SignUpDto;
import by.toukach.employeeservice.dto.UserDto;
import by.toukach.employeeservice.exception.EntityDuplicateException;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.ValidationExceptionList;
import by.toukach.employeeservice.security.Authentication;
import by.toukach.employeeservice.security.AuthenticationManager;
import by.toukach.employeeservice.security.impl.AuthenticationManagerImpl;
import by.toukach.employeeservice.service.user.UserService;
import by.toukach.employeeservice.service.user.impl.UserServiceImpl;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthServiceImplTest {

  @InjectMocks
  private AuthServiceImpl authService;

  @Mock
  private UserService userService;

  @Mock
  private AuthenticationManager authenticationManager;

  private MockedStatic<UserServiceImpl> userServiceMockedStatic;
  private MockedStatic<AuthenticationManagerImpl> authenticationManagerMockedStatic;

  @BeforeEach
  public void setUp() throws NoSuchMethodException, InvocationTargetException,
      InstantiationException, IllegalAccessException {

    userServiceMockedStatic = mockStatic(UserServiceImpl.class);
    userServiceMockedStatic
        .when(UserServiceImpl::getInstance)
        .thenReturn(userService);

    authenticationManagerMockedStatic = mockStatic(AuthenticationManagerImpl.class);
    authenticationManagerMockedStatic
        .when(AuthenticationManagerImpl::getInstance)
        .thenReturn(authenticationManager);

    Constructor<AuthServiceImpl> privateConstructor = AuthServiceImpl.class
        .getDeclaredConstructor();
    privateConstructor.setAccessible(true);

    authService = privateConstructor.newInstance();
  }

  @AfterEach
  public void cleanUp() {
    userServiceMockedStatic.close();
    authenticationManagerMockedStatic.close();
  }

  @Test
  public void logInTestShouldLogInUser() {
    // given
    Authentication authentication = AuthenticationTestData.builder()
        .build()
        .buildAuthentication();
    InfoUserDto infoUserDto = UserTestData.builder()
        .build()
        .buildInfoUserDto();
    String name = infoUserDto.getName();
    LogInDto logInDto = AuthTestData.builder()
        .build()
        .buildLoginDto();
    LogInDtoResponse expected = LogInDtoResponse.builder()
        .authentication(authentication)
        .infoUserDto(infoUserDto)
        .build();

    when(userService.getByUsername(name))
        .thenReturn(infoUserDto);
    when(authenticationManager.authenticate(name, infoUserDto.getPassword()))
        .thenReturn(authentication);

    // when
    LogInDtoResponse actual = authService.logIn(logInDto);

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void logInTestShouldThrowExceptionWhenUserNotFound() {
    // given
    Authentication authentication = AuthenticationTestData.builder()
        .build()
        .buildAuthentication();
    LogInDto logInDto = AuthTestData.builder()
        .build()
        .buildLoginDto();

    doThrow(EntityNotFoundException.class)
        .when(userService).getByUsername(authentication.getUsername());

    // when, then
    assertThatThrownBy(() -> authService.logIn(logInDto))
        .isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  public void logInTestShouldThrowExceptionWhenUserNotAuthenticated() {
    // given
    Authentication authentication = AuthenticationTestData.builder()
        .withAuthenticated(false)
        .build()
        .buildAuthentication();
    LogInDto logInDto = AuthTestData.builder()
        .build()
        .buildLoginDto();
    InfoUserDto infoUserDto = UserTestData.builder()
        .build()
        .buildInfoUserDto();
    String username = logInDto.getUsername();

    when(userService.getByUsername(username))
        .thenReturn(infoUserDto);
    when(authenticationManager.authenticate(username, logInDto.getPassword()))
        .thenReturn(authentication);

    // when, then
    assertThatThrownBy(() -> authService.logIn(logInDto))
        .isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  public void logInTestShouldThrowExceptionWhenLogInDtoFieldsIncorrect() {
    // given
    LogInDto logInDto = AuthTestData.builder()
        .withUsername(null)
        .withPassword(null)
        .build()
        .buildLoginDto();

    // when
    assertThatThrownBy(() -> authService.logIn(logInDto))
        .isInstanceOf(ValidationExceptionList.class);
  }

  @Test
  public void signUpTestShouldSignUpUser() {
    // given
    Authentication authentication = AuthenticationTestData.builder()
        .build()
        .buildAuthentication();
    SignUpDto signUpDto = AuthTestData.builder()
        .build()
        .buildSignUpDto();
    InfoUserDto infoUserDto = UserTestData.builder()
        .build()
        .buildInfoUserDto();
    UserDto userDto = UserTestData.builder()
        .build()
        .buildUserDto();
    LogInDtoResponse expected = LogInDtoResponse.builder()
        .authentication(authentication)
        .infoUserDto(infoUserDto)
        .build();

    when(userService.create(userDto))
        .thenReturn(infoUserDto);
    when(authenticationManager.authenticate(signUpDto.getUsername(), signUpDto.getPassword()))
        .thenReturn(authentication);

    // when
    LogInDtoResponse actual = authService.signUp(signUpDto);

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void signUpTestShouldThrowExceptionWhenUserAlreadyExists() {
    // given
    SignUpDto signUpDto = AuthTestData.builder()
        .build()
        .buildSignUpDto();
    UserDto userDto = UserTestData.builder()
        .build()
        .buildUserDto();

    doThrow(EntityDuplicateException.class)
        .when(userService).create(userDto);

    // when, then
    assertThatThrownBy(() -> authService.signUp(signUpDto))
        .isInstanceOf(EntityDuplicateException.class);
  }

  @Test
  public void signUpTestShouldThrowExceptionWhenSignUpDtoFieldsIncorrect() {
    // given
    SignUpDto signUpDto = AuthTestData.builder()
        .withUsername(null)
        .withPassword(null)
        .build()
        .buildSignUpDto();

    // when, then
    assertThatThrownBy(() -> authService.signUp(signUpDto))
        .isInstanceOf(ValidationExceptionList.class);
  }

  @Test
  public void logOutTestShouldLogOutUser() {
    // given
    InfoUserDto infoUserDto = UserTestData.builder()
        .build()
        .buildInfoUserDto();

    String username = infoUserDto.getName();
    LogInDtoResponse expected = LogInDtoResponse.builder()
        .authentication(null)
        .infoUserDto(infoUserDto)
        .build();

    when(userService.getByUsername(username))
        .thenReturn(infoUserDto);
    doNothing()
        .when(authenticationManager).clearAuthentication(username);

    // when
    LogInDtoResponse actual = authService.logOut(username);

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void logOutTestShouldThrowExceptionWhenUserNotFound() {
    // given
    String username = AuthTestData.builder().build().username();

    doThrow(EntityNotFoundException.class)
        .when(userService).getByUsername(username);

    // when, then
    assertThatThrownBy(() -> authService.logOut(username))
        .isInstanceOf(EntityNotFoundException.class);
  }
}
