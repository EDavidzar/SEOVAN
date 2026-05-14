/*
 * To change this template, choose Tools | Templates
 * *
 */
package beleris.es.finalutils;

import java.io.*;

/**
 *
 * @author Emilio David Diaus López 2008-2021
 */
public class WebFileComposer {

    /**
     *
     * @param File
     */
    public WebFileComposer(String File) {
        try {
            TheFile = new FileWriter(File);
        } catch (IOException ie) {
            sErrorMessage = ie.getLocalizedMessage();
            berror = true;
        }
        TheBuffer = new BufferedWriter(TheFile);
    }

    /**
     *
     * @param tmps
     */
    public void Write(String tmps) {

        try {
            TheBuffer.write(H2S.getWrite(tmps));
        } catch (IOException ie) {
            sErrorMessage = ie.getLocalizedMessage();
            berror = true;
        }
    }

    /**
     *
     * @param sTitle
     */
    public void WriteGlobalTitle(String sTitle) {

        Write(H2S.getWriteGlobalTitle(sTitle));
    }

    /**
     *
     * @param tmps
     * @param sizehdr
     */
    public void WriteHeader(String tmps, String sizehdr) {

        Write(H2S.getWriteHeader(tmps, sizehdr));
    }

    /**
     *
     * @param Lugar
     * @param borde
     * @param tamanoh
     * @param tamanow
     */
    public void WriteImage(String Lugar, String borde, String tamanoh, String tamanow) {

        Write(H2S.getWriteImage(Lugar, borde, tamanoh, tamanow));
    }

    /**
     *
     */
    public void WriteOpenBody() {

        Write(H2S.getWriteOpenBody());
    }

    /**
     *
     */
    public void WriteCloseBody() {

        Write(H2S.getWriteCloseBody());
    }

    /**
     *
     */
    public void WriteHorizLine() {

        Write(H2S.getWriteHorizLine());
    }

    /**
     *
     * @param centered
     */
    public void WriteHorizLine(String centered) {

        Write(H2S.getWriteHorizLine(centered));
    }

    /**
     *
     * @param centered
     * @param width
     */
    public void WriteHorizLine(String centered, String width) {

        Write(H2S.getWriteHorizLine(centered, width));
    }

    /**
     *
     * @param border
     * @param cellspacing
     * @param cellpadding
     * @param width
     */
    public void WriteHeaderTable(String border, String cellspacing, String cellpadding, String width) {

        Write(H2S.getWriteHeaderTable(border, cellspacing, cellpadding, width));
    }

    /**
     *
     */
    public void WriteCloseTable() {

        Write(H2S.getWriteCloseTable());
    }

    /**
     *
     */
    public void WriteOpenRowLine() {

        Write(H2S.getWriteOpenRowLine());
    }

    /**
     *
     */
    public void WriteCloseRowLine() {

        Write(H2S.getWriteCloseRowLine());
    }

    /**
     *
     * @param title
     */
    public void WriteTitleLine(String title) {

        Write(H2S.getWriteTitleLine(title));
    }

    /**
     *
     * @param title
     */
    public void WriteSubCategoryTitleLine(String title) {
        Write(H2S.getWriteSubCategoryTitleLine(title));
    }

    /**
     *
     * @param title
     */
    public void WriteSubTitleLine(String title) {
        Write(H2S.getWriteSubTitleLine(title));
    }

    /**
     *
     * @param dcell
     */
    public void WriteDataCell(String dcell) {
        Write(H2S.getWriteDataCell(dcell));
    }

    /**
     *
     * @param Lugar
     * @param borde
     * @param tamanoh
     * @param tamanow
     */
    public void WriteImageinCell(String Lugar, String borde, String tamanoh, String tamanow) {
        Write(H2S.getWriteImageinCell(Lugar, borde, tamanoh, tamanow));
    }

    /**
     *
     */
    public void HTMLWriteCloseAll() {
        try {
            TheBuffer.close();
        } catch (IOException ie) {
            sErrorMessage = ie.getLocalizedMessage();
            berror = true;
        }
        try {
            TheFile.close();
        } catch (IOException ie) {
            sErrorMessage = ie.getLocalizedMessage();
            berror = true;
        }

    }

    /**
     *
     */
    public String sAlignLeft = "left";

    /**
     *
     */
    public String sAlignCenter = "center";

    /**
     *
     */
    public String sAlignRight = "right";

    /**
     *
     */
    public String sHsize1 = "1";

    /**
     *
     */
    public String sHsize2 = "2";

    /**
     *
     */
    public String sHsize3 = "3";

    /**
     *
     */
    public String sHsize4 = "4";

    /**
     *
     */
    public String sHsize5 = "5";

    /**
     *
     */
    public String sHsize6 = "6";
    FileWriter TheFile = null;
    BufferedWriter TheBuffer = null;
    boolean berror = false;
    String sErrorMessage;
    WebUtils H2S = new WebUtils();
}
