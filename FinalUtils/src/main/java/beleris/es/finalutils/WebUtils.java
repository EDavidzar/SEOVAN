/*
 * To change this template, choose Tools | Templates
 * *
 */
package beleris.es.finalutils;

import beleris.es.finalprimaryclasses.CL_DCVS;

/**
 *
 * @author Emilio David Diaus López 2008-2021
 */
public class WebUtils {

    /**
     * @return the sNewLineSeparator
     */
    public static String getNewLineSeparator() {
        return sNewLineSeparator;
        //return "";
    }

    /**
     * @param aNewLineSeparator the sNewLineSeparator to set
     */
    public static void setNewLineSeparator(String aNewLineSeparator) {
        sNewLineSeparator = aNewLineSeparator;
    }

    /**
     * @return the sTabSeparator
     */
    public static String getTabSeparator() {
        //return sTabSeparator;
        return "";
    }

    /**
     *
     * @param more
     * @return
     */
    public static String getmoreTabSeparator(int more) {
        String ts = "";

        for (int i = 0; i < more; i++) {
            ts += sTabSeparator;
        }

        return ts;
    }

    /**
     * @param aTabSeparator the sTabSeparator to set
     */
    public static void setTabSeparator(String aTabSeparator) {
        sTabSeparator = aTabSeparator;
    }

    /*
     * public String ConvertSimbolsToHTML(String sTmp) { String sRes = "";
     *
     *
     * sTmp = sTmp.replaceAll("á", "&aacute;"); sTmp = sTmp.replaceAll("é",
     * "&eacute;"); sTmp = sTmp.replaceAll("í", "&iacute;"); sTmp =
     * sTmp.replaceAll("ó", "&oacute;"); sTmp = sTmp.replaceAll("ú",
     * "&uacute;");
     *
     * sTmp = sTmp.replaceAll("ñ", "&ntilde;"); sTmp = sTmp.replaceAll("ü",
     * "&uulm;");
     *
     * sTmp = sTmp.replaceAll("Á", "&Aacute;"); sTmp = sTmp.replaceAll("É",
     * "&Eacute;"); sTmp = sTmp.replaceAll("Í", "&Iacute;"); sTmp =
     * sTmp.replaceAll("Ó", "&Oacute;"); sTmp = sTmp.replaceAll("Ú",
     * "&Uacute;");
     *
     * sTmp = sTmp.replaceAll("Ñ", "&Ntilde;"); sTmp = sTmp.replaceAll("Ü",
     * "&Uulm;");
     *
     * sRes = sTmp;
     *
     * return sRes; }
     */

    /**
     *
     * @return
     */

    public static String getOpenElementEnvio() {
        String sAENR = "<Envio Id=\"AGPD\">" + WebUtils.getNewLineSeparator();
        return sAENR;
    }

    /**
     *
     * @return
     */
    public static String getCloseElementEnvio() {
        String sAENR = "</Envio>" + WebUtils.getNewLineSeparator();
        return sAENR;
    }

    /**
     *
     * @param tmpdcv
     * @return
     */
    public String Field2XML(CL_DCVS tmpdcv) {
        String ssdcv = "";
        //if (tmpdcv.getsValor().equals("0")){
        //      tmpdcv.setsValor("");
        //  }
        if (!tmpdcv.getsValue().equals("")) {
            String AbrirCampo = "<" + tmpdcv.getsField() + ">";
            String CerrarCampo = "</" + tmpdcv.getsField() + ">";// + WebUtils.getNewLineSeparator();
            ssdcv += AbrirCampo + tmpdcv.getsValue() + CerrarCampo;
        } else {
            ssdcv += "<" + tmpdcv.getsField() + "/>";// + WebUtils.getNewLineSeparator();
            //sdcv += WebUtils.getNewLineSeparator()+"<" + tmpdcv.getsCampo() + "/>";// + WebUtils.getNewLineSeparator();
        }
        return ssdcv;
    }

    /**
     *
     * @param sTmp
     * @return
     */
    public static String ConvertSimbolsToHTML(String sTmp) {
        String sresult = null;

        sTmp = sTmp.replaceAll("á", "&aacute;");
        sTmp = sTmp.replaceAll("é", "&eacute;");
        sTmp = sTmp.replaceAll("í", "&iacute;");
        sTmp = sTmp.replaceAll("ó", "&oacute;");
        sTmp = sTmp.replaceAll("ú", "&uacute;");

        sTmp = sTmp.replaceAll("ñ", "&ntilde;");
        sTmp = sTmp.replaceAll("ü", "&uulm;");

        sTmp = sTmp.replaceAll("Á", "&Aacute;");
        sTmp = sTmp.replaceAll("É", "&Eacute;");
        sTmp = sTmp.replaceAll("Í", "&Iacute;");
        sTmp = sTmp.replaceAll("Ó", "&Oacute;");
        sTmp = sTmp.replaceAll("Ú", "&Uacute;");

        sTmp = sTmp.replaceAll("Ñ", "&Ntilde;");
        sTmp = sTmp.replaceAll("Ü", "&Uulm;");
        //sTmp=getReplaceLiteralChars(sTmp);

        sresult = sTmp;

        return sresult;
    }

    /**
     *
     * @param border
     * @param cellspacing
     * @param cellpadding
     * @param width
     * @return
     */
    public String getWriteHeaderTable(String border, String cellspacing, String cellpadding, String width) {
        String WHTable = "<TABLE border=" + border + " class=\"tabla\" align=center cellspacing=" + cellspacing + " cellpadding="
                + cellpadding + " width=" + width + ">";
        return ConvertSimbolsToHTML(WHTable);
    }

