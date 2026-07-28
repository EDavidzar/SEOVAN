/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seovan.es.pdfutils;

import beleris.es.finaldbmanager.FDBMan;
import static beleris.es.finalinformationmanager.MainErrorManager.ShowDError;
import beleris.es.finalprimaryclasses.CLAllObjectList;
import com.itextpdf.text.BadElementException;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
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
import java.awt.Desktop;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.stream.Stream;
import seovan.es.programconfig.PConfigManager;
import static seovan.es.programconfig.PConfigManager.PConfig;

/**
 *
 * @author edavid
 */
public class SoevanPDFManager {

    private Document document = null;

    /**
     *
     */
    protected CLAllObjectList<CLAllObjectList<String>> ACTUALITY;

    /**
     *
     */
    protected CLAllObjectList<CLAllObjectList<String>> APLICUTIL;

    /**
     *
     */
    protected CLAllObjectList<CLAllObjectList<String>> AUTHOR;

    /**
     *
     */
    protected CLAllObjectList<CLAllObjectList<String>> AUTHORITY;

    /**
     *
     */
    protected CLAllObjectList<CLAllObjectList<String>> CONTENT;
    FDBMan DBMSAP;

    /**
     *
     */
    protected FDBMan DBMSEV;

    /**
     *
     */
    protected CLAllObjectList<CLAllObjectList<String>> EVAL;

    /**
     *
     */
    protected CLAllObjectList<CLAllObjectList<String>> IDENT;

    /**
     *
     */
    protected int IndexDataTotal = 0;

    /**
     *
     */
    protected CLAllObjectList<String> ListAccessTable_ent;

    /**
     *
     */
    protected CLAllObjectList<String> ListFormatMediumTable_ent;

    /**
     *
     */
    protected CLAllObjectList<String> ListGeoCoverTable_ent;

    /**
     *
     */
    protected CLAllObjectList<String> ListSourceOriginTable_ent;

    /**
     *
     */
    protected CLAllObjectList<String> ListSourcecontents_ent;

    /**
     *
     */
    protected CLAllObjectList<String> ListSourcelevel_ent;

    /**
     *
     */
    protected CLAllObjectList<CLAllObjectList<String>> OBJETIVITY;

    /**
     *
     */
    protected CLAllObjectList<CLAllObjectList<String>> PUBLICATION;

    /**
     *
     */
    protected CLAllObjectList<CLAllObjectList<String>> QUALITY;

    /**
     *
     */
    protected CLAllObjectList<CLAllObjectList<String>> RELEVANCE;

    /**
     *
     */
    protected CLAllObjectList<CLAllObjectList<String>> Records_List;

    /**
     *
     */
    protected CLAllObjectList<CLAllObjectList<String>> TEMCLASS;

    /**
     *
     */
    protected CLAllObjectList<CLAllObjectList<String>> THEWEB;

    /**
     *
     */
    protected CLAllObjectList<CLAllObjectList<String>> TIP;

    /**
     *
     */
    protected PdfPTable analtable;

    /**
     *
     */
    protected PdfPTable evaltable;
    String ThePath="";

    /**
     *
     */
    public SoevanPDFManager() {
        document = new Document();
    }

