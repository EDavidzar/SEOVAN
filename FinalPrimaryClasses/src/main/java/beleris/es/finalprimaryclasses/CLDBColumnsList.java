/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beleris.es.finalprimaryclasses;

/**
 *
 * @author Emilio David Diaus López 2008-2025
 */
public class CLDBColumnsList {

    private CLAllObjectList<CL_DBColumns> Elements = new CLAllObjectList<>();

    /**
     * @return the Elements
     */
    public CLAllObjectList<CL_DBColumns> getElements() {
        return Elements;
    }

    /**
     * @param Elements the Elements to set
     */
    public void setElements(CLAllObjectList<CL_DBColumns> Elements) {
        this.Elements = Elements;
    }

    /**
     *
     * @param Element
     */
    public void addCol(CL_DBColumns Element) {
        Elements.add(Element);
    }

}