    /**
     *
     * @return
     */
    public String getWriteCloseTable() {
        String WCTable = "</TABLE>";
        return ConvertSimbolsToHTML(WCTable);
    }

    /**
     *
     * @return
     */
    public String getWriteOpenRowLine() {
        String WRLTable = "<TR>";
        return WRLTable;
    }

    /**
     *
     * @return
     */
    public String getWriteCloseRowLine() {
        String WCLTable = "</TR>";
        return WCLTable;
    }

    /**
     *
     * @param dcell
     * @return
     */
    public String getWriteDataCell(String dcell) {
        String WRDCTable = "<TD>" + dcell + "</TD>";
        return ConvertSimbolsToHTML(WRDCTable);
    }

    /**
     *
     * @param title
     * @return
     */
    public String getWriteTitleLine(String title) {
        String WRLTable = "<TH class=\"titulo2\" colspan=100%>" + title + "</TH>";
        return ConvertSimbolsToHTML(WRLTable);
    }

    /**
     *
     * @param link
     * @param text
     * @param section
     * @return
     */
    public String getWriteLink(String link, String text, String section) {
        String WRLTable = "<A href=" + link + " title=" + text + " name=" + section + "> " + text + " </A>";
        return ConvertSimbolsToHTML(WRLTable);
    }

    /**
     *
     * @param text
     * @return
     */
    public String getWrite(String text) {
        return ConvertSimbolsToHTML(text);

    }

    /**
     *
     * @param sTitle
     * @return
     */
    public String getWriteGlobalTitle(String sTitle) {
        String sHeader = "<head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"> <title>"
                + sTitle
                + "</title> <link href=\"estilos.css\" rel=\"stylesheet\" type=\"text/css\"></head>";
        return ConvertSimbolsToHTML(sHeader);
    }

    /**
     *
     * @param tmps
     * @param sizehdr
     * @return
     */
    public String getWriteHeader(String tmps, String sizehdr) {

        String Headerstr = "<H" + sizehdr + " align=center class=\"titulo\">" + tmps
                + "</H" + sizehdr + ">";
        return ConvertSimbolsToHTML(Headerstr);
    }

    /**
     *
     * @param Lugar
     * @param borde
     * @param tamanoh
     * @param tamanow
     * @return
     */
    public String getWriteImage(String Lugar, String borde, String tamanoh, String tamanow) {
        String ImgStr = "<IMG  align=\"center\" src=\"" + Lugar + "\" border=\"" + borde + "\" heigth=\"" + tamanoh
                + " width=\"" + tamanow + "\" >";
        return ConvertSimbolsToHTML(ImgStr);
    }

    /**
     *
     * @return
     */
    public String getWriteOpenBody() {
        String OBody = "<BODY bgcolor=\"white\" text=\"blue\">";

        return ConvertSimbolsToHTML(OBody);
    }

    /**
     *
     * @return
     */
    public String getWriteCloseBody() {
        String CBody = "</BODY>";
        return ConvertSimbolsToHTML(CBody);
    }

    /**
     *
     * @return
     */
    public String getWriteHorizLine() {
        String WHLine = "<HR>";
        return ConvertSimbolsToHTML(WHLine);
    }

    /**
     *
     * @param centered
     * @return
     */
    public String getWriteHorizLine(String centered) {
        String WHLine = "<HR align=\"" + centered + "\">";
        return ConvertSimbolsToHTML(WHLine);
    }

    /**
     *
     * @param centered
     * @param width
     * @return
     */
    public String getWriteHorizLine(String centered, String width) {
        String WHLine = "<HR align=\"" + centered + "\" width=\"" + width + "\">";
        return ConvertSimbolsToHTML(WHLine);
    }

    /**
     *
     * @param title
     * @return
     */
    public String getWriteSubCategoryTitleLine(String title) {
        String WRLTable = "<TH class=\"titulo4\" colspan=100%>" + title + "</TH>";
        return ConvertSimbolsToHTML(WRLTable);
    }

    /**
     *
     * @param title
     * @return
     */
    public String getWriteSubTitleLine(String title) {
        String WRLTable = "<TH class=\"titulo3\">" + title + "</TH>";
        return ConvertSimbolsToHTML(WRLTable);
    }

    /**
     *
     * @param Lugar
     * @param borde
     * @param tamanoh
     * @param tamanow
     * @return
     */
    public String getWriteImageinCell(String Lugar, String borde, String tamanoh, String tamanow) {
        String ImgStr = "<TD> <IMG  align=\"center\" src=\"" + Lugar + "\" border=\"" + borde + "\" heigth=\"" + tamanoh
                + " width=\"" + tamanow + "\" > </TD>";
        return ConvertSimbolsToHTML(ImgStr);
    }

    /**
     *
     * @param stmpStr
     * @return
     */
    public static String getReplaceLiteralChars(String stmpStr) {
        String sResult = null;
        sResult = stmpStr.replace("<", "&lt;");
        stmpStr = sResult;
        sResult = stmpStr.replace(">", "&gt;");
        stmpStr = sResult;
        sResult = stmpStr.replace("\"", "&quot;");

        return sResult;
    }

    private static String sNewLineSeparator = System.getProperty("line.separator");
    private static String sTabSeparator = "\t";

}
