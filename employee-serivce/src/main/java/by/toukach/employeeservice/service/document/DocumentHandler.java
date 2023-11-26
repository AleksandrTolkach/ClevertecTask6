package by.toukach.employeeservice.service.document;

import by.toukach.employeeservice.enumiration.DocumentType;
import java.io.ByteArrayOutputStream;

/**
 * Интерфейс для обработки запросов на создание документа по сотрудникам.
 */
public interface DocumentHandler {

  ByteArrayOutputStream handle(DocumentType documentType);

  ByteArrayOutputStream handle(Long id, DocumentType documentType);
}
