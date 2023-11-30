package by.toukach.employeeservice.enumiration;

import lombok.Getter;

/**
 * Перечисление типов документов.
 */
@Getter
public enum DocumentType {

  PDF(".pdf");

  private String value;

  DocumentType(String value) {
    this.value = value;
  }
}
