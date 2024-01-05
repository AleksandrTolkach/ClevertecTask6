package by.toukach.employeeservice.service.document.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import by.toukach.employeeservice.EmployeeTestData;
import by.toukach.employeeservice.config.ApplicationProperty;
import by.toukach.employeeservice.enumiration.DocumentType;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FileManagerImplTest {

  private static final String PRINT_OUT_DIRECTORY = "file";

  @InjectMocks
  private FileManagerImpl fileManager;

  @Mock
  private ApplicationProperty applicationProperty;

  private final Path testPdfDirectoryPath = Paths.get(PRINT_OUT_DIRECTORY);
  private final File pdfOutDirectory = new File(testPdfDirectoryPath.toString());

  @AfterEach
  public void cleanUp() {
    Arrays.stream(pdfOutDirectory.listFiles())
        .forEach(File::delete);
    pdfOutDirectory.delete();
  }

  @Test
  public void createFileTestShouldCreateFile() {
    // given
    ByteArrayOutputStream data = new ByteArrayOutputStream();

    when(applicationProperty.getPrintOutDirectory())
        .thenReturn(PRINT_OUT_DIRECTORY);

    // when
    fileManager.createFile(data, DocumentType.PDF, EmployeeTestData.FILE_NAME);

    // then
    File[] actual = pdfOutDirectory.listFiles();

    assertThat(actual)
        .hasSize(1);
    assertThat(actual[0].getName())
        .contains(DocumentType.PDF.getValue());
  }
}
