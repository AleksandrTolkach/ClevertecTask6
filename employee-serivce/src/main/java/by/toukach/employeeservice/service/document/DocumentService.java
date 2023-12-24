package by.toukach.employeeservice.service.document;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Класс для создания документа.
 */
public interface DocumentService {

  <I> ByteArrayOutputStream createDocumentFromSingleObject(I item);

  <I> ByteArrayOutputStream createDocumentFromObjectList(List<I> itemList, Class<I> type);
}
