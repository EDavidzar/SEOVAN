/*
 * To change this template, choose Tools | Templates
 * *
 */
package beleris.es.finalprimaryclasses;

/**
 *
 ** @author Emilio David Diaus López 2008-2025
 */
public class CL_Client extends Object {

    private String sNClient = null;
    private String sIDClient = null;

    /**
     *
     */
    public CL_Client() {
        sNClient = "ninguno";
        sIDClient = "0";
    }

    /**
     *
     * @param sNCl
     * @param sIDCl
     */
    public CL_Client(String sNCl, String sIDCl) {
        setsNClient(sNCl);
        setsIDClient(sIDCl);

    }

    /**
     * @return the sNClient
     */
    public String getsNClient() {
        return sNClient;
    }

    /**
     * @param sNClient the sNClient to set
     */
    public final void setsNClient(String sNClient) {
        this.sNClient = sNClient;
    }

    /**
     * @return the sIDClient
     */
    public String getsIDClient() {
        return sIDClient;
    }

    /**
     * @param sIDClient the sIDClient to set
     */
    public final void setsIDClient(String sIDClient) {
        this.sIDClient = sIDClient;
    }
    /**
     * @return the sContra
     */
}
