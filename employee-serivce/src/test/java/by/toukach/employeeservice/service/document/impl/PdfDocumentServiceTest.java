package by.toukach.employeeservice.service.document.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import by.toukach.employeeservice.EmployeeTestData;
import by.toukach.employeeservice.config.ApplicationProperty;
import by.toukach.employeeservice.dto.InfoEmployeeDto;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PdfDocumentServiceTest {

  private static final String FONT_FILE_PATH = "document/pdf/font/Alice-Regular.ttf";
  private static final String TEMPLATE_FILE_PATH = "document/pdf/template/Clevertec_Template.pdf";

  @InjectMocks
  private PdfDocumentService pdfDocumentService;

  @Mock
  private ApplicationProperty applicationProperty;

  @Test
  public void createDocumentFromSingleObjectTestShouldCreateByteArrayOutputStream()
      throws IOException {
    // given
    InfoEmployeeDto infoEmployeeDto = EmployeeTestData.builder()
        .build()
        .buildInfoEmployeeDto();

    ByteArrayOutputStream expected = new ByteArrayOutputStream();

    Files.copy(EmployeeTestData.EMPLOYEE_PDF_FILE_PATH, expected);

    when(applicationProperty.getPrintFontFilePath())
        .thenReturn(FONT_FILE_PATH);
    when(applicationProperty.getPrintTemplateFilePath())
        .thenReturn(TEMPLATE_FILE_PATH);

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

    when(applicationProperty.getPrintFontFilePath())
        .thenReturn(FONT_FILE_PATH);
    when(applicationProperty.getPrintTemplateFilePath())
        .thenReturn(TEMPLATE_FILE_PATH);

    // when
    ByteArrayOutputStream actual =
        pdfDocumentService.createDocumentFromObjectList(infoEmployeeDtoList, InfoEmployeeDto.class);

    // then
    assertThat(actual.size())
        .isEqualTo(expected.size());
  }
}
