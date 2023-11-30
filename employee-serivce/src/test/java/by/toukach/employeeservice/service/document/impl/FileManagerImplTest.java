package by.toukach.employeeservice.service.document.impl;

import static org.assertj.core.api.Assertions.assertThat;

import by.toukach.employeeservice.EmployeeTestData;
import by.toukach.employeeservice.enumiration.DocumentType;
import by.toukach.employeeservice.service.document.FileManager;
import by.toukach.employeeservice.util.property.ApplicationProperties;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileManagerImplTest {

  private FileManager fileManager;
  private final Path testPdfDirectoryPath = Paths.get(ApplicationProperties.PRINT_OUT_DIRECTORY);
  private final File pdfOutDirectory = new File(testPdfDirectoryPath.toString());

  @BeforeEach
  public void setUp() throws NoSuchMethodException, InvocationTargetException,
      InstantiationException, IllegalAccessException {

    Constructor<FileManagerImpl> privateConstructor =
        FileManagerImpl.class.getDeclaredConstructor();
    privateConstructor.setAccessible(true);

    fileManager = privateConstructor.newInstance();
  }

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
