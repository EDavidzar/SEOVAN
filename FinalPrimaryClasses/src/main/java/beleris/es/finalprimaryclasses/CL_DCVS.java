/*
 * To change this template, choose Tools | Templates
 * *
 */
package beleris.es.finalprimaryclasses;

import java.util.regex.Pattern;

/**
 *
 * @author Emilio David Diaus López 2008-2026
 */
public class CL_DCVS {

    private String sField = null;
    private String sValue = null;
    private int ilongchar = 0;
    private boolean errorverificacion = false;
    private static final Pattern cifPattern = Pattern.compile("[[A-H][J-N][P-S]UVW][0-9]{7}[0-9A-J]");
    private static final String C_ONLY_NUMBERS = "ABEH"; // Sólo admiten números como caracter de control
    private static final String C_ONLY_LETTERS = "KPQS"; // Sólo admiten letras como caracter de control
    private static final String C_NUMBERTOLETTER = "JABCDEFGHI"; // Conversión de dígito a letter de control.
/*
    public static boolean isCif(String cif) {
        try {
            if (!cifPattern.matcher(cif).matches()) {
                // No cumple el patrón
                return false;
            }

            int parA = 0;
            for (int i = 2; i < 8; i += 2) {
                final int idigitnumber = Character.digit(cif.charAt(i), 10);
                if (idigitnumber < 0) {
                    return false;
                }
                parA += idigitnumber;
            }

            int nonB = 0;
            for (int i = 1; i < 9; i += 2) {
                final int idigitnumber = Character.digit(cif.charAt(i), 10);
                if (idigitnumber < 0) {
                    return false;
                }
                int nn = 2 * idigitnumber;
                if (nn > 9) {
                    nn = 1 + (nn - 10);
                }
                nonB += nn;
            }

            final int parcialC = parA + nonB;
            final int digittoE = parcialC % 10;
            final int digittoD = (digittoE > 0)
                    ? (10 - digittoE)
                    : 0;
            final char letraIni = cif.charAt(0);
            final char caracterFin = cif.charAt(8);

            final boolean isValid =
                    // ¿el caracter de control es válido como letter?
                    (C_ONLY_NUMBERS.indexOf(letraIni) < 0
                    && C_NUMBERTOLETTER.charAt(digittoD) == caracterFin)
                    || // ¿el caracter de control es válido como dígito?
                    (C_ONLY_LETTERS.indexOf(letraIni) < 0
                    && digittoD == Character.digit(caracterFin, 10));
            return isValid;

        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNif(String nif) {


        //si es NIE, eliminar la x,y,z inicial para tratarlo como nif
        if (nif.toUpperCase().startsWith("X") || nif.toUpperCase().startsWith("Y") || nif.toUpperCase().startsWith("Z")) {
            nif = nif.substring(1);
        }

        Pattern nifPattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
        Matcher m = nifPattern.matcher(nif);
        if (m.matches()) {
            String letter = m.group(2);
//Extraer letter del NIF
            String sletters = "TRWAGMYFPDXBNJZSQVHLCKE";
            int dni = Integer.parseInt(m.group(1));
            dni = dni % 23;
            String reference = sletters.substring(dni, dni + 1);

            if (reference.equalsIgnoreCase(letter)) {

                return true;
            } else {

                return false;
            }
        } else {
            return false;
        }
    }

    void showerror(String tmptipo, String tmpsvalor) {
     //   ShowDError("La Cadena " + tmptipo + " [" + tmpsvalor + "] no cumple las especificaciones ");
    }

    final String Verificador(String stype, String sValue, int iLongitude) {
        String result = null;

        if (iLongitude > sValue.length()) {
            iLongitude = sValue.length();
        }

        if (sValue.length() > iLongitude) {
            sValue = sValue.substring(0, iLongitude);
        }
        if (iLongitude == -1) {
            result = null;
        }
        if (iLongitude > 0) {
            result = sValue.substring(0, iLongitude);
        }
        if ("".equals(sValue)) {
            result = sValue;
        }


        if ("cif_nif".equals(stype)) {
            boolean r1 = isCif(sValue);
            boolean r2 = isNif(sValue);
            if ((r1 == false) && (r2 == false)) {
                result = null;
                setErrorverification(true);
                showerror(stype, sValue);
                //throw new NumberFormatException();
            }
        }
        if ("cif".equals(stype)) {
            if (!isCif(sValue)) {
                result = null;
                setErrorverification(true);
                showerror(stype, sValue);
                //throw new NumberFormatException();
            }
        }
        if ("nif".equals(stype)) {

            Pattern telPattern = Pattern.compile("(\\w{9})");
            Matcher m = telPattern.matcher(sValue);
            if ((sValue.length() != 9) || (sValue.isEmpty())) {
                result = null;
                setErrorverification(true);
                showerror(stype, sValue);
                // throw new NumberFormatException();
            }
            if (!m.matches()) {
                result = null;
                setErrorverification(true);
                showerror(stype, sValue);
                // throw new NumberFormatException();
            }
            if (!isNif(sValue)) {
                result = null;
                setErrorverification(true);
                showerror(stype, sValue);
                //throw new NumberFormatException();
            }
        }
        if ("telefono".equals(stype) || "fax".equals(stype)) {
            Pattern telPattern = Pattern.compile("(\\d{9})");
            Matcher m = telPattern.matcher(sValue);
            if (!m.matches()) {
                result = null;
                setErrorverification(true);
                showerror(stype, sValue);
                //throw new NumberFormatException();
            }
        }

        if ("correoe".equals(stype) || "email".equals(stype)) {
            Pattern telPattern = Pattern.compile("[a-zA-Z0-9]+[.[a-zA-Z0-9_-]+]*@[a-z0-9][\\w\\.-]*[a-z0-9]\\.[a-z][a-z\\.]*[a-z]$");
            Matcher m = telPattern.matcher(sValue);
            if (!m.matches()) {
                result = null;
                setErrorverification(true);
                showerror(stype, sValue);
                //throw new NumberFormatException();
            }
        }
        if ("postal".equals(stype)) {
            Pattern telPattern = Pattern.compile("(\\d{5})");
            Matcher m = telPattern.matcher(sValue);
            if (!m.matches()) {
                result = null;
                setErrorverification(true);
                showerror(stype, sValue);
                //throw new NumberFormatException();
            }
        }

        return result;
    }

    public CL_DCVS() {
        sField = null;
        sValue = null;
    }*/

