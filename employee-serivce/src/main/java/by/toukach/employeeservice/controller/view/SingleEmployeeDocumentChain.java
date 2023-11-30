package by.toukach.employeeservice.controller.view;

import by.toukach.employeeservice.enumiration.DocumentType;
import by.toukach.employeeservice.service.document.DocumentHandler;

/**
 * Класс для вывода формы по созданию документа по сотруднику.
 */
public class SingleEmployeeDocumentChain extends DocumentViewChain {

  private final Long employeeId;

  public SingleEmployeeDocumentChain(Long employeeId) {
    this.employeeId = employeeId;
  }

  @Override
  public void handle() {
    DocumentHandler documentHandler = getDocumentHandler();
    documentHandler.handle(employeeId, DocumentType.PDF);

    setNextViewChain(new ActionListViewChain());
  }
}
