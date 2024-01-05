package by.toukach.employeeservice.service.document.impl;

import by.toukach.employeeservice.enumiration.DocumentType;
import by.toukach.employeeservice.service.document.DocumentService;
import by.toukach.employeeservice.service.document.DocumentServiceFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Класс, представляющий фабрику для создания DocumentService.
 */
@Service
public class DocumentServiceFactoryImpl implements DocumentServiceFactory {

  private final Map<DocumentType, DocumentService> documentServiceMap;

  public DocumentServiceFactoryImpl(List<DocumentService> documentServices) {
    documentServiceMap = documentServices.stream()
        .collect(Collectors.toMap(DocumentService::getDocumentType, d -> d));
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
}
