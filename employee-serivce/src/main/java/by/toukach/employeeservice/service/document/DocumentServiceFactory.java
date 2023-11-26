package by.toukach.employeeservice.service.document;

import by.toukach.employeeservice.enumiration.DocumentType;

/**
 * Интерфейс, представляющий фабрику для создания DocumentService.
 */
public interface DocumentServiceFactory {

  DocumentService getDocumentService(DocumentType documentType);
}
