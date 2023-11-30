package by.toukach.employeeservice.controller.view;

import by.toukach.employeeservice.service.document.DocumentHandler;
import by.toukach.employeeservice.service.document.impl.EmployeeDocumentHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс для вывода формы по документам в консоль.
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public abstract class DocumentViewChain extends ViewChain {

  private final DocumentHandler documentHandler;

  protected DocumentViewChain() {
    documentHandler = new EmployeeDocumentHandler();
  }
}
