package by.toukach.employeeservice.controller.view;

import by.toukach.employeeservice.dto.Pageable;
import by.toukach.employeeservice.enumiration.DocumentType;
import by.toukach.employeeservice.service.document.DocumentHandler;

/**
 * Класс для вывода формы по печати документа.
 */
public class ListEmployeeDocumentChain extends DocumentViewChain {

  @Override
  public void handle() {
    Pageable pageable = Pageable.builder()
        .pageNumber(0)
        .pageSize(20)
        .build();

    DocumentHandler documentHandler = getDocumentHandler();
    documentHandler.handle(pageable, DocumentType.PDF);

    setNextViewChain(new ActionListViewChain());
  }
}
