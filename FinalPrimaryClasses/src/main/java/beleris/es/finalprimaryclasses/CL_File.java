/*
 * To change this template, choose Tools | Templates
 * *
 */
package beleris.es.finalprimaryclasses;

/**
 *
 ** @author Emilio David Diaus López 2008-2021
 */
public class CL_File extends Object {

    private String sNFile = null;
    private String sIDFile = null;

    /**
     *
     */
    public CL_File() {
        sNFile = "ninguno";
        sIDFile = "0";
    }

    /**
     *
     * @param tmpNfichero
     * @param tmpIDFichero
     */
    public CL_File(String tmpNfichero, String tmpIDFichero) {
        sNFile = tmpNfichero;
        sIDFile = tmpIDFichero;
    }

    /**
     * @return the sNFile
     */
    public String getsNFile() {
        return sNFile;
    }

    /**
     * @param sNFile the sNFile to set
     */
    public void setsNFile(String sNFile) {
        this.sNFile = sNFile;
    }

    /**
     * @return the sIDFile
     */
    public String getsIDFile() {
        return sIDFile;
    }

    /**
     * @param sIDFile the sIDFile to set
     */
    public void setsIDFile(String sIDFile) {
        this.sIDFile = sIDFile;
    }
}
