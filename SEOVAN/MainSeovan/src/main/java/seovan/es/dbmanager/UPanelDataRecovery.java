/*
 * To change this template, choose Tools | Templates
 * *
 */
package seovan.es.dbmanager;

import beleris.es.finalprimaryclasses.CLAllObjectList;
import beleris.es.finalprimaryclasses.CL_String_Obj;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 ** @author Emilio David Diaus López 2008-2025
 */
public class UPanelDataRecovery {

    /**
     *
     * @param jlm
     * @return
     */
    public static String ComputeFullModel(JList<?> jlm) {
        int icounter = 0, ilistsize = 0;

        String sTmp = "", sElement = null, sPartElement = null;

        ListModel<?> lm = jlm.getModel();

        ilistsize = lm.getSize();
        for (icounter = 0; icounter < ilistsize; icounter++) {

            if (jlm.getModel().getElementAt(icounter) instanceof java.util.ArrayList) {

                ArrayList<?> alElement = (ArrayList<?>) lm.getElementAt(icounter);
                if (alElement != null) {
                    sElement = alElement.get(0).toString();
                }
            }

            if (jlm.getModel().getElementAt(icounter) instanceof java.lang.String) {
                String tmpEl = lm.getElementAt(icounter).toString();
                if (tmpEl != null) {
                    sElement = tmpEl;
                }

            }

            if (sElement != null) {
                if (sElement.length() > 1) {
                    if ((sElement.charAt(3) == ' ') && (sElement.charAt(0) != '[')) {
                        sPartElement = sElement.substring(0, 3);
                    }
                    if (sElement.startsWith("N") && sElement.startsWith("[")) {
                        sPartElement = sElement.substring(1, 2);
                        sTmp += sPartElement;
                    }
                    if (sPartElement != null) {
                        if (sPartElement.startsWith("4") && sPartElement.length() == 3) {
                            sPartElement = sElement.substring(1, 3);
                            sTmp += sPartElement;
                        }

                    } else {
                        sPartElement = sElement.substring(0, 2);
                        sTmp += sPartElement;
                        sPartElement = null;
                    }
                } else {
                    sTmp = sElement;
                }
            }
            if (icounter < ilistsize) {
                sTmp += ";";
            }

        }
        ilistsize = sTmp.length();
        String laststr = sTmp.substring(ilistsize - 1);

        if (";".equals(laststr)) {
            sTmp = sTmp.substring(0, ilistsize - 1);
        }
        return sTmp;
    }

    /**
     *
     * @param ThePanel
     * @return
     */
    public static String LoadandComputeSwitchPanel(JPanel ThePanel) {
        String sresult = null, sNCampo = null;
        //    JPanel jtempJP = null;

        Component[] C = null;
        C = ThePanel.getComponents();

        int isize = C.length;
        long iData = 0;

        for (int i = 0; i < isize; i++) {
            sNCampo = C[i].getClass().getName();

            if (sNCampo.equals("javax.swing.JCheckBox")) {
                if (((JCheckBox) C[i]).isSelected()) {
                    long pr = i << 2;
                    iData = iData + pr;

                }
            }
        }
        sresult = String.valueOf(iData);

        return sresult;

    }

    /**
     *
     * @param tmpSField
     * @param car
     * @return
     */
    public static CLAllObjectList<Integer> ConvertField2Numbers(String tmpSField, int car) {
        CLAllObjectList<Integer> WholeNumberList = new CLAllObjectList<>(1);
        int ilistsize = tmpSField.length();
        String laststr = tmpSField.substring(ilistsize - 1);
        if (";".equals(laststr)) {
            tmpSField = tmpSField.substring(0, ilistsize - 1);
        }
        ilistsize = tmpSField.length();
        if ((ilistsize < 2) && (ilistsize % 2 != 0)) {
            tmpSField = "0" + tmpSField;
        }
        if (ilistsize == 2) {
            // fase uno solo numeros y no ;
            int pos = tmpSField.indexOf(";");
            if (pos == -1) {
                int idata = Integer.parseInt(tmpSField);
                WholeNumberList.add(idata);
            }
        }
        if (ilistsize > 2) {
            for (int icounter = 0; icounter < ilistsize; icounter += car) {
                if ((icounter + car) < ilistsize) {
                    String sdata2 = tmpSField.substring(icounter, icounter + car);
                    sdata2 = sdata2.replaceAll(";", "");
                    int idata = Integer.parseInt(sdata2);
                    if (idata > 0) {
                        WholeNumberList.add(idata);
                    }
                } else if ((icounter + car) == ilistsize + 1) {
                    String sdata2 = tmpSField.substring(icounter, icounter + car - 1);
                    sdata2 = sdata2.replaceAll(";", "");
                    int idata = Integer.parseInt(sdata2);
                    if (idata > 0) {
                        WholeNumberList.add(idata);
                    }

                }
            }
        }
        return WholeNumberList;
    }

