/*
 * To change this template, choose Tools | Templates
 * *
 */
package beleris.es.finalprimaryclasses;

/**
 *
 * @author Emilio David Diaus López 2008-2021
 */
public class CL_NSC {

    private int inumber = 0;
    private int iafirmative = 0;
    private int iexcluded = 0;
    private int itotal = 0;

    /**
     *
     */
    public CL_NSC() {

    }

    /**
     *
     * @param inttmpnum
     */
    public CL_NSC(int inttmpnum) {
        inumber = inttmpnum;
    }

    /**
     *
     * @param inttmpnum
     * @param iafir
     * @param iexclu
     * @param itmptotal
     */
    public CL_NSC(int inttmpnum, int iafir, int iexclu, int itmptotal) {
        inumber = inttmpnum;
        iafirmative = iafir;
        iexcluded = iexclu;
        itotal = itmptotal;

    }

    /**
     * @return the number
     */
    public int getNumber() {
        return inumber;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.inumber = number;
    }

    /**
     * @return the afirmative
     */
    public int getAfirmative() {
        return iafirmative;
    }

    /**
     * @param afirmative the afirmative to set
     */
    public void setAfirmative(int afirmative) {
        this.iafirmative = afirmative;
    }

    /**
     * @return the excluded
     */
    public int getExcluded() {
        return iexcluded;
    }

    /**
     * @param excluded the excluded to set
     */
    public void setExcluded(int excluded) {
        this.iexcluded = excluded;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return itotal;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.itotal = total;
    }
}
