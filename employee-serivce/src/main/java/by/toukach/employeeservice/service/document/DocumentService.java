package by.toukach.employeeservice.service.document;

import by.toukach.employeeservice.enumiration.DocumentType;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Класс для создания документа.
 */
public interface DocumentService {

  <I> ByteArrayOutputStream createDocumentFromSingleObject(I item);

  <I> ByteArrayOutputStream createDocumentFromObjectList(List<I> itemList, Class<I> type);

  DocumentType getDocumentType();
}
