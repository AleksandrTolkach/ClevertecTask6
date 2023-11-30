package by.toukach.employeeservice.controller.view;

import by.toukach.employeeservice.enumiration.DocumentType;
import by.toukach.employeeservice.service.document.DocumentHandler;

/**
 * Класс для вывода формы по печати документа.
 */
public class ListEmployeeDocumentChain extends DocumentViewChain {

  @Override
  public void handle() {
    DocumentHandler documentHandler = getDocumentHandler();
    documentHandler.handle(DocumentType.PDF);

    setNextViewChain(new ActionListViewChain());
  }
}
