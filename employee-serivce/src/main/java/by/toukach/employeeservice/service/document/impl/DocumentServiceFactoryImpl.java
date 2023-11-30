package by.toukach.employeeservice.service.document.impl;

import by.toukach.employeeservice.enumiration.DocumentType;
import by.toukach.employeeservice.service.document.DocumentService;
import by.toukach.employeeservice.service.document.DocumentServiceFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, представляющий фабрику для создания DocumentService.
 */
public class DocumentServiceFactoryImpl implements DocumentServiceFactory {

  private static final DocumentServiceFactory instance = new DocumentServiceFactoryImpl();

  private final Map<DocumentType, DocumentService> documentServiceMap = new HashMap<>();

  private DocumentServiceFactoryImpl() {
    documentServiceMap.put(DocumentType.PDF, new PdfDocumentService());
  }

  /**
   * Метод для получения DocumentService по типу документа.
   *
   * @param documentType тип документа.
   * @return запрашиваемый DocumentService.
   */
  @Override
  public DocumentService getDocumentService(DocumentType documentType) {
    return documentServiceMap.get(documentType);
  }

  /**
   * Метод для получения фабрики.
   *
   * @return запрашиваемая фабрика.
   */
  public static DocumentServiceFactory getInstance() {
    return instance;
  }
}
