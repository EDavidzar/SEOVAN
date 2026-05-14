/*
 * To change this template, choose Tools | Templates
 * *
 */
package beleris.es.finalprimaryclasses;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Emilio David Diaus López 2008-2025
 * @param <E>
 */
public class CLAllObjectList<E> extends ArrayList<E> {

    private static final long serialVersionUID = 1626517488592449430L;

    /**
     *
     */
    public CLAllObjectList() {
        super();

    }

    /**
     *
     * @param c
     */
    public CLAllObjectList(Collection<? extends E> c) {
        super(c);
    }

    /**
     *
     * @param size
     */
    public CLAllObjectList(int size) {
        super(size);
    }

}
