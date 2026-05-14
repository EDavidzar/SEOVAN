/*
 @author Emilio David Diaus López 2008-2025
 * *
 * *
 */
package beleris.es.finalprimaryclasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author Emilio David Diaus López 2008-2025
 */
public class DateUtils {

    /**
     *
     * @param TheDate
     * @return
     */
    static public boolean isDateUSA(String TheDate) {
        SimpleDateFormat FechaEEUU = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat FechaEEUU2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat FechaEEUU3 = new SimpleDateFormat("yyyy.MM.dd");
        boolean isusa = true, isusa1 = true, isusa2 = true, isusa3 = true;
        String regexpbar = "\\d{4}/\\d{1,2}/\\d{1,2}";
        String regexpsign = "\\d{4}-\\d{1,2}-\\d{1,2}";
        String regexppoint = "\\d{4}.\\d{1,2}.\\d{1,2}";

        try {
            Date newdate = FechaEEUU.parse(TheDate);
            isusa1 = (Pattern.matches(regexpbar, TheDate) == true);
        } catch (ParseException ex) {
            isusa1 = false;
        }
        try {
            Date newdate = FechaEEUU2.parse(TheDate);
            isusa2 = (Pattern.matches(regexpsign, TheDate) == true);
        } catch (ParseException ex) {
            isusa2 = false;
        }
        try {
            Date newdate = FechaEEUU3.parse(TheDate);
            isusa3 = (Pattern.matches(regexppoint, TheDate) == true);
        } catch (ParseException ex) {
            isusa3 = false;
        }
        isusa = (isusa1 == true) || (isusa2 == true) || (isusa3 == true);
        return isusa;
    }

    /**
     *
     * @param USA
     * @param laFecha
     * @return
     */
    static public String ConvertDateFormat(boolean USA, String laFecha) {
//        Calendar calendario = GregorianCalendar.getInstance();
        //       java.util.Date sdfDate = calendario.getTime();
        String returnstr = null;
        java.util.Date sdfDate = null;
        SimpleDateFormat FechaSpain = new SimpleDateFormat("dd/MM/yyyy"),
                FechaEEUU = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat FechaSpain2 = new SimpleDateFormat("dd-MM-yyyy"),
                FechaEEUU2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat FechaSpain3 = new SimpleDateFormat("dd.MM.yyyy"),
                FechaEEUU3 = new SimpleDateFormat("yyyy.MM.dd");

        if (USA == true) {
            try {
                sdfDate = FechaSpain.parse(laFecha);
            } catch (ParseException ex) {

            }
            if (sdfDate != null) {
                returnstr = FechaEEUU.format(sdfDate);
            }

        } else {
            try {
                sdfDate = FechaEEUU.parse(laFecha);
            } catch (ParseException ex) {

            }
            if (sdfDate != null) {
                returnstr = FechaSpain.format(sdfDate);
            }

        }
        if (returnstr == null) {
            if (USA == true) {
                try {
                    sdfDate = FechaSpain2.parse(laFecha);
                } catch (ParseException ex) {

                }
                if (sdfDate != null) {
                    returnstr = FechaEEUU2.format(sdfDate);
                }

            } else {
                try {
                    sdfDate = FechaEEUU2.parse(laFecha);
                } catch (ParseException ex) {

                }
                if (sdfDate != null) {
                    returnstr = FechaSpain2.format(sdfDate);
                }

            }
        }
        if (returnstr == null) {
            if (USA == true) {
                try {
                    sdfDate = FechaSpain3.parse(laFecha);
                } catch (ParseException ex) {

                }
                if (sdfDate != null) {
                    returnstr = FechaEEUU3.format(sdfDate);
                }

            } else {
                try {
                    sdfDate = FechaEEUU3.parse(laFecha);
                } catch (ParseException ex) {

                }
                if (sdfDate != null) {
                    returnstr = FechaSpain3.format(sdfDate);
                }

            }
        }

        return returnstr;
    }

}