    /**
     *
     * @param stmpValue
     * @param stmpField
     * @param itmpLongchar
     */
    public CL_DCVS(String stmpValue, String stmpField, int itmpLongchar) {

        sField = stmpField;
        sValue = stmpValue;
        ilongchar = itmpLongchar;
        // sValue = Verificador(stmpField, stmpValue, itmpLongchar);
    }

    /**
     * @return the sField
     */
    public String getsField() {
        return sField;
    }

    /**
     * @param sField the sField to set
     */
    public void setsField(String sField) {
        this.sField = sField;
    }

    /**
     * @return the sValue
     */
    public String getsValue() {
        return sValue;
    }

    /**
     * @param sValue the sValue to set
     */
    public void setsValue(String sValue) {
        this.sValue = sValue;
    }

    /**
     * @return the Size
     */
    public int getSize() {
        return ilongchar;
    }

    /**
     * @param itmpLongchar the itmpLongchar to set
     */
    public void setLongchar(int itmpLongchar) {
        this.ilongchar = itmpLongchar;
    }
    /*
    /**
     * @return the errorverificacion
     */
 /*   public boolean isErrorverification() {
        return errorverificacion;
    }

    /**
     * @param errorverification the errorverification to set
     */
 /*  public void setErrorverification(boolean errorverification) {
        this.errorverificacion = errorverification;
    }*/
}
