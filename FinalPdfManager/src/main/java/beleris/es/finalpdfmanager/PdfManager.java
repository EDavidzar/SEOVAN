/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beleris.es.finalpdfmanager;

import static beleris.es.finalinformationmanager.MainErrorManager.ShowDError;
import beleris.es.finalprimaryclasses.CLAllObjectList;
import com.itextpdf.text.BadElementException;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.stream.Stream;

/**
 *
 * @author edavid
 */
public class PdfManager {

    private Document document = null;

    /**
     *
     */
    public PdfManager() {
        document = new Document();
    }

    /**
     *
     * @param FilePath
     */
    public PdfManager(String FilePath) {
        PdfWriter writer = null;
        document = new Document();
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(FilePath));
        } catch (DocumentException | FileNotFoundException ex) {
            ShowDError(PdfManager.class.getName() + " " + ex.getLocalizedMessage());
        }
        PageNumeration event = new PageNumeration();
        writer.setPageEvent(event);
    }

    /**
     *
     * @param sText
     * @param tmpfont
     */
    public void PdfWriteText(String sText, com.itextpdf.text.Font tmpfont) {

        Chunk chunk = new Chunk(sText, tmpfont);
        try {
            getDocument().add(new Paragraph(chunk));
        } catch (DocumentException ex) {
            ShowDError(PdfManager.class.getName() + " " + ex.getLocalizedMessage());
        }
    }

    /**
     *
     * @param tmpbi
     */
    public void PdfWriteImage(BufferedImage tmpbi) {
        try {
            //  path = Paths.get(tmpSelectedFile);
            Image img = Image.getInstance(tmpbi, null);

            getDocument().add(img);
        } catch (DocumentException | IOException ex) {
            ShowDError(PdfManager.class.getName() + " " + ex.getLocalizedMessage());
        }

    }

    /**
     *
     * @param tab
     * @param text
     * @param tmpbi
     */
    public void TablAddImage(PdfPTable tab, String text, BufferedImage tmpbi) {
        try {
            //  path = Paths.get(tmpSelectedFile);
            Image img = Image.getInstance(tmpbi, null);

            tab.addCell(img);
            tab.addCell(text);
        } catch (DocumentException | IOException ex) {
            ShowDError(PdfManager.class.getName() + " " + ex.getLocalizedMessage());
        }

    }

    /**
     *
     * @param table
     * @param ColumnHeader1
     * @param ColumnHeader2
     */
    public void addTableHeader(PdfPTable table, String ColumnHeader1, String ColumnHeader2) {
        Stream.of(ColumnHeader1, ColumnHeader2).forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });

    }

    /**
     *
     * @param table
     */
    private void addCustomRows(PdfPTable table) {
        Path path = null;
        try {
            path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());
        } catch (URISyntaxException ex) {
            ShowDError(PdfManager.class.getName() + " " + ex.getLocalizedMessage());
        }
        Image img = null;
        try {
            img = Image.getInstance(path.toAbsolutePath().toString());
            img.scalePercent(10);
        } catch (BadElementException | IOException ex) {
            ShowDError(PdfManager.class.getName() + " " + ex.getLocalizedMessage());
        }

        PdfPCell imageCell = new PdfPCell(img);
        table.addCell(imageCell);

        PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell);

        PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(verticalAlignCell);
    }

    /**
     *
     * @param table
     * @param RowLeft
     * @param RowRight
     */
    public void addRows(PdfPTable table, CLAllObjectList<String> RowLeft, CLAllObjectList<String> RowRight) {
        int indexleft = RowLeft.size();
        int indexright = RowRight.size();

        if (indexleft != indexright) {
            if (indexleft < indexright) {
                indexleft = indexright;
            } else {
                indexright = indexleft;
            }
        }
        for (int idx = 0; idx < indexleft; idx++) {
            String sLeft = RowLeft.get(idx);
            String sRight = RowRight.get(idx);
            if (sLeft == null) {
                sLeft = sRight;
            }
            if (sRight == null) {
                sRight = sLeft;
            }
            table.addCell(sLeft);
            table.addCell(sRight);
        }

    }

    private static void setAbsoluteColumnWidthsInTableWidth(PdfPTable table) {
        try {
            table.setTotalWidth(new float[]{72f, 144f, 216f});
        } catch (DocumentException ex) {
            ShowDError(PdfManager.class.getName() + " " + ex.getLocalizedMessage());
            //  ShowDError(PdfManager.class.getName() + " " + ex.getLocalizedMessage());
        }
        table.setLockedWidth(true);
    }

    private static void setRelativeColumnWidths(PdfPTable table) {
        try {
            table.setWidths(new float[]{5, 5});
        } catch (DocumentException ex) {
            ShowDError(PdfManager.class.getName() + " " + ex.getLocalizedMessage());
        }
        table.setWidthPercentage(80);
    }

    private static void setAbsoluteColumnWidths(PdfPTable table) {
        table.setTotalWidth(500);
        table.setLockedWidth(true);
        float[] columnWidths = {100f, 200f, 200f};
        try {
            table.setWidths(columnWidths);
        } catch (DocumentException ex) {
            ShowDError(PdfManager.class.getName() + " " + ex.getLocalizedMessage());
        }
    }

    /**
     *
     * @param FileInputPath
     * @param FileOutputPath
     * @param UserPass
     * @param OwnerPass
     */
    public void PdFEncryptor(String FileInputPath, String FileOutputPath, String UserPass, String OwnerPass) {
        PdfReader pdfReader;
        try {
            pdfReader = new PdfReader(FileInputPath);
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(FileOutputPath));
            pdfStamper.setEncryption(UserPass.getBytes(), OwnerPass.getBytes(), 0, PdfWriter.ENCRYPTION_AES_256);
            pdfStamper.close();
        } catch (DocumentException | IOException ex) {
            ShowDError(PdfManager.class.getName() + " " + ex.getLocalizedMessage());
        }

    }

    class PageNumeration extends PdfPageEventHelper {

        /**
         * The template with the total number of pages.
         */
        PdfTemplate total;

        private Font normal, normalSmall;
//private Company company;

        public PageNumeration() {
            try {
                this.normal = new Font(BaseFont.createFont(FontFactory.HELVETICA_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED), 8);
                this.normalSmall = new Font(BaseFont.createFont(FontFactory.HELVETICA_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED), 6);
            } catch (Exception ex) {
                ShowDError(PdfManager.class.getName() + " " + ex.getLocalizedMessage());

            }
        }

        /**
         * Creates the PdfTemplate that will hold the total number of pages.
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
         * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onOpenDocument(PdfWriter writer, Document document) {
            total = writer.getDirectContent().createTemplate(30, 12);
        }

        /**
         * Adds a header to every page
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
         * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable table = new PdfPTable(3);
            try {
                table.setWidths(new int[]{24, 24, 2});
                table.getDefaultCell().setFixedHeight(10);
                table.getDefaultCell().setBorder(Rectangle.TOP);
                PdfPCell cell = new PdfPCell();
                cell.setBorder(0);
                cell.setBorderWidthTop(1);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setPhrase(new Phrase("Registro de Actividades de tratamiento de datos personales", normalSmall));
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorder(0);
                cell.setBorderWidthTop(1);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPhrase(new Phrase(String.format("Página %d de", writer.getPageNumber()), normal));
                table.addCell(cell);

                cell = new PdfPCell(Image.getInstance(total));
                cell.setBorder(0);
                cell.setBorderWidthTop(1);
                table.addCell(cell);
                table.setTotalWidth(document.getPageSize().getWidth()
                        - document.leftMargin() - document.rightMargin());
                table.writeSelectedRows(0, -1, document.leftMargin(),
                        document.bottomMargin() - 15, writer.getDirectContent());
            } catch (DocumentException ex) {
                ShowDError(PdfManager.class.getName() + " " + ex.getLocalizedMessage());
            }
        }

        /**
         * Fills out the total number of pages before the document is closed.
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
         * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onCloseDocument(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(writer.getPageNumber() - 1), normal),
                    2, 2, 0);
        }
    }

    /**
     * @return the document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(Document document) {
        this.document = document;
    }

}
