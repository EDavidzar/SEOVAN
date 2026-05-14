/*
 * To change this template, choose Tools | Templates
 * *
 */
package beleris.es.finalutils;

import beleris.es.finalinformationmanager.LoggingManagerGenerator;
import java.io.*;
import java.util.logging.Level;

/**
 *
 * @author Emilio David Diaus López 2008-2021
 */
public class WebFileBufferManager {

    /**
     *
     */
    public WebFileBufferManager() {
    }

    /**
     *
     * @param FileIn
     * @param FileOut
     */
    public WebFileBufferManager(String FileIn, String FileOut) {
        if (FileIn != null) {

            TheFile = new File(FileIn);
            if (TheFile.exists()) {
                SizeOfFile = TheFile.length();
            } else {
                sErrorMessage = "Fichero Inexistente";
                berror = true;
            }
            try {
                TheFileReading = new FileReader(FileIn);
            } catch (IOException ie) {
                sErrorMessage = ie.getLocalizedMessage();
                berror = true;
            }

            TheBufferReading = new BufferedReader(getTheFileReading());
        } else {
            TheFileReading = null;
            TheBufferReading = null;
        }
        if (FileOut != null) {
            try {
                TheFileWriting = new FileWriter(FileOut);
            } catch (IOException ie) {
                sErrorMessage = ie.getLocalizedMessage();
                berror = true;
            }
            TheBufferWriting = new BufferedWriter(TheFileWriting);
        } else {
            TheFileWriting = null;
            TheBufferWriting = null;
        }

    }

    /**
     *
     * @return
     */
    public String ReadLine() {

        String sresult = null;

        try {
            sresult = TheBufferReading.readLine();
        } catch (IOException ie) {
            setErrorMessage(ie.getLocalizedMessage());
            setError(true);
        }

        return sresult;
    }

    /**
     *
     */
    public void HTMLReaderCloseAll() {
        if (TheBufferReading != null) {
            try {
                TheBufferReading.close();
            } catch (IOException ie) {
                setErrorMessage(ie.getLocalizedMessage());
                setError(true);
            }
        }
        if (getTheFileReading() != null) {
            try {
                getTheFileReading().close();
            } catch (IOException ie) {
                setErrorMessage(ie.getLocalizedMessage());
                setError(true);
            }
        }
        if (TheBufferWriting != null) {
            try {
                TheBufferWriting.close();
            } catch (IOException ie) {
                setErrorMessage(ie.getLocalizedMessage());
                setError(true);
            }
        }
        if (TheFileWriting != null) {
            try {
                TheFileWriting.close();
            } catch (IOException ie) {
                setErrorMessage(ie.getLocalizedMessage());
                setError(true);
            }
        }

    }
    private FileReader TheFileReading = null;
    BufferedReader TheBufferReading = null;
    FileWriter TheFileWriting = null;
    BufferedWriter TheBufferWriting = null;
    private boolean berror = false;
    private String sErrorMessage;
    private long SizeOfFile = 0;
    File TheFile = null;

    /**
     * @return the berror
     */
    public boolean isError() {
        return berror;
    }

    /**
     * @param error the error to set
     */
    public void setError(boolean error) {
        this.berror = error;
    }

    /**
     * @return the sErrorMessage
     */
    public String getErrorMessage() {
        return sErrorMessage;
    }

    /**
     * @param ErrorMessage the ErrorMessage to set
     */
    public void setErrorMessage(String ErrorMessage) {
        this.sErrorMessage = ErrorMessage;
    }

    /**
     *
     * @param tmpMyBuff
     * @return
     */
    public int LoadDatainBuffer(char[] tmpMyBuff) {

        //int ioff = 0;
        int ilast = 0;

        //while (ilast != -1) {
        try {
            ilast = getTheFileReading().read(tmpMyBuff);
            //   ilast = getTheFileReading().read(tmpMyBuff, ioff, 1);
            //      ioff++;

        } catch (IOException ex) {
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Estado {1}", new Object[]{WebFileBufferManager.class.getName(), ex.getLocalizedMessage()});
        }
        //}
        return ilast;
        // return ioff;
    }

    /**
     *
     * @param tmpMyBuff
     * @param size
     */
    public void WriteDataBuffer(char[] tmpMyBuff, int size) {

        try {
            TheFileWriting.write(tmpMyBuff, 0, size);

        } catch (IOException ex) {
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Estado {1}", new Object[]{WebFileBufferManager.class.getName(), ex.getLocalizedMessage()});
        }
    }

    /**
     *
     * @param sourcetmpElBuffer
     * @param destinotmpBufferGrab
     * @param tmpcantidad
     */
    public void CopyBuffer2Buffer(char[] sourcetmpElBuffer, char[] destinotmpBufferGrab, int tmpcantidad) {
        System.arraycopy(sourcetmpElBuffer, 0, destinotmpBufferGrab, 0, tmpcantidad);

    }

    /**
     * @return the TheFileReading
     */
    public FileReader getTheFileReading() {
        return TheFileReading;
    }

    /**
     * @param TheFileReading the TheFileReading to set
     */
    public void setTheFileReading(FileReader TheFileReading) {
        this.TheFileReading = TheFileReading;
    }

    /**
     * @return the SizeOfFile
     */
    public long getSizeOfFile() {
        return SizeOfFile;
    }

    /**
     * @param SizeOfFile the SizeOfFile to set
     */
    public void setSizeOfFile(long SizeOfFile) {
        this.SizeOfFile = SizeOfFile;
    }
}
