/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seovan.es.panels;

import beleris.es.finalinformationmanager.MainErrorManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
/**
 *
 * @author Emilio David Diaus López 2023-2026
 */
public class TextVerifiers {

    /**
     *
     */
    public TextVerifiers() {
    }

    private Eval05Verifier j05Ver = new Eval05Verifier();
    private Eval10Verifier j10Ver = new Eval10Verifier();

    /**
     * @return the j05Ver
     */
    public Eval05Verifier getJ05Ver() {
        return j05Ver;
    }

    /**
     * @param j05Ver the j05Ver to set
     */
    public void setJ05Ver(Eval05Verifier j05Ver) {
        this.j05Ver = j05Ver;
    }

    /**
     * @return the j10Ver
     */
    public Eval10Verifier getJ10Ver() {
        return j10Ver;
    }

    /**
     * @param j10Ver the j10Ver to set
     */
    public void setJ10Ver(Eval10Verifier j10Ver) {
        this.j10Ver = j10Ver;
    }

}

class Eval05Verifier extends InputVerifier {

    public boolean verify(JComponent input) {
        boolean bisok = false;
        String sTheText = null;
        JTextField tf = (JTextField) input;
        String TheText = tf.getText();
        Pattern telPattern = Pattern.compile("[-1][0-5]");
        Matcher m = telPattern.matcher(sTheText);
        if (m.matches()) {
            bisok = true;
        } else {
            MainErrorManager.ShowDError("La valoración debe ser de 0 a 5 puntos o -1 para no aplicable");
        }
        return bisok;
    }

}

class Eval10Verifier extends InputVerifier {

    public boolean verify(JComponent input) {
        boolean bisok = false;
        String sTheText = null;
        JTextField tf = (JTextField) input;
        String TheText = tf.getText();
        Pattern telPattern = Pattern.compile("[-1][0-10]");
        Matcher m = telPattern.matcher(sTheText);
        if (m.matches()) {
            bisok = true;
        } else {
            MainErrorManager.ShowDError("La valoración debe ser de 0 a 10 puntos o -1 para no aplicable");
        }
        return bisok;
    }

}
