package by.toukach.employeeservice.service.document.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import by.toukach.employeeservice.enumiration.DocumentType;
import by.toukach.employeeservice.service.document.DocumentService;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DocumentServiceFactoryImplTest {

  private DocumentServiceFactoryImpl documentServiceFactory;

  @Mock
  private PdfDocumentService pdfDocumentService;

  @BeforeEach
  public void setUp() {
    when(pdfDocumentService.getDocumentType())
        .thenReturn(DocumentType.PDF);

    documentServiceFactory = new DocumentServiceFactoryImpl(List.of(pdfDocumentService));
  }

  @ParameterizedTest
  @MethodSource("documentTypeProvider")
  public void getDocumentServiceTest(DocumentType documentType, Class<?> expected) {
    // when
    DocumentService actual = documentServiceFactory.getDocumentService(documentType);

    // then
    assertThat(actual)
        .isInstanceOf(expected);
  }

  public static Stream<Arguments> documentTypeProvider() {
    return Stream.of(
        Arguments.arguments(DocumentType.PDF, PdfDocumentService.class)
    );
  }
}
