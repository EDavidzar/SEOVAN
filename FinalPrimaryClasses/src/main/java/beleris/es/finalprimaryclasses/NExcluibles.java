/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beleris.es.finalprimaryclasses;

/**
 *
 * @author edavid
 */
public class NExcluibles {

    /**
     *
     */
    public NExcluibles() {

    }

    /**
     *
     * @param tmpid_excluded
     * @param tmpncatex
     */
    public NExcluibles(int tmpid_excluded, int tmpncatex) {
        id_excluded = tmpid_excluded;
        ncatex = tmpncatex;
    }
    private int id_excluded = 0;
    private int ncatex = 0;

    /**
     * @return the ncatex
     */
    public int getNcatex() {
        return ncatex;
    }

    /**
     * @param ncatex the ncatex to set
     */
    public void setNcatex(int ncatex) {
        this.ncatex = ncatex;
    }

    /**
     * @return the id_excluded
     */
    public int getId_excluded() {
        return id_excluded;
    }

    /**
     * @param id_excluded the id_excluded to set
     */
    public void setId_excluded(int id_excluded) {
        this.id_excluded = id_excluded;
    }

}
