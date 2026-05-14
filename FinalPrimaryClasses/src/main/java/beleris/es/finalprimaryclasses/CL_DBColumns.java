/*
 * To change this template, choose Tools | Templates
 * *
 */
package beleris.es.finalprimaryclasses;

/**
 *
 * @author Emilio David Diaus López 2008-2025
 */
public class CL_DBColumns {

    String alsColumnsNName = "";

    private CLAllObjectList<String> ElementList = new CLAllObjectList<>();

    /**
     *
     */
    public CL_DBColumns() {

    }

    /**
     *
     * @param tmpalsColumns
     * @param tmpElementList
     */
    public CL_DBColumns(String tmpalsColumns, CLAllObjectList<String> tmpElementList) {
        alsColumnsNName = tmpalsColumns;
        ElementList = tmpElementList;
    }

    /**
     * @return the alsColumnsNName
     */
    public String getAlsColumns() {
        return alsColumnsNName;
    }

    /**
     * @param alsColumns the alsColumnsNName to set
     */
    public void setAlsColumns(String alsColumns) {
        this.alsColumnsNName = alsColumns;
    }

    /**
     * @return the ElementList
     */
    public CLAllObjectList<String> getElementList() {
        return ElementList;
    }

    /**
     * @param ElementList the ElementList to set
     */
    public void setElementList(CLAllObjectList<String> ElementList) {
        this.ElementList = ElementList;
    }

}
