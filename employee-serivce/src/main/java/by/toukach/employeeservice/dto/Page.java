package by.toukach.employeeservice.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий страницу с контентом.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page<I> {

  @Builder.Default
  private int pageNumber = 0;
  @Builder.Default
  private int pageSize = 20;
  private int totalPages;
  private int totalElements;
  private List<I> content;

  /**
   * Методя для создания страницы.
   *
   * @param pageable параметры страницы.
   * @param content контент.
   * @param totalElements общее количество элементов.
   * @param <I> тип элемента.
   * @return запрашиваемая страница.
   */
  public static <I> Page<I> of(Pageable pageable, List<I> content, Integer totalElements) {

    int pageNumber = pageable.getPageNumber();
    int pageSize = pageable.getPageSize();
    int totalPages = totalElements == pageSize ? 0 : totalElements / pageSize;

    return Page.<I>builder()
        .pageNumber(pageNumber)
        .pageSize(pageSize)
        .totalPages(totalPages + 1)
        .content(content)
        .totalElements(totalElements)
        .build();
  }
}
