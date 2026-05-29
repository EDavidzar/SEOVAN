/*
 * To change this template, choose Tools | Templates
 * *
 */
package beleris.es.finalprimaryclasses;

/**
 *
 ** @author Emilio David Diaus López 2008-2026
 */
public class CL_Enterprise extends Object {

    private String sNEnterprise = null;
    private String sIDEnterprise = null;

    /**
     *
     */
    public CL_Enterprise() {
        sNEnterprise = "ninguno";
        sIDEnterprise = "0";
    }

    /**
     *
     * @param sNCl
     * @param sIDCl
     */
    public CL_Enterprise(String sNCl, String sIDCl) {
        setsNEnterprise(sNCl);
        setsIDEnterprise(sIDCl);

    }

    /**
     * @return the sNEnterprise
     */
    public String getsNEnterprise() {
        return sNEnterprise;
    }

    /**
     * @param sNClient the sNEnterprise to set
     */
    public final void setsNEnterprise(String sNClient) {
        this.sNEnterprise = sNClient;
    }

    /**
     * @return the sIDEnterprise
     */
    public String getsIDEnterprise() {
        return sIDEnterprise;
    }

    /**
     * @param sIDClient the sIDEnterprise to set
     */
    public final void setsIDEnterprise(String sIDClient) {
        this.sIDEnterprise = sIDClient;
    }
    /**
     * @return the sContra
     */
}
