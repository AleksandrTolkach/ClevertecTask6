package by.toukach.employeeservice.service.document.impl;

import static org.assertj.core.api.Assertions.assertThat;

import by.toukach.employeeservice.EmployeeTestData;
import by.toukach.employeeservice.dto.InfoEmployeeDto;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PdfDocumentServiceTest {

  private final PdfDocumentService pdfDocumentService = new PdfDocumentService();

  @Test
  public void createDocumentFromSingleObjectTestShouldCreateByteArrayOutputStream()
      throws IOException {
    // given
    InfoEmployeeDto infoEmployeeDto = EmployeeTestData.builder()
        .build()
        .buildInfoEmployeeDto();

    ByteArrayOutputStream expected = new ByteArrayOutputStream();

    Files.copy(EmployeeTestData.EMPLOYEE_PDF_FILE_PATH, expected);

    // when
    ByteArrayOutputStream actual =
        pdfDocumentService.createDocumentFromSingleObject(infoEmployeeDto);

    // then
    assertThat(actual.size())
        .isEqualTo(expected.size());
  }

  @Test
  public void createDocumentFromObjectListTestShouldCreateByteArrayOutputStream()
      throws IOException {
    // given
    List<InfoEmployeeDto> infoEmployeeDtoList = List.of(EmployeeTestData.builder()
        .build()
        .buildInfoEmployeeDto());

    ByteArrayOutputStream expected = new ByteArrayOutputStream();

    Files.copy(EmployeeTestData.EMPLOYEE_PDF_FILE_PATH, expected);

    // when
    ByteArrayOutputStream actual =
        pdfDocumentService.createDocumentFromObjectList(infoEmployeeDtoList);

    // then
    assertThat(actual.size())
        .isEqualTo(expected.size());
  }
}
