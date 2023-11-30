package by.toukach.employeeservice.service.document;

import by.toukach.employeeservice.enumiration.DocumentType;
import java.io.ByteArrayOutputStream;

/**
 * Интерфейс для работы файлами.
 */
public interface FileManager {

  void createFile(ByteArrayOutputStream documentOutputStream, DocumentType documentType,
      String fileName);
}