    private Object makeObj(final String item) {
        return new Object() {
            public String toString() {
                return item;
            }
        };
    }

    /**
     *
     * @param tmpLista
     * @param Lista
     */
    public static void InsertDataListToPanel(CLAllObjectList<String> tmpLista, Object Lista) {
        int sz = 0, i = 0;

        JComboBox<Object> tempJBC = null;
        JList<Object> tempL = null;

        String CN = Lista.getClass().getName();
        if (CN.equals("javax.swing.JList")) {
            tempL = (JList) Lista;

        }

        if (CN.equals("javax.swing.JComboBox")) {
            tempJBC = (JComboBox<Object>) Lista;
            if (tmpLista.toArray() != null || tempJBC != null) {
                tempJBC.setModel(new DefaultComboBoxModel(tmpLista.toArray()));
            }
        }

        if (CN.equals(
                "javax.swing.JList")) {
            if (tmpLista.toArray() == null || tempL == null) {
            } else {
                tempL.setListData(tmpLista.toArray());
            }

        }

    }

    /**
     *
     * @param Element
     * @param Value
     */
    public static void RecoverObjectTypeNoPanel(CL_String_Obj Element, String Value) {

        JComboBox<Object> jtempCB = null;
        JTextField jtempTF = null;
        JTextArea jtempTA = null;
        JLabel jtempJL = null;
        JCheckBox jtempChB = null;
        String jtempS = null;
        String sNField = null;

        int iValue = 0;

        sNField = Element.getoPanelObject().getClass().getName();

        try {
            iValue = Integer.parseInt(Value);

        } catch (NumberFormatException ex) {
            iValue = -1;

        }
        // Ojo!! Esto es imposible que funcione Strings no se pasan por valor
        if (sNField.equals("java.lang.String")) {
            jtempS = (String) Element.getoPanelObject();
            jtempS = Value;

        }

        if (sNField.equals("javax.swing.JComboBox")) {
            jtempCB = (JComboBox<Object>) Element.getoPanelObject();
            int tmpcount = jtempCB.getItemCount();

            if (iValue == -1) {
                jtempCB.addItem(Value);
                jtempCB.setSelectedItem(Value);
            } else {
                if (iValue > tmpcount) {
                    iValue = tmpcount;
                }
                if (iValue == tmpcount) {
                    iValue = tmpcount - 1;
                }
                if (iValue > 0) {
                    if (tmpcount > 0) {
                        jtempCB.setSelectedIndex(iValue);
                    }
                } else if (tmpcount > 0) {
                    jtempCB.setSelectedIndex(0);
                }
            }

        }

        if (sNField.equals("javax.swing.JTextField")) {
            jtempTF = (JTextField) Element.getoPanelObject();
            jtempTF.setText(Value);

        }
        if (sNField.equals("javax.swing.JTextArea")) {
            jtempTA = (JTextArea) Element.getoPanelObject();
            jtempTA.setText(Value);

        }
        if (sNField.equals("javax.swing.JLabel")) {
            jtempJL = (JLabel) Element.getoPanelObject();
            jtempJL.setText(Value);

        }
        if (sNField.equals("javax.swing.JCheckBox")) {
            jtempChB = (JCheckBox) Element.getoPanelObject();

            if (Value.equals("1")) {
                jtempChB.setSelected(true);

            } else {
                jtempChB.setSelected(false);

            }
        }
    }

    /**
     *
     * @param Element
     * @param Valor
     */
    public static void TypePanelObjectResolve(CL_String_Obj Element, String Valor) {
        String sNfield = null;
        JPanel jtempJP = null;

        jtempJP = (JPanel) Element.getoPanelObject();
        Component[] C = null;
        C = jtempJP.getComponents();

        int longitude = C.length;

        int iDatos = Integer.parseInt(Valor);

        for (int icounter = 0; icounter < longitude; icounter++) {
            int pr = 1 << (icounter - 1);

            int pr2 = iDatos & pr;

            if (pr2 == pr) {
                sNfield = C[icounter].getClass().getName();

                if (sNfield.equals("javax.swing.JCheckBox")) {
                    ((JCheckBox) C[icounter]).setSelected(true);

                }
            }
        }
    }

}