    /**
     *
     * @param FilePath
     */
    public SoevanPDFManager(String FilePath) {
        DBMSAP = new FDBMan("jdbc:mysql://localhost:3306/desidaniespsources", PConfig.getConfPassDB(), "desidaniespsources", PConfig.getConfUserDB(), PConfig.getDatabaseManagerinUse());
        DBMSEV = new FDBMan("jdbc:mysql://localhost:3306/sourcesevalprotocol", PConfig.getConfPassDB(), "sourcesevalprotocol", PConfig.getConfUserDB(), PConfig.getDatabaseManagerinUse());
        PdfWriter writer = null;
        ThePath=FilePath;
        
        document = new Document();
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(FilePath));
        } catch (DocumentException | FileNotFoundException ex) {
            ShowDError(SoevanPDFManager.class.getName() + " " + ex.getLocalizedMessage());
        }
        PDFNumerationPages event = new PDFNumerationPages();
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
            ShowDError(SoevanPDFManager.class.getName() + " " + ex.getLocalizedMessage());
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
            ShowDError(SoevanPDFManager.class.getName() + " " + ex.getLocalizedMessage());
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
            ShowDError(SoevanPDFManager.class.getName() + " " + ex.getLocalizedMessage());
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

    private void addCustomRows(PdfPTable table) {
        Path path = null;
        try {
            path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());
        } catch (URISyntaxException ex) {
            ShowDError(SoevanPDFManager.class.getName() + " " + ex.getLocalizedMessage());
        }
        Image img = null;
        try {
            img = Image.getInstance(path.toAbsolutePath().toString());
            img.scalePercent(10);
        } catch (BadElementException | IOException ex) {
            ShowDError(SoevanPDFManager.class.getName() + " " + ex.getLocalizedMessage());
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
    public void addHighlitedRows(PdfPTable table, CLAllObjectList<String> RowLeft, CLAllObjectList<String> RowRight) {
        int indexleft = RowLeft.size();
        int indexright = RowRight.size();

        if (indexleft != indexright) {
            if (indexleft < indexright) {
                indexleft = indexright;
            } else {
                indexright = indexleft;
            }
        }
        float miniheaderFontSize = 12f;
        for (int idx = 0; idx < indexleft; idx++) {
            String sLeft = RowLeft.get(idx);
            String sRight = RowRight.get(idx);
            if (sLeft == null) {
                sLeft = sRight;
            }
            if (sRight == null) {
                sRight = sLeft;
            }
            //PdfPCell Leftcell = new PdfPCell(new Paragraph(sLeft,helveticaBoldFont,12f,BaseColor.BLACK)));            
            PdfPCell Leftcell = new PdfPCell(new Paragraph(sLeft, FontFactory.getFont(FontFactory.HELVETICA_BOLD, miniheaderFontSize, Font.NORMAL, BaseColor.BLACK)));
            PdfPCell Rightcell = new PdfPCell(new Paragraph(sRight, FontFactory.getFont(FontFactory.HELVETICA_BOLD, miniheaderFontSize, Font.NORMAL, BaseColor.BLACK)));
            Rightcell.setBackgroundColor(new BaseColor(242, 242, 242));
            Rightcell.setBorderWidth(1);
            Leftcell.setBackgroundColor(new BaseColor(242, 242, 242));
            Leftcell.setBorderWidth(1);
            table.addCell(Leftcell);
            table.addCell(Rightcell);
        }

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
            ShowDError(SoevanPDFManager.class.getName() + " " + ex.getLocalizedMessage());
            //  ShowDError(SoevanPDFManager.class.getName() + " " + ex.getLocalizedMessage());
        }
        table.setLockedWidth(true);
    }

    private static void setRelativeColumnWidths(PdfPTable table) {
        try {
            table.setWidths(new float[]{5, 5});
        } catch (DocumentException ex) {
            ShowDError(SoevanPDFManager.class.getName() + " " + ex.getLocalizedMessage());
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
            ShowDError(SoevanPDFManager.class.getName() + " " + ex.getLocalizedMessage());
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
            ShowDError(SoevanPDFManager.class.getName() + " " + ex.getLocalizedMessage());
        }

    }

    void CloseDocument() {
        getDocument().close();
        OpenPdfDocument(ThePath);
    }

    /**
     *
     */
    protected void CloseTable() {
        //no hacer nada
    }

    /**
     *
     */
    protected void CreateAnalisysTableHeader() {
        analtable = new PdfPTable(2);
        addTableHeader(analtable, "An\u00e1lisis de fuentes de informaci\u00f3n especializadas", "Datos");
    }

    /**
     *
     */
    protected void CreateEvalTableHeader() {
        evaltable = new PdfPTable(2);
        addTableHeader(evaltable, "Evaluaci\u00f3n de fuentes de informaci\u00f3n especializadas", "Puntuaciones");
    }

    /**
     *
     */
    public void CreatePDFDocuments() {
        LoadInformationData();
        OpenPDFDocument();
        LoadGeneralRecordsData();

        for (int tmpitem = 0; tmpitem < IndexDataTotal; tmpitem++) {
            CreateAnalisysTableHeader();
            FillAnalisysTable(tmpitem);
            CloseTable();
            InsertNewPage();
            CreateEvalTableHeader();
            FillEvaluationTable(tmpitem);
            CloseTable();
            InsertTablesInDoc();

        }
        CloseDocument();
    }

    void InsertNewPage() {

        getDocument().newPage();

    }

    void FillAnalisysTable(int titem) {
        addAnalisysRows(analtable, titem);
    }

    void FillEvaluationTable(int trow) {
        addEvalRows(evaltable, trow);
    }

    /**
     *
     */
    protected void InsertTablesInDoc() {
        Paragraph pg = new Paragraph();
        pg.setSpacingBefore(10);
        try {
            getDocument().add(pg);
        } catch (DocumentException ex) {
            ShowDError(this.getClass().getName() + " " + ex.getLocalizedMessage());
        }
        try {
            getDocument().add(analtable);
        } catch (DocumentException ex) {
            ShowDError(this.getClass().getName() + " " + ex.getLocalizedMessage());
        }
        try {
            getDocument().add(pg);
        } catch (DocumentException ex) {
            ShowDError(this.getClass().getName() + " " + ex.getLocalizedMessage());
        }
        InsertNewPage();
        try {
            getDocument().add(new Chapter(1));
        } catch (DocumentException ex) {
            ShowDError(this.getClass().getName() + " " + ex.getLocalizedMessage());
        }
        try {
            getDocument().add(evaltable);
        } catch (DocumentException ex) {
            ShowDError(this.getClass().getName() + " " + ex.getLocalizedMessage());
        }
        try {
            getDocument().add(pg);
        } catch (DocumentException ex) {
            ShowDError(this.getClass().getName() + " " + ex.getLocalizedMessage());
        }
        try {
            getDocument().add(new Chapter(2));
        } catch (DocumentException ex) {
            ShowDError(this.getClass().getName() + " " + ex.getLocalizedMessage());
        }
    }

    /**
     *
     */
    protected void LoadGeneralRecordsData() {
        DBMSAP = new FDBMan("jdbc:mysql://localhost:3306/desidaniespsources", PConfig.getConfPassDB(), "desidaniespsources", PConfig.getConfUserDB(), PConfigManager.PConfig.getDatabaseManagerinUse());
        CLAllObjectList<String> ColumnsName = DBMSAP.GetColumnsName("elements_record");
        Records_List = DBMSAP.LoadList_Table("desidaniespsources", "elements_record", ColumnsName);
        IndexDataTotal = Records_List.get(0).size();
        DBMSEV = new FDBMan("jdbc:mysql://localhost:3306/sourcesevalprotocol", PConfig.getConfPassDB(), "sourcesevalprotocol", PConfig.getConfUserDB(), PConfigManager.PConfig.getDatabaseManagerinUse());
    }

    void LoadInformationData() {
        LoadingGeneralLists();
        LoadingAnalisysLists();
        LoadingEvaluationLists();
    }

    /**
     *
     */
    protected void LoadingAnalisysLists() {
        // ANALISYS DATA passed to add analisys rows
    }

    /**
     *
     */
    protected void LoadingEvaluationLists() {
        // EVALUATION DATA passed to add eval rows

    }

    /**
     *
     */
    protected void LoadingGeneralLists() {
        //GENERAL LISTS
        ListSourcelevel_ent = DBMSAP.LoadList("list_sourcelevel", "sourcelevelitem");
        ListSourceOriginTable_ent = DBMSAP.LoadList("list_source_origin_table", "originitem");
        ListGeoCoverTable_ent = DBMSAP.LoadList("list_geo_cover_table", "geographicvoveritem");
        ListFormatMediumTable_ent = DBMSAP.LoadList("list_format_medium_table", "format_mediumitem");
        ListSourcecontents_ent = DBMSAP.LoadList("list_sourcecontents", "sourcecontentsitem");
        ListAccessTable_ent = DBMSAP.LoadList("list_access_table", "access_item");
    }

    void OpenPDFDocument() {
        String title = "Análisis, Identificación y Descripción de fuentes de información especializadas.Protocolo de evaluación de fuentes de información especializadas";
        getDocument().open();
        getDocument().addTitle(title);
        getDocument().addSubject("Análisis, Identificación y Descripción de fuentes de información especializadas.Protocolo de evaluación de fuentes de información especializadas");
        getDocument().addKeywords("Análisis, Identificación,Descripción,fuentes de información especializadas,protocolo,evaluación,fuentes de información,especializadas,PDF");
        getDocument().addAuthor("SEOVAN (c) Emilio David Diaus L\u00f3pez 2024-2026");
        getDocument().addCreator("SEOVAN (c) Emilio David Diaus L\u00f3pez 2024-2026");
    }

    /**
     *
     * @param table
     * @param irowidx
     */
    public void addAnalisysRows(PdfPTable table, int irowidx) {
        int TMPirowidx = irowidx;
        CLAllObjectList<String> RowLeft = new CLAllObjectList<>();
        CLAllObjectList<String> RowRight = new CLAllObjectList<>();
        CLAllObjectList<String> ColumnsName = new CLAllObjectList<>();
        ColumnsName = DBMSAP.GetColumnsName("identification");
        IDENT = DBMSAP.LoadList_Table("desidaniespsources", "identification", ColumnsName);
        RowLeft.add("Identificaci\u00f3n");
        RowRight.add("--------");
        RowLeft.add("Denominaci\u00f3n principal");
        RowRight.add("----");
        addHighlitedRows(table, RowLeft, RowRight);
        RowLeft.clear();
        RowRight.clear();
        RowLeft.add("T\u00edtulo propiamente dicho");
        RowRight.add(IDENT.get(1).get(TMPirowidx));
        RowLeft.add("Subt\u00edtulo u otra parte del t\u00edtulo");
        RowRight.add(IDENT.get(2).get(TMPirowidx));
        RowLeft.add("Forma completa del nombre");
        RowRight.add(IDENT.get(3).get(TMPirowidx));
        RowLeft.add("Otras denominaciones");
        RowRight.add("----");
        RowLeft.add("T\u00edtulo alternativo al t\u00edtulo principal");
        RowRight.add(IDENT.get(4).get(TMPirowidx));
        RowLeft.add("Subt\u00edtulo alternativo o t\u00edtulo paralelo");
        RowRight.add(IDENT.get(5).get(TMPirowidx));
        RowLeft.add("Otra forma aceptada del nombre");
        RowRight.add(IDENT.get(6).get(TMPirowidx));
        RowLeft.add("Direcci\u00f3n URL");
        RowRight.add("----");
        RowLeft.add("Direcci\u00f3n de dns o dominio");
        RowRight.add(IDENT.get(7).get(TMPirowidx));
        RowLeft.add("Direccion url completa");
        RowRight.add(IDENT.get(8).get(TMPirowidx));
        RowLeft.add("Permalink");
        RowRight.add(IDENT.get(9).get(TMPirowidx));
        RowLeft.add("Canal de sindicaci\u00f3n");
        RowRight.add("----");
        RowLeft.add("Direcci\u00f3n del canal");
        RowRight.add(IDENT.get(10).get(TMPirowidx));
        RowLeft.add("Fechas");
        RowRight.add("----");
        RowLeft.add("Fechas de publicaci\u00f3n");
        RowRight.add(IDENT.get(11).get(TMPirowidx));
        RowLeft.add("Fechas de actualizaci\u00f3n");
        RowRight.add(IDENT.get(12).get(TMPirowidx));
        RowLeft.add("Derechos");
        RowRight.add("----");
        RowLeft.add("Derechos legales");
        RowRight.add(IDENT.get(13).get(TMPirowidx));
        RowLeft.add("Lenguajes");
        RowRight.add("----");
        RowLeft.add("Idioma");
        RowRight.add(IDENT.get(14).get(TMPirowidx));
        addRows(table, RowLeft, RowRight);
        IDENT.clear();
        RowLeft.clear();
        RowRight.clear();
        ColumnsName = DBMSAP.GetColumnsName("tipification");
        TIP = DBMSAP.LoadList_Table("desidaniespsources", "tipification", ColumnsName);
        RowLeft.add("Tipificaci\u00f3n");
        RowRight.add("--------");
        addHighlitedRows(table, RowLeft, RowRight);
        RowLeft.clear();
        RowRight.clear();
        RowLeft.add("Seg\u00fan el nivel");
        RowRight.add(ListSourcelevel_ent.get(Integer.parseInt(TIP.get(1).get(TMPirowidx))-1));
        //ListSourcelevel_ent.clear();
        RowLeft.add("Seg\u00fan el contenido");
        RowRight.add(ListSourcecontents_ent.get(Integer.parseInt(TIP.get(2).get(TMPirowidx))-1));
        //ListSourcecontents_ent.clear();
        RowLeft.add("Seg\u00fan el origen");
        RowRight.add(ListSourceOriginTable_ent.get(Integer.parseInt(TIP.get(3).get(TMPirowidx))-1));
        //ListSourcecontents_ent.clear();
        RowLeft.add("Seg\u00fan el acceso");
        RowRight.add(ListAccessTable_ent.get(Integer.parseInt(TIP.get(4).get(TMPirowidx))-1));
        //ListAccessTable_ent.clear();
        RowLeft.add("Seg\u00fan la cobertura geogr\u00e1fica");
        RowRight.add(ListGeoCoverTable_ent.get(Integer.parseInt(TIP.get(5).get(TMPirowidx))-1));
        //ListGeoCoverTable_ent.clear();
        RowLeft.add("Seg\u00fan la cobertura temporal");
        RowRight.add(TIP.get(6).get(TMPirowidx));
        RowLeft.add("Seg\u00fan el soporte o medio");
        RowRight.add(ListFormatMediumTable_ent.get(Integer.parseInt(TIP.get(7).get(TMPirowidx))-1));
        //ListFormatMediumTable_ent.clear();
        addRows(table, RowLeft, RowRight);
        TIP.clear();
        RowLeft.clear();
        RowRight.clear();
        ColumnsName = DBMSAP.GetColumnsName("authorities");
        AUTHOR = DBMSAP.LoadList_Table("desidaniespsources", "authorities", ColumnsName);
        RowLeft.add("Autoridades");
        RowRight.add("--------");
        addHighlitedRows(table, RowLeft, RowRight);
        RowLeft.clear();
        RowRight.clear();
        RowLeft.add("Autoridad principal");
        RowRight.add(AUTHOR.get(1).get(TMPirowidx));
        RowLeft.add("Otras Autoridades");
        RowRight.add(AUTHOR.get(2).get(TMPirowidx));
        addRows(table, RowLeft, RowRight);
        AUTHOR.clear();
        RowLeft.clear();
        RowRight.clear();
        ColumnsName = DBMSAP.GetColumnsName("temclass");
        TEMCLASS = DBMSAP.LoadList_Table("desidaniespsources", "temclass", ColumnsName);
        RowLeft.add("Clasificaci\u00f3n tem\u00e1tica");
        RowRight.add("--------");
        addHighlitedRows(table, RowLeft, RowRight);
        RowLeft.clear();
        RowRight.clear();
        RowLeft.add("Clasificaci\u00f3n general");
        RowRight.add(TEMCLASS.get(1).get(TMPirowidx));
        RowLeft.add("Clasificaci\u00f3n especializada");
        RowRight.add(TEMCLASS.get(2).get(TMPirowidx));
        RowLeft.add("descriptores, palabras clave");
        RowRight.add(TEMCLASS.get(3).get(TMPirowidx));
        addRows(table, RowLeft, RowRight);
        TEMCLASS.clear();
        RowLeft.clear();
        RowRight.clear();
        ColumnsName = DBMSAP.GetColumnsName("contents");
        CONTENT = DBMSAP.LoadList_Table("desidaniespsources", "contents", ColumnsName);
        RowLeft.add("Contenidos");
        RowRight.add("--------");
        addHighlitedRows(table, RowLeft, RowRight);
        RowLeft.clear();
        RowRight.clear();
        RowLeft.add("Descripci\u00f3n general");
        RowRight.add(CONTENT.get(1).get(TMPirowidx));
        RowLeft.add("Relaci\u00f3n de contenidos");
        RowRight.add(CONTENT.get(2).get(TMPirowidx));
        RowLeft.add("Extractos significativos");
        RowRight.add(CONTENT.get(3).get(TMPirowidx));
        addRows(table, RowLeft, RowRight);
        CONTENT.clear();
        RowLeft.clear();
        RowRight.clear();
        ColumnsName = DBMSAP.GetColumnsName("sourceapplication");
        APLICUTIL = DBMSAP.LoadList_Table("desidaniespsources", "sourceapplication", ColumnsName);
        RowLeft.add("Utlidades de aplicaci\u00f3n");
        RowRight.add("--------");
        addHighlitedRows(table, RowLeft, RowRight);
        RowLeft.clear();
        RowRight.clear();
        RowLeft.add("Utlidades de aplicaci\u00f3n");
        RowRight.add(APLICUTIL.get(1).get(TMPirowidx));
        addRows(table, RowLeft, RowRight);
        APLICUTIL.clear();
        RowLeft.clear();
        RowRight.clear();
        ColumnsName = DBMSAP.GetColumnsName("evaluation");
        EVAL = DBMSAP.LoadList_Table("desidaniespsources", "evaluation", ColumnsName);
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add("--------");
        addHighlitedRows(table, RowLeft, RowRight);
        RowLeft.clear();
        RowRight.clear();
        RowLeft.add("Puntuaci\u00f3n de la fuente de informaci\u00f3n");
        RowRight.add(EVAL.get(1).get(TMPirowidx));
        addRows(table, RowLeft, RowRight);
        EVAL.clear();
        ColumnsName.clear();
    }

    /**
     *
     * @param tmpevaltable
     * @param trow
     */
    protected void addEvalRows(PdfPTable tmpevaltable, int trow) {
        int TMPirowidx = trow;
        CLAllObjectList<String> RowLeft = new CLAllObjectList<>();
        CLAllObjectList<String> RowRight = new CLAllObjectList<>();
        CLAllObjectList<String> ColumnsName = new CLAllObjectList<>();

        ColumnsName = DBMSEV.GetColumnsName("authority");
        AUTHORITY = DBMSEV.LoadList_Table("sourcesevalprotocol", "authority", ColumnsName);
        RowLeft.add("Autoridad");
        RowRight.add("--------");
        addHighlitedRows(tmpevaltable, RowLeft, RowRight);
        RowLeft.clear();
        RowRight.clear();
        RowLeft.add("Autor\u00eda");
        RowRight.add("----");
        RowLeft.add("Denominaci\u00f3n completa del autor\nFirma cient\u00edfica reconocida");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(AUTHORITY.get(1).get(TMPirowidx));
        RowLeft.add("Autor reconocido");
        RowRight.add("----");
        RowLeft.add("Forma parte o ha colaborado en un grupo de investigaci\u00f3n\nEl autor est\u00e1 presente o ha sido citado en otras publicaciones\n\u00cdndice H\nN\u00famero de citas recibidas\nN\u00famero de publicaciones\nPresencia en Congresos y Seminarios");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(AUTHORITY.get(2).get(TMPirowidx));
        RowLeft.add("Filiaci\u00f3n del autor");
        RowRight.add("----");
        RowLeft.add("El autor trabaja en una organizaci\u00f3n o instituci\u00f3n cient\u00edfica reconocida\n(Universidad, Centro de investigaci\u00f3n, Laboratorio, Departamento de investigaci\u00f3n\nEl autor dispone de portfolio o sitio web de investigaci\u00f3n");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(AUTHORITY.get(3).get(TMPirowidx));
        addRows(tmpevaltable, RowLeft, RowRight);
        AUTHORITY.clear();
        RowLeft.clear();
        RowRight.clear();
        ColumnsName = DBMSEV.GetColumnsName("publication");
        PUBLICATION = DBMSEV.LoadList_Table("sourcesevalprotocol", "publication", ColumnsName);
        RowLeft.add("Publicaci\u00f3n");
        RowRight.add("--------");
        addHighlitedRows(tmpevaltable, RowLeft, RowRight);
        RowLeft.clear();
        RowRight.clear();
        RowLeft.add("Editor reconocido");
        RowRight.add("----");
        RowLeft.add("La autoridad responsable de la fuente de informaci\u00f3n es reconocida en el \u00e1mbito de la investigaci\u00f3n en el \u00e1rea de conocimiento pertinente");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(PUBLICATION.get(1).get(TMPirowidx));
        RowLeft.add("Calidad de las publicaciones");
        RowRight.add("----");
        RowLeft.add("La fuente de informaci\u00f3n se somete a procesos de evaluaci\u00f3n por pares\ndispone de normas de estilo y publicaci\u00f3n de contenidos\nexiste normalizaci\u00f3n en la presentaci\u00f3n de la informaci\u00f3n");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(PUBLICATION.get(2).get(TMPirowidx));
        addRows(tmpevaltable, RowLeft, RowRight);
        PUBLICATION.clear();
        RowLeft.clear();
        RowRight.clear();
        ColumnsName = DBMSEV.GetColumnsName("objetivity");
        OBJETIVITY = DBMSEV.LoadList_Table("sourcesevalprotocol", "objetivity", ColumnsName);
        RowLeft.add("Objetividad");
        RowRight.add("--------");
        addHighlitedRows(tmpevaltable, RowLeft, RowRight);
        RowLeft.clear();
        RowRight.clear();
        RowLeft.add("Claridad y precisi\u00f3n");
        RowRight.add("----");
        RowLeft.add("La informaci\u00f3n proporcionada es correcta y exacta\nSe definen claramente los objetivos de cada investigaci\u00f3n");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(OBJETIVITY.get(1).get(TMPirowidx));
        RowLeft.add("No comercial");
        RowRight.add("----");
        RowLeft.add("La investigaci\u00f3n no es un folleto informativo, no intenta vender productos o servicios, no es comercial.\nNo utiliza recursos de marketing, ni utiliza un lenguaje comercial, ausencia de anuncios publicitarios");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(OBJETIVITY.get(2).get(TMPirowidx));
        RowLeft.add("Sin sesgo");
        RowRight.add("----");
        RowLeft.add("La autoridad de la fuente de informaci\u00f3n no est\u00e1 sesgada por intereses comerciales o econ\u00f3micos\nLa fuente de informaci\u00f3n aborda diversos enfoques cient\u00edficos, no uno solo\nCuenta con los puntos de vista de otros investigadores\nLos resultados de las investigaciones siempre son contrastados, obteni\u00e9ndose a trav\u00e9s del metido");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(OBJETIVITY.get(3).get(TMPirowidx));
        RowLeft.add("Informaci\u00f3n validada");
        RowRight.add("----");
        RowLeft.add("Los resultados y conclusiones de las investigaciones se respaldan con pruebas.\nSe emplea el m\u00e9todo cient\u00edfico y se explica la metodolog\u00eda utilizada en cada investigaci\u00f3n\nSe citan fuentes de informaci\u00f3n v\u00e1lidas para poder desarrollar las nuevas investigaciones\nLas experiencias y resultados obtenidos han sido contrastados por repetibilidad de las pruebas o por medio de otros investigadores");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(OBJETIVITY.get(4).get(TMPirowidx));
        addRows(tmpevaltable, RowLeft, RowRight);
        OBJETIVITY.clear();
        RowLeft.clear();
        RowRight.clear();
        ColumnsName = DBMSEV.GetColumnsName("quality");
        QUALITY = DBMSEV.LoadList_Table("sourcesevalprotocol", "quality", ColumnsName);
        RowLeft.add("Calidad");
        RowRight.add("--------");
        addHighlitedRows(tmpevaltable, RowLeft, RowRight);
        RowLeft.clear();
        RowRight.clear();
        RowLeft.add("Informaci\u00f3n bien organizada");
        RowRight.add("----");
        RowLeft.add("Estructura l\u00f3gica. Presencia de \u00edndices\nEstructura propia de una investigaci\u00f3n cient\u00edfica\n(Introducci\u00f3n, Objeto de estudio, fuentes, metodolog\u00eda, estado de la cuesti\u00f3n, desarrollo,resultados, conclusiones, bibliograf\u00eda)\nObjetivos y metodolog\u00eda bien presentados\nEstilos y maquetaci\u00f3n normalizada para todos los documentos\nCorrecta narraci\u00f3n en lenguaje cient\u00edfico\nPresencia de normas de estilo l\u00e9xico-gramaticales\nEl argumento del autor no es repetitivo\nNo existe reiteraci\u00f3n continuada de la misma informaci\u00f3n,\nsiempre existe una continuidad investigadora.");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(QUALITY.get(1).get(TMPirowidx));
        RowLeft.add("Gr\u00e1ficos, tablas, im\u00e1genes, ilustraciones y diagramas");
        RowRight.add("----");
        RowLeft.add("Presencia de \u00edndices de tablas, figuras e ilustraciones y su correspondiente identificaci\u00f3n y descripci\u00f3n en los trabajos y documentos cient\u00edficos.");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(QUALITY.get(2).get(TMPirowidx));
        RowLeft.add("\u00cdndices de citas, impacto, rankings");
        RowRight.add("----");
        RowLeft.add("Si la fuente es institucional universitaria o investigadora, determinar su posici\u00f3n en los rankings locales y globales\nSi la fuente es una revista cient\u00edfica comprobar su factor de impacto,  n\u00famero de citas e \u00edndice H\nSi la fuente es una base de datos referencial, determinar el n\u00famero de fuentes y tipos documentales,realizar varios muestreos y comprobar sus factores de calidad. La calidad de la muestra ser\u00e1 extrapolable a la de la base de datos.");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(QUALITY.get(3).get(TMPirowidx));
        addRows(tmpevaltable, RowLeft, RowRight);
        QUALITY.clear();
        RowLeft.clear();
        RowRight.clear();
        ColumnsName = DBMSEV.GetColumnsName("actuality");
        ACTUALITY = DBMSEV.LoadList_Table("sourcesevalprotocol", "actuality", ColumnsName);
        RowLeft.add("Actualidad");
        RowRight.add("--------");
        addHighlitedRows(tmpevaltable, RowLeft, RowRight);
        RowLeft.clear();
        RowRight.clear();
        RowLeft.add("Fechas de publicaci\u00f3n");
        RowRight.add("----");
        RowLeft.add("Distintas fechas de publicaci\u00f3n. (Especialmente relevante en el caso de las revistas cient\u00edficas\nfecha de env\u00edo, fecha de recepci\u00f3n, fecha de aceptaci\u00f3n,fecha de revisi\u00f3n, fecha de publicaci\u00f3n\nLa fuente de informaci\u00f3n permanece actualizada constantemente o con periodicidad temporal reducida.");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(ACTUALITY.get(1).get(TMPirowidx));
        RowLeft.add("Actualizaci\u00f3n");
        RowRight.add("----");
        RowLeft.add("Las fuentes de informaci\u00f3n en ciencias puras, experimentales y aplicadas\ndeben tener una frecuencia de actualizaci\u00f3n m\u00e1s elevada que en ciencias sociales y humanidades\nLa bibliograf\u00eda y documentaci\u00f3n de referencia se actualiza con frecuencia.");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(ACTUALITY.get(2).get(TMPirowidx));
        addRows(tmpevaltable, RowLeft, RowRight);
        ACTUALITY.clear();
        RowLeft.clear();
        RowRight.clear();
        ColumnsName = DBMSEV.GetColumnsName("relevance");
        RELEVANCE = DBMSEV.LoadList_Table("sourcesevalprotocol", "relevance", ColumnsName);
        RowLeft.add("Relevancia");
        RowRight.add("--------");
        addHighlitedRows(tmpevaltable, RowLeft, RowRight);
        RowLeft.clear();
        RowRight.clear();
        RowLeft.add("Fuente acad\u00e1emica");
        RowRight.add("----");
        RowLeft.add("La fuente de informaci\u00f3n es mantenida o gestionada por una organizaci\u00f3n o instituci\u00f3n acad\u00e9mica o cient\u00edfica.\nLa fuente de informaci\u00f3n no es gen\u00e9rica y el p\u00fablico objetivo es cient\u00edfico.");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(RELEVANCE.get(1).get(TMPirowidx));
        RowLeft.add("Se puede tip\u00ecficar");
        RowRight.add("----");
        RowLeft.add("La fuente de informaci\u00f3n puede tipificarse seg\u00fan el nivel,el tipo documental, su contenido cient\u00edfico,\nseg\u00fan su tem\u00e1tica y origen. Una fuente de la que no es f\u00e1cil distinguir sus caracter\u00edsticas no ofrece la transparencia y claridad requeridas.");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(RELEVANCE.get(2).get(TMPirowidx));
        RowLeft.add("Novedad y valor a\u00f1adido");
        RowRight.add("----");
        RowLeft.add("La fuente proporciona informaci\u00f3n adicional al resto de fuentes del \u00e1rea de conocimiento o aporta informaci\u00f3n reiterada o gen\u00e9rica, ya conocida.\nOriginalidad, nuevos enfoques de investigaci\u00f3n, planteamiento de nuevas hip\u00f3tesis e ideas, identificaci\u00f3n y desarrollo de nuevos m\u00e9todos cient\u00edficos.");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(RELEVANCE.get(3).get(TMPirowidx));
        addRows(tmpevaltable, RowLeft, RowRight);
        RELEVANCE.clear();
        RowLeft.clear();
        RowRight.clear();
        ColumnsName = DBMSEV.GetColumnsName("theweb");
        THEWEB = DBMSEV.LoadList_Table("sourcesevalprotocol", "theweb", ColumnsName);
        RowLeft.add("Web");
        RowRight.add("--------");
        addHighlitedRows(tmpevaltable, RowLeft, RowRight);
        RowLeft.clear();
        RowRight.clear();
        RowLeft.add("Dominio");
        RowRight.add("----");
        RowLeft.add("Se valoran positivamente aquellos recursos que posean dominio .edu .science .gob .gov y dominios geogr\u00e1ficos correspondientes a instituciones");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(THEWEB.get(1).get(TMPirowidx));
        RowLeft.add("Acessibilidad y usabilidad");
        RowRight.add("----");
        RowLeft.add("Acceso adecuado a los elementos de navegaci\u00f3n de la p\u00e1gina web, disposici\u00f3n de mapa web, glosarios, hipertexto ,\nnavegaci\u00f3n estructurada (secuencial y ordenada),  correcta jerarquizaci\u00f3n de los contenidos, navegaci\u00f3n sem\u00e1ntica,\nuso de etiquetados y ontolog\u00edas para clasificar los contenidos, sistemas de b\u00fasqueda simple y avanzada.\nDise\u00f1o web compatible con diferentes resoluciones y navegadores web.\nDistintas alternativas de visualizaci\u00f3n, maquetaci\u00f3n web que facilite la lectura, versiones multiling\u00fces.");
        RowRight.add("");
        RowLeft.add("Evaluaci\u00f3n");
        RowRight.add(THEWEB.get(2).get(TMPirowidx));
        THEWEB.clear();
        addRows(tmpevaltable, RowLeft, RowRight);
    }

    class PDFNumerationPages extends PdfPageEventHelper {

        /**
         * The template with the total number of pages.
         */
        PdfTemplate total;

        private Font normal, normalSmall;
//private Company company;

        public PDFNumerationPages() {
            try {
                this.normal = new Font(BaseFont.createFont(FontFactory.HELVETICA_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED), 8);
                this.normalSmall = new Font(BaseFont.createFont(FontFactory.HELVETICA_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED), 6);
            } catch (Exception ex) {
                ShowDError(SoevanPDFManager.class.getName() + " " + ex.getLocalizedMessage());

            }
        }

        public void onOpenDocument(PdfWriter tmppdfwriter, Document tmpdocumenttoprocess) {
            total = tmppdfwriter.getDirectContent().createTemplate(30, 12);
        }

        /**
         * Adds a header to every page
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
         * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onEndPage(PdfWriter tmppdfwriter, Document tmpocumenttoprocess) {
            PdfPTable downtable = new PdfPTable(3);
            try {
                downtable.setWidths(new int[]{24, 24, 2});
                downtable.getDefaultCell().setFixedHeight(10);
                downtable.getDefaultCell().setBorder(Rectangle.TOP);
                PdfPCell celltableelement = new PdfPCell();
                celltableelement.setBorder(0);
                celltableelement.setBorderWidthTop(1);
                celltableelement.setHorizontalAlignment(Element.ALIGN_LEFT);
                celltableelement.setPhrase(new Phrase("Análisis y evaluación de fuentes especializadas", normalSmall));
                downtable.addCell(celltableelement);

                celltableelement = new PdfPCell();
                celltableelement.setBorder(0);
                celltableelement.setBorderWidthTop(1);
                celltableelement.setHorizontalAlignment(Element.ALIGN_RIGHT);
                celltableelement.setPhrase(new Phrase(String.format("Página %d de", tmppdfwriter.getPageNumber()), normal));
                downtable.addCell(celltableelement);

                celltableelement = new PdfPCell(Image.getInstance(total));
                celltableelement.setBorder(0);
                celltableelement.setBorderWidthTop(1);
                downtable.addCell(celltableelement);
                downtable.setTotalWidth(tmpocumenttoprocess.getPageSize().getWidth()
                        - tmpocumenttoprocess.leftMargin() - tmpocumenttoprocess.rightMargin());
                downtable.writeSelectedRows(0, -1, tmpocumenttoprocess.leftMargin(),
                        tmpocumenttoprocess.bottomMargin() - 15, tmppdfwriter.getDirectContent());
            } catch (DocumentException ex) {
                ShowDError(SoevanPDFManager.class.getName() + " " + ex.getLocalizedMessage());
            }
        }

        public void onCloseDocument(PdfWriter tmppdfwriter, Document tmpdocumenttoprocess) {
            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(tmppdfwriter.getPageNumber() - 1), normal),
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
    
    void OpenPdfDocument(String Path) {
        Runtime rt;
        String OSName = System.getProperty("os.name");
        rt = Runtime.getRuntime();

        if ("windows".equals(OSName)) {
            try {
                String commandwin[] = {"cmd /c start " + Path};
                rt.exec(commandwin);

            } catch (IOException ex) {
                ShowDError(SoevanPDFManager.class.getName() + " " + ex.getLocalizedMessage());
            }
        } else {
            if (Desktop.isDesktopSupported()) {

                File theUMFile = new File(Path);
                try {
                    Desktop.getDesktop().open(theUMFile);
                } catch (IOException ex) {
                    ShowDError(SoevanPDFManager.class.getName() + " " + ex.getLocalizedMessage());
                }
            }
        }

    }

}
