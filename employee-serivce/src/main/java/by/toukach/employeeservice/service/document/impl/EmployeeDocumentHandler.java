package by.toukach.employeeservice.service.document.impl;

import by.toukach.employeeservice.dto.InfoEmployeeDto;
import by.toukach.employeeservice.enumiration.DocumentType;
import by.toukach.employeeservice.service.document.DocumentHandler;
import by.toukach.employeeservice.service.document.DocumentService;
import by.toukach.employeeservice.service.document.DocumentServiceFactory;
import by.toukach.employeeservice.service.document.FileManager;
import by.toukach.employeeservice.service.employee.EmployeeService;
import by.toukach.employeeservice.service.employee.impl.EmployeeServiceImpl;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Класс для обработки запросов на создание документа по сотрудникам.
 */
public class EmployeeDocumentHandler implements DocumentHandler {

  private static final String FILE_NAME = "employee_%s";
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

  private final EmployeeService employeeService;
  private final DocumentServiceFactory documentServiceFactory;
  private final FileManager fileManager;

  /**
   * Конструктор для создания обработчика.
   */
  public EmployeeDocumentHandler() {
    employeeService = EmployeeServiceImpl.getInstance();
    documentServiceFactory = DocumentServiceFactoryImpl.getInstance();
    fileManager = FileManagerImpl.getInstance();
  }

  /**
   * Метод для обработки запроса на создание документа по сотрудникам.
   *
   * @param documentType тип документа.
   * @return ByteArrayOutputStream с созданным документом.
   */
  @Override
  public ByteArrayOutputStream handle(DocumentType documentType) {
    List<InfoEmployeeDto> infoEmployeeDtoList = employeeService.getAll();
    DocumentService documentService = documentServiceFactory.getDocumentService(documentType);

    ByteArrayOutputStream documentAsByteArrayOutputStream =
        documentService.createDocumentFromObjectList(infoEmployeeDtoList);

    createFile(documentAsByteArrayOutputStream, documentType);

    return documentAsByteArrayOutputStream;
  }

  /**
   * Метод для обработки запроса на создание документа по сотруднику.
   *
   * @param employeeId   id сотрудника.
   * @param documentType тип документа.
   * @return ByteArrayOutputStream с созданным документом.
   */
  @Override
  public ByteArrayOutputStream handle(Long employeeId, DocumentType documentType) {
    InfoEmployeeDto infoEmployeeDto = employeeService.getById(employeeId);
    DocumentService documentService = documentServiceFactory.getDocumentService(documentType);

    ByteArrayOutputStream documentAsByteArrayOutputStream =
        documentService.createDocumentFromSingleObject(infoEmployeeDto);

    createFile(documentAsByteArrayOutputStream, documentType);

    return documentAsByteArrayOutputStream;
  }

  private void createFile(ByteArrayOutputStream documentAsByteArrayOutputStream,
      DocumentType documentType) {

    LocalDateTime createdAt = LocalDateTime.now().withNano(0);
    String createdAtAsString = createdAt.format(DATE_TIME_FORMATTER);

    fileManager.createFile(documentAsByteArrayOutputStream, documentType,
        String.format(FILE_NAME, createdAtAsString));
  }
}
