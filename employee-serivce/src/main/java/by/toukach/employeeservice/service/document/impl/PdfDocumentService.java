package by.toukach.employeeservice.service.document.impl;

import by.toukach.employeeservice.config.ApplicationProperty;
import by.toukach.employeeservice.enumiration.DocumentType;
import by.toukach.employeeservice.exception.ExceptionMessage;
import by.toukach.employeeservice.exception.FileException;
import by.toukach.employeeservice.exception.ReflectionException;
import by.toukach.employeeservice.service.document.DocumentService;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Класс для создания PDF документа.
 */
@Service
@RequiredArgsConstructor
public class PdfDocumentService implements DocumentService {

  private final ApplicationProperty applicationProperty;

  /**
   * Метод для создания PDF документа по переданному объекту.
   *
   * @param item объект, по которому будет создан PDF документ.
   * @param <I>  тип объекта.
   * @return ByteArrayOutputStream с созданным документом.
   */
  @Override
  public <I> ByteArrayOutputStream createDocumentFromSingleObject(I item) {
    List<Field> declaredFieldList = List.of(item.getClass().getDeclaredFields());

    PdfPTable table = new PdfPTable(declaredFieldList.size());
    table.setTotalWidth(470);

    createHeader(table, declaredFieldList);
    addRows(table, item);

    return prepareTable(table);
  }

  /**
   * Метод для создания PDF документа из списка объектов.
   *
   * @param itemList список объектов.
   * @param <I>      тип объектов.
   * @return ByteArrayOutputStream с созданным документом.
   */
  @Override
  public <I> ByteArrayOutputStream createDocumentFromObjectList(List<I> itemList, Class<I> type) {
    List<Field> declaredFieldList = List.of(type.getDeclaredFields());

    PdfPTable table = new PdfPTable(declaredFieldList.size());
    table.setTotalWidth(470);

    createHeader(table, declaredFieldList);

    itemList.forEach(item -> addRows(table, item));

    return prepareTable(table);
  }

  /**
   * Метод для получения формата документа, с которым работает сервис.
   *
   * @return запрашиваемый формат.
   */
  @Override
  public DocumentType getDocumentType() {
    return DocumentType.PDF;
  }

  private void createHeader(PdfPTable table, List<Field> declaredFieldList) {
    declaredFieldList.forEach(columnTitle -> {
      PdfPCell columnHeaderCell = new PdfPCell();

      Font cellFont = FontFactory.getFont(applicationProperty.getPrintFontFilePath(),
          BaseFont.IDENTITY_H, true, 10);

      columnHeaderCell.setPhrase(new Phrase(columnTitle.getName(), cellFont));
      table.addCell(columnHeaderCell);
    });
  }

  private <I> void addRows(PdfPTable table, I item) {
    List<Field> declaredFieldList = List.of(item.getClass().getDeclaredFields());

    declaredFieldList.forEach(columnBody -> {
      columnBody.setAccessible(true);

      Object fieldValue;
      try {
        fieldValue = columnBody.get(item);

      } catch (IllegalAccessException e) {
        throw new ReflectionException(
            String.format(ExceptionMessage.FILED_ACCESS, columnBody.getName()), e);
      }

      Font cellFont = FontFactory.getFont(applicationProperty.getPrintFontFilePath(),
          BaseFont.IDENTITY_H, true, 8);

      PdfPCell cell = new PdfPCell(new Phrase(fieldValue.toString(), cellFont));
      table.addCell(cell);
    });
  }

  private ByteArrayOutputStream prepareTable(PdfPTable table) {
    PdfStamper stamper = null;

    try {
      ByteArrayOutputStream result = new ByteArrayOutputStream();

      PdfReader reader = new PdfReader(applicationProperty.getPrintTemplateFilePath());
      stamper = new PdfStamper(reader, result);
      PdfContentByte content = stamper.getOverContent(1);

      table.writeSelectedRows(0, -1, 100, 700, content);

      return result;

    } catch (IOException | DocumentException e) {
      throw new FileException(ExceptionMessage.CREATE_FILE, e);
    } finally {
      if (stamper != null) {
        try {
          stamper.close();
        } catch (IOException | DocumentException e) {
          throw new FileException(ExceptionMessage.CREATE_FILE, e);
        }
      }
    }
  }
}
