package by.toukach.employeeservice.service.document.impl;

import by.toukach.employeeservice.enumiration.DocumentType;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.exception.FileException;
import by.toukach.employeeservice.service.document.FileManager;
import by.toukach.employeeservice.util.property.ApplicationProperties;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс для работы с файлами.
 */
public class FileManagerImpl implements FileManager {

  private static final FileManagerImpl instance = new FileManagerImpl();

  private final Path fileDirectoryPath = Paths.get(ApplicationProperties.PRINT_OUT_DIRECTORY);

  private FileManagerImpl() {
    File pdfOutDirectory = new File(fileDirectoryPath.toString());
    if (!pdfOutDirectory.exists()) {
      pdfOutDirectory.mkdir();
    }
  }

  /**
   * Метод для создания файла.
   *
   * @param data данные для сохранения.
   * @param documentType формат файла.
   * @param fileName название файла.
   */
  @Override
  public void createFile(ByteArrayOutputStream data, DocumentType documentType,
      String fileName) {

    Path filePath = Paths.get(fileName + documentType.getValue());

    filePath = fileDirectoryPath.resolve(filePath);
    try (FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile())) {

      data.writeTo(fileOutputStream);

    } catch (IOException e) {
      throw new FileException(ExceptionMessage.CREATE_FILE, e);
    }
  }

  /**
   * Метод для получения объекта FileManager.
   *
   * @return запрашиваемый FileManager.
   */
  public static FileManager getInstance() {
    return instance;
  }
}
