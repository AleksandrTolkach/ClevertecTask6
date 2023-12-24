package by.toukach.employeeservice.service.document.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import by.toukach.employeeservice.EmployeeTestData;
import by.toukach.employeeservice.dto.InfoEmployeeDto;
import by.toukach.employeeservice.dto.Page;
import by.toukach.employeeservice.dto.Pageable;
import by.toukach.employeeservice.enumiration.DocumentType;
import by.toukach.employeeservice.exception.EntityNotFoundException;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.service.document.DocumentService;
import by.toukach.employeeservice.service.document.DocumentServiceFactory;
import by.toukach.employeeservice.service.document.FileManager;
import by.toukach.employeeservice.service.employee.EmployeeService;
import by.toukach.employeeservice.service.employee.impl.EmployeeServiceImpl;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EmployeeDocumentHandlerTest {

  private EmployeeDocumentHandler employeeDocumentHandler;

  @Mock
  private EmployeeService employeeService;
  private MockedStatic<EmployeeServiceImpl> employeeServiceImplMockedStatic;

  @Mock
  private DocumentServiceFactory documentServiceFactory;
  private MockedStatic<DocumentServiceFactoryImpl> documentServiceFactoryImplMockedStatic;

  @Mock
  private FileManager fileManager;
  private MockedStatic<FileManagerImpl> fileManagerMockedStatic;

  @Mock
  private DocumentService documentService;

  @BeforeEach
  public void setUp() {
    employeeServiceImplMockedStatic = mockStatic(EmployeeServiceImpl.class);
    employeeServiceImplMockedStatic
        .when(EmployeeServiceImpl::getInstance)
        .thenReturn(employeeService);

    documentServiceFactoryImplMockedStatic = mockStatic(DocumentServiceFactoryImpl.class);
    documentServiceFactoryImplMockedStatic
        .when(DocumentServiceFactoryImpl::getInstance)
        .thenReturn(documentServiceFactory);

    fileManagerMockedStatic = mockStatic(FileManagerImpl.class);
    fileManagerMockedStatic
        .when(FileManagerImpl::getInstance)
        .thenReturn(fileManager);

    employeeDocumentHandler = new EmployeeDocumentHandler();
  }

  @AfterEach
  public void cleanUp() {
    employeeServiceImplMockedStatic.close();
    documentServiceFactoryImplMockedStatic.close();
    fileManagerMockedStatic.close();
  }

  @Test
  public void handleTestShouldReturnByteArrayOutputStream() {
    // given
    List<InfoEmployeeDto> infoEmployeeDtoList =
        List.of(EmployeeTestData.builder().build().buildInfoEmployeeDto());

    Pageable pageable = Pageable.builder()
        .pageNumber(0)
        .pageSize(1)
        .build();
    Page<InfoEmployeeDto> infoEmployeeDtoPage =
        Page.of(pageable, infoEmployeeDtoList, infoEmployeeDtoList.size());

    ByteArrayOutputStream expected = new ByteArrayOutputStream();

    when(employeeService.getAll(pageable))
        .thenReturn(infoEmployeeDtoPage);
    when(documentServiceFactory.getDocumentService(DocumentType.PDF))
        .thenReturn(documentService);
    when(documentService.createDocumentFromObjectList(infoEmployeeDtoList, InfoEmployeeDto.class))
        .thenReturn(expected);
    doNothing()
        .when(fileManager)
        .createFile(expected, DocumentType.PDF, EmployeeTestData.FILE_NAME);

    // when
    ByteArrayOutputStream actual = employeeDocumentHandler.handle(pageable, DocumentType.PDF);

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void handleByIdTestShouldReturnByteArrayOutputStream() {
    // given
    InfoEmployeeDto infoEmployeeDto = EmployeeTestData.builder().build().buildInfoEmployeeDto();
    Long infoEmployeeDtoId = infoEmployeeDto.getId();

    ByteArrayOutputStream expected = new ByteArrayOutputStream();

    when(employeeService.getById(infoEmployeeDtoId))
        .thenReturn(infoEmployeeDto);
    when(documentServiceFactory.getDocumentService(DocumentType.PDF))
        .thenReturn(documentService);
    when(documentService.createDocumentFromSingleObject(infoEmployeeDto))
        .thenReturn(expected);
    doNothing()
        .when(fileManager)
        .createFile(expected, DocumentType.PDF, EmployeeTestData.FILE_NAME);

    // when
    ByteArrayOutputStream actual =
        employeeDocumentHandler.handle(infoEmployeeDtoId, DocumentType.PDF);

    // then
    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  public void handleByIdTestShouldThrowExceptionWhenEmployeeNotExist() {
    // given
    InfoEmployeeDto infoEmployeeDto = EmployeeTestData.builder().build().buildInfoEmployeeDto();
    Long infoEmployeeDtoId = infoEmployeeDto.getId();

    EntityNotFoundException expected = new EntityNotFoundException(
        String.format(ExceptionMessage.EMPLOYEE_BY_ID_NOT_FOUND, infoEmployeeDtoId));

    doThrow(expected)
        .when(employeeService)
        .getById(infoEmployeeDtoId);

    // when, then
    assertThatThrownBy(() -> employeeDocumentHandler.handle(infoEmployeeDtoId, DocumentType.PDF))
        .isEqualTo(expected);
  }
}
