package by.toukach.employeeservice.service.authentication.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import by.toukach.employeeservice.AuthTestData;
import by.toukach.employeeservice.UserTestData;
import by.toukach.employeeservice.dto.InfoUserDto;
import by.toukach.employeeservice.dto.LogInDto;
import by.toukach.employeeservice.dto.LogInResponseDto;
import by.toukach.employeeservice.dto.LogInResponseDto.Fields;
import by.toukach.employeeservice.dto.SignUpDto;
import by.toukach.employeeservice.dto.UserDto;
import by.toukach.employeeservice.exception.EntityDuplicateException;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.ValidationExceptionList;
import by.toukach.employeeservice.service.user.UserService;
import by.toukach.employeeservice.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthServiceImplTest {

  @InjectMocks
  private AuthServiceImpl authService;

  @Mock
  private UserService userService;

  @Mock
  private AuthenticationManager authenticationManager;

  @Test
  public void logInTestShouldLogInUser() {
    // given

    InfoUserDto infoUserDto = UserTestData.builder()
        .build()
        .buildInfoUserDto();
    String name = infoUserDto.getName();
    LogInDto logInDto = AuthTestData.builder()
        .build()
        .buildLoginDto();

    String token = JwtUtil.generateTokenFromUsername(infoUserDto.getName());

    LogInResponseDto expected = LogInResponseDto.builder()
        .accessToken(token)
        .infoUserDto(infoUserDto)
        .build();

    when(userService.getByUsername(name))
        .thenReturn(infoUserDto);

    // when
    LogInResponseDto actual = authService.logIn(logInDto);

    // then
    assertThat(actual)
        .usingRecursiveComparison()
        .ignoringFields(Fields.accessToken)
        .isEqualTo(expected);

    assertThat(JwtUtil.validateJwtToken(token))
        .isTrue();

    assertThat(JwtUtil.getUsernameFromJwtToken(token))
        .isEqualTo(logInDto.getUsername());
  }

  @Test
  public void logInTestShouldThrowExceptionWhenUserNotFound() {
    // given

    LogInDto logInDto = AuthTestData.builder()
        .build()
        .buildLoginDto();

    doThrow(EntityNotFoundException.class)
        .when(userService).getByUsername(logInDto.getUsername());

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
    SignUpDto signUpDto = AuthTestData.builder()
        .build()
        .buildSignUpDto();
    InfoUserDto infoUserDto = UserTestData.builder()
        .build()
        .buildInfoUserDto();
    UserDto userDto = UserTestData.builder()
        .build()
        .buildUserDto();

    String token = JwtUtil.generateTokenFromUsername(infoUserDto.getName());

    LogInResponseDto expected = LogInResponseDto.builder()
        .accessToken(token)
        .infoUserDto(infoUserDto)
        .build();

    when(userService.create(userDto))
        .thenReturn(infoUserDto);

    // when
    LogInResponseDto actual = authService.signUp(signUpDto);

    // then
    assertThat(actual)
        .usingRecursiveComparison()
        .ignoringFields(LogInResponseDto.Fields.accessToken)
        .isEqualTo(expected);

    assertThat(JwtUtil.validateJwtToken(token))
        .isTrue();

    assertThat(JwtUtil.getUsernameFromJwtToken(token))
        .isEqualTo(signUpDto.getUsername());
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
    LogInResponseDto expected = LogInResponseDto.builder()
        .build();

    // when
    LogInResponseDto actual = authService.logOut();

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

}
