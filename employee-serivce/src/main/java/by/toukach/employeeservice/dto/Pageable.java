package by.toukach.employeeservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * Класс для определения пагинации.
 */
@Data
@Builder
@FieldNameConstants
public class Pageable {

  private int pageNumber;
  private int pageSize;
}
