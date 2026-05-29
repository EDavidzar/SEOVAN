/*
 @author Emilio David Diaus López 2008-2026
 * *
 * *
 */
package beleris.es.finalinformationmanager;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Emilio David Diaus López 2008-2026
 */
public class LoggingManagerGenerator {

    private final static Logger globalLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    static private FileHandler fileTextFormatter;

    static private SimpleFormatter textFormatter;
   

    /**
     * @return the globalLogger
     */
    public static Logger getGlobalLogger() {
        return globalLogger;
    }

    /**
     *
     */
    public LoggingManagerGenerator() {
        String ProgramName = System.getProperty("program.name");
        try {
            fileTextFormatter = new FileHandler(ProgramName + " " + FechaActual() + ".log", 50 * 1024, 10, true);
        } catch (IOException | SecurityException ex) {
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, null, ex);
            globalLogger.warning("no he podido configurar el archivo de logs ...");
        }

        textFormatter = new SimpleFormatter();
        fileTextFormatter.setFormatter(textFormatter);
        // globalLogger.setUseParentHandlers(true);
        globalLogger.addHandler(fileTextFormatter);
        globalLogger.setLevel(Level.INFO);
        //globalLogger.setUseParentHandlers(true);
        globalLogger.config("Configurado el archivo de logs ...");
    }

    private String FechaActual() {

        SimpleDateFormat date_format = new SimpleDateFormat("dd.MM.yyyy");

        Date Fecha = new Date();

        return date_format.format(Fecha);

    }

}
