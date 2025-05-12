package ma.agilisys.devis.services.impl;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.agilisys.devis.models.Devis;
import ma.agilisys.devis.models.DevisLigne;
import ma.agilisys.devis.repositories.DevisRepository;
import ma.agilisys.devis.services.DevisPdfGenerator;
import ma.agilisys.devis.utils.Constants;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class DevisPdfGeneratorImpl implements DevisPdfGenerator {
    private static final Font TITLE_FONT = new Font(Font.HELVETICA, 18, Font.BOLD);
    private static final Font SECTION_FONT = new Font(Font.HELVETICA, 14, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.HELVETICA, 12);

    private final DevisRepository devisRepository;

    @Override
    public byte[] generateDevisPdf(Long devisId) throws DocumentException {
        Devis devis = devisRepository.findByIdWithDetails(devisId)
                .orElseThrow(() -> new EntityNotFoundException("Devis non trouvé"));
        if (!devis.getStatut().equals(Constants.APPROVED_STATUS))
            throw new RuntimeException("le devis avec " + devis.getId() + "n'est pas valide");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();
        addHeader(document, devis);
        addClientInfo(document, devis);
        addPrestationsTable(document, devis);
        addMetaContent(document, devis);
        addFooter(document, devis);
        document.close();
        return baos.toByteArray();
    }

    @Override
    public void addHeader(Document document, Devis devis) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 1});
        // Cellule de gauche (Logo Agilisys)
        PdfPCell leftCell = new PdfPCell();
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.addElement(new Paragraph("AGILISYS", TITLE_FONT));
        leftCell.addElement(new Paragraph("Votre partenaire digital", NORMAL_FONT));
        table.addCell(leftCell);
        // Cellule de droite (Infos devis)
        PdfPCell rightCell = new PdfPCell();
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        rightCell.addElement(new Paragraph("DEVIS N°: " + devis.getNumero(), NORMAL_FONT));
        rightCell.addElement(new Paragraph("Date: " + formatDate(devis.getDateCreation()), NORMAL_FONT));
        rightCell.addElement(new Paragraph("Statut: " + devis.getStatut(), NORMAL_FONT));
        table.addCell(rightCell);
        document.add(table);
        document.add(new Paragraph("\n"));
    }

    @Override
    public void addClientInfo(Document document, Devis devis) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        addClientCell(table, "Client:", devis.getClient().getNom());
        addClientCell(table, "ICE:", devis.getClient().getIce());
        addClientCell(table, "Adresse:", devis.getClient().getAdresse());
        addClientCell(table, "Ville:", devis.getClient().getVille());
        document.add(table);
        document.add(new Paragraph("\n"));
    }

    @Override
    public void addClientCell(PdfPTable table, String label, String value) {
        if (value != null) {
            PdfPCell labelCell = new PdfPCell(new Phrase(label, NORMAL_FONT));
            labelCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(labelCell);
            PdfPCell valueCell = new PdfPCell(new Phrase(value, NORMAL_FONT));
            valueCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(valueCell);
        }
    }

    @Override
    public void addPrestationsTable(Document document, Devis devis) throws DocumentException {
        Paragraph title = new Paragraph("DÉTAIL DES PRESTATIONS", SECTION_FONT);
        title.setSpacingAfter(10f);
        document.add(title);
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 1, 1, 1, 1});
        // En-têtes
        addTableHeader(table, "Description");
        addTableHeader(table, "Quantité");
        addTableHeader(table, "Prix HT");
        addTableHeader(table, "TVA %");
        addTableHeader(table, "Total HT");
        // Lignes
        for (DevisLigne ligne : devis.getLignes()) {
            addTableRow(table, ligne.getDescriptionLibre());
            addTableRow(table, ligne.getQuantite().toString());
            addTableRow(table, formatMoney(ligne.getPrixUnitaireHt()));
            addTableRow(table, ligne.getTvaPct() + "%");
            BigDecimal totalLigne = ligne.getPrixUnitaireHt().multiply(ligne.getQuantite());
            addTableRow(table, formatMoney(totalLigne));
        }
        // Totaux
        PdfPCell totalCell = new PdfPCell(new Phrase("TOTAL HT", NORMAL_FONT));
        totalCell.setColspan(4);
        totalCell.setBorder(Rectangle.NO_BORDER);
        totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(totalCell);
        addTableRow(table, formatMoney(devis.getTotalHt()));
        PdfPCell tvaCell = new PdfPCell(new Phrase("TVA", NORMAL_FONT));
        tvaCell.setColspan(4);
        tvaCell.setBorder(Rectangle.NO_BORDER);
        tvaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(tvaCell);
        BigDecimal totalTva = devis.getTotalTtc().subtract(devis.getTotalHt());
        addTableRow(table, formatMoney(totalTva));
        PdfPCell ttcCell = new PdfPCell(new Phrase("TOTAL TTC", SECTION_FONT));
        ttcCell.setColspan(4);
        ttcCell.setBorder(Rectangle.NO_BORDER);
        ttcCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(ttcCell);
        addTableRow(table, formatMoney(devis.getTotalTtc()), SECTION_FONT);
        document.add(table);
        document.add(new Paragraph("\n"));
    }

    @Override
    public void addTableHeader(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, NORMAL_FONT));
        cell.setBackgroundColor(new Color(220, 220, 220));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    @Override
    public void addTableRow(PdfPTable table, String text) {
        addTableRow(table, text, NORMAL_FONT);
    }

    @Override
    public void addTableRow(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    @Override
    public void addMetaContent(Document document, Devis devis) throws DocumentException {
        if (devis.getMeta() != null) {
            addSection(document, "PÉRIMÈTRE", devis.getMeta().getPerimetre());
            addSection(document, "OFFRE FONCTIONNELLE", devis.getMeta().getOffreFonctionnelle());
            addSection(document, "PLANNING", devis.getMeta().getPlanning());
            addSection(document, "CONDITIONS", devis.getMeta().getConditions());
        }
    }

    @Override
    public void addSection(Document document, String title, String content) throws DocumentException {
        if (content != null && !content.trim().isEmpty()) {
            Paragraph sectionTitle = new Paragraph(title, SECTION_FONT);
            sectionTitle.setSpacingBefore(15f);
            sectionTitle.setSpacingAfter(5f);
            document.add(sectionTitle);
            Paragraph sectionContent = new Paragraph(content, NORMAL_FONT);
            document.add(sectionContent);
        }
    }

    @Override
    public void addFooter(Document document, Devis devis) throws DocumentException {
        Paragraph footer = new Paragraph();
        footer.add(new Chunk("Fait à Casablanca, le ", NORMAL_FONT));
        footer.add(new Chunk(formatDate(ZonedDateTime.now()), NORMAL_FONT));
        footer.add(new Chunk("\nPour AGILISYS", NORMAL_FONT));
        footer.setSpacingBefore(30f);
        document.add(footer);
    }

    @Override
    public String formatDate(ZonedDateTime date) {
        return date != null
                ? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date)
                : "";
    }

    @Override
    public String formatMoney(BigDecimal amount) {
        return amount != null
                ? String.format("%,.2f MAD", amount)
                : "0,00 MAD";
    }
}