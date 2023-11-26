package by.toukach.employeeservice.service.document.impl;

import static org.assertj.core.api.Assertions.assertThat;

import by.toukach.employeeservice.enumiration.DocumentType;
import by.toukach.employeeservice.service.document.DocumentService;
import by.toukach.employeeservice.service.document.DocumentServiceFactory;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DocumentServiceFactoryImplTest {

  private final DocumentServiceFactory documentServiceFactory =
      DocumentServiceFactoryImpl.getInstance();

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
