/*
 * To change this template, choose Tools | Templates
 * *
 */
package beleris.es.finalprimaryclasses;

/**
 *
 * @author Emilio David Diaus López 2008-2025
 */
public class CL_DBCV {

    private String sValue = null;
    private String sTableDBField = null;

    /**
     *
     */
    public CL_DBCV() {
        sTableDBField = "";
        sValue = "";
    }

    /**
     *
     * @param tmpsTableDBField
     * @param tmpsValue
     */
    public CL_DBCV(String tmpsTableDBField, String tmpsValue) {
        sTableDBField = tmpsTableDBField;
        sValue = tmpsValue;
    }

    /**
     * @return the sValue
     */
    public String getsValue() {
        return sValue;
    }

    /**
     * @param sValue the sValue to set
     */
    public void setsValue(String sValue) {
        this.sValue = sValue;
    }

    /**
     * @return the sTableDBField
     */
    public String getsTableDBField() {
        return sTableDBField;
    }

    /**
     * @param tmpsTableDBField the tmpsTableDBField to set
     */
    public void setsTableDBField(String tmpsTableDBField) {
        this.sTableDBField = tmpsTableDBField;
    }
}
