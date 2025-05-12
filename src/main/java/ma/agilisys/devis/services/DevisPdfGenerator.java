package ma.agilisys.devis.services;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import ma.agilisys.devis.models.Devis;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public interface DevisPdfGenerator {
    byte[] generateDevisPdf(Long devisId) throws DocumentException;

    void addHeader(Document document, Devis devis) throws DocumentException;

    void addClientInfo(Document document, Devis devis) throws DocumentException;

    void addClientCell(PdfPTable table, String label, String value);

    void addPrestationsTable(Document document, Devis devis) throws DocumentException;

    void addTableHeader(PdfPTable table, String text);

    void addTableRow(PdfPTable table, String text);

    void addTableRow(PdfPTable table, String text, Font font);

    void addMetaContent(Document document, Devis devis) throws DocumentException;

    void addSection(Document document, String title, String content) throws DocumentException;

    void addFooter(Document document, Devis devis) throws DocumentException;

    String formatDate(ZonedDateTime date);

    String formatMoney(BigDecimal amount);
}
