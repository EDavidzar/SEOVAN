/*
 * To change this template, choose Tools | Templates
 * *
 */
package beleris.es.finalprimaryclasses;

/**
 *
 * @author Emilio David Diaus López 2008-2026
 */
public class CL_ParamList {

    private String sLabel = null;
    private String sTableDBField = null;
    private CLAllObjectList alUsersList = null;

    /**
     *
     */
    public CL_ParamList() {
        sLabel = "";
        sTableDBField = "";
        alUsersList = new CLAllObjectList<>();
    }

    /**
     *
     * @param stmpLabel Etiqueta
     * @param stmpTableDBField Campo
     * @param altmpUsersList Lista de Objetos
     */
    public CL_ParamList(String stmpLabel, String stmpTableDBField, CLAllObjectList altmpUsersList) {

        setsLabel(stmpLabel);
        setsTableDBField(stmpTableDBField);
        setalUsersList(altmpUsersList);
    }

    /**
     * @return the Etiqueta
     */
    public String getsLabel() {
        return sLabel;
    }

    /**
     * @param stmpLabel the stmpLabel to set
     */
    public final void setsLabel(String stmpLabel) {
        this.sLabel = stmpLabel;
    }

    /**
     * @return the alUsersList
     */
    public CLAllObjectList getalUsersList() {
        return alUsersList;
    }

    /**
     * @param tmpalUsersList the tmpalUsersList to set
     */
    public final void setalUsersList(CLAllObjectList tmpalUsersList) {
        this.alUsersList = tmpalUsersList;
    }

    /**
     * @return the CampoDB
     */
    public String getsTableDBField() {
        return sTableDBField;
    }

    /**
     * @param stmpsTableDBField the stmpsTableDBField to set
     */
    public final void setsTableDBField(String stmpsTableDBField) {
        this.sTableDBField = stmpsTableDBField;
    }
}
