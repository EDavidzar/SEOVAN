/*
 * To change this template, choose Tools | Templates
 * *
 */
package beleris.es.finalutils;

import beleris.es.finalinformationmanager.LoggingManagerGenerator;
import beleris.es.finalinformationmanager.MainErrorManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;

/**
 *
 * @author Emilio David Diaus López 2008-2025
 */
public final class FileManagerGenerator {

    private FileInputStream fis;
    private FileOutputStream fos;
    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;

//    private DataInputStream dis;
//    private DataOutputStream dos;
    private BufferedReader brd;
    private BufferedWriter bfd;

    /**
     *
     */
    public int ifmCreateWrite = 0x0001;
    private int ifmRead = 0x0002;
    private int iFileMode = ifmCreateWrite;
    private final int ierrCodeFileNotExist = 0x0001;
    private final int ierrCanWrite = 0x0002;
    private final int ierrCanRead = 0x0004;
    private boolean berror = false;
    private int ierrorcode = 0;
  
    /**
     *
     */
    public FileManagerGenerator() {

    }

    /**
     *
     * @param sFileName
     */
    public FileManagerGenerator(String sFileName) {
        if (FileExists(sFileName)) {
            Init(ifmRead, sFileName);
        } else {
            berror = true;
            ierrorcode = ierrCodeFileNotExist;
        }

    }

    /**
     *
     * @param Mode
     * @param sFileName
     */
    public FileManagerGenerator(int Mode, String sFileName) {
        Init(Mode, sFileName);
    }

    /**
     *
     * @param Mode
     * @param sFileName
     */
    public void Init(int Mode, String sFileName) {

        setiFileMode(Mode);
        if (getiFileMode() == getIfmRead()) {
            try {
                setFis(new FileInputStream(sFileName));
                setBrd(new BufferedReader(new InputStreamReader(getFis())));

            } catch (FileNotFoundException ex) {
                LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
                MainErrorManager.ShowDError(" Error al abrir fichero.  " + ex.getLocalizedMessage());
                berror = true;
                ierrorcode = getIerrCodeFileNotExist();

            } finally {
                WhenErrorCloseAll();
            }
            if ((getFis() != null) && berror == false)
            try {
                ois = new ObjectInputStream(getFis());
            } catch (IOException ex) {
                LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
                MainErrorManager.ShowDError(" Error de entrada/salida.  " + ex.getLocalizedMessage());
                berror = true;
                ierrorcode = ierrCanRead;
            }
        }
        if (getiFileMode() == getIfmCreateWrite()) {
            try {
                setFos(new FileOutputStream(sFileName));
                bfd = new BufferedWriter(new OutputStreamWriter(getFos()));

            } catch (FileNotFoundException ex) {
                LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
                MainErrorManager.ShowDError(" Error al abrir fichero.  " + ex.getLocalizedMessage());
                berror = true;
                ierrorcode = getIerrCodeFileNotExist();
            } finally {
                WhenErrorCloseAll();
            }
            if ((getFos() != null) && berror == false)
            try {
                oos = new ObjectOutputStream(getFos());
            } catch (IOException ex) {
                LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
                MainErrorManager.ShowDError(" Error de entrada/salida.  " + ex.getLocalizedMessage());
                berror = true;
                ierrorcode = ierrCanWrite;
            }
        }

    }

    private void WhenErrorCloseAll() {
        if (berror) {
            CloseAll();
        }
    }

    /**
     *
     * @return
     */
    public Object ReadObject() {
        Object oTmp2 = null;
        try {
            oTmp2 = ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
            MainErrorManager.ShowDError(" Error de entrada/salida al leer un objeto.  " + ex.getLocalizedMessage());
            berror = true;
            ierrorcode = ierrCanRead;
        }
        return oTmp2;
    }

    /**
     *
     * @param oTmp
     */
    public void WriteObject(Object oTmp) {
        try {
            oos.writeObject(oTmp);
        } catch (IOException ex) {
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
            MainErrorManager.ShowDError(" Error de entrada/salida al escribir un objeto.  " + ex.getLocalizedMessage());
            berror = true;
            ierrorcode = ierrCanWrite;
        }
    }

    /**
     *
     * @param sTmp
     */
    public void WriteOneString(String sTmp) {
        try {
            bfd.write(sTmp);
            FlushBuffers();
        } catch (IOException ex) {
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
            MainErrorManager.ShowDError(" Error de entrada/salida al escribir una cadena.  " + ex.getLocalizedMessage());
            berror = true;
            ierrorcode = ierrCanWrite;
        } finally {
            WhenErrorCloseAll();
        }
    }

    /**
     *
     * @param iTmp
     */
    public void WriteInt(int iTmp) {
        try {
            bfd.write(iTmp);
            FlushBuffers();
        } catch (IOException ex) {
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
            MainErrorManager.ShowDError(" Error de entrada/salida al escribir un entero.  " + ex.getLocalizedMessage());
            berror = true;
            ierrorcode = ierrCanWrite;
        } finally {
            WhenErrorCloseAll();
        }
    }

    /**
     *
     * @param chsTmp
     */
    public void WritecharArray(char[] chsTmp) {
        try {
            bfd.write(chsTmp);
            FlushBuffers();
        } catch (IOException ex) {
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
            MainErrorManager.ShowDError(" Error de entrada/salida al escribir un buffer de cadena.  " + ex.getLocalizedMessage());
            berror = true;
            ierrorcode = ierrCanWrite;
        } finally {
            WhenErrorCloseAll();
        }
    }

    /**
     *
     * @param sTmp
     */
    public void WriteString(String sTmp) {
        try {
            bfd.write(sTmp);
            bfd.newLine();
            FlushBuffers();
        } catch (IOException ex) {
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
            MainErrorManager.ShowDError(" Error de entrada/salida al escribir una cadena.  " + ex.getLocalizedMessage());
            berror = true;
            ierrorcode = ierrCanWrite;
        } finally {
            WhenErrorCloseAll();
        }
    }

    /**
     *
     */
    public void FlushBuffers() {
        try {
            bfd.flush();
        } catch (IOException ex) {
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
            MainErrorManager.ShowDError(" Error de entrada/salida al escribir una cadena.  " + ex.getLocalizedMessage());
            berror = true;
            ierrorcode = ierrCanWrite;
        } finally {
            WhenErrorCloseAll();
        }
    }

    /**
     *
     * @return
     */
    public String ReadString() {
        String sTmp = "";
        try {
            sTmp = getBrd().readLine();

        } catch (IOException ex) {
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
            MainErrorManager.ShowDError(" Error de entrada/salida al leer una cadena.  " + ex.getLocalizedMessage());
            berror = true;
            ierrorcode = ierrCanRead;
        } finally {
            WhenErrorCloseAll();
        }

        return sTmp;
    }

    /**
     *
     * @return
     */
    public int ReadInt() {
        int iTmp = 0;
        try {
            iTmp = getBrd().read();

        } catch (IOException ex) {
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
            MainErrorManager.ShowDError(" Error de entrada/salida al leer un entero.  " + ex.getLocalizedMessage());
            berror = true;
            ierrorcode = ierrCanRead;
        } finally {
            WhenErrorCloseAll();
        }

        return iTmp;
    }

    /**
     *
     * @param size
     * @return
     */
    public char[] ReadcharArray(int size) {

        char[] chsTmp = new char[size];
        try {
            getBrd().read(chsTmp);
        } catch (IOException ex) {
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
            MainErrorManager.ShowDError(" Error de entrada/salida al leer una cadena.  " + ex.getLocalizedMessage());
            berror = true;
            ierrorcode = ierrCanRead;
        } finally {
            WhenErrorCloseAll();
        }

        return chsTmp;
    }

    /**
     * @return the iFileMode
     */
    public int getiFileMode() {
        return iFileMode;
    }

    /**
     * @param iFileMode the iFileMode to set
     */
    public void setiFileMode(int iFileMode) {
        this.iFileMode = iFileMode;
    }

    /**
     *
     */
    public void CloseAll() {
        if (getBrd() != null) {
            try {
                getBrd().close();
            } catch (IOException ex) {
                LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
                MainErrorManager.ShowDError(" Error al cerrar un archivo.  " + ex.getLocalizedMessage());
            }
            setBrd(null);
        }

        if (bfd != null) {
            try {
                bfd.close();

            } catch (IOException ex) {
                LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
                MainErrorManager.ShowDError(" Error al cerrar un archivo.  " + ex.getLocalizedMessage());
            }
            bfd = null;
        }

        if (getFis() != null) {
            try {
                getFis().close();

            } catch (IOException ex) {
                LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
                MainErrorManager.ShowDError(" Error al cerrar un archivo.  " + ex.getLocalizedMessage());
            }
            setFis(null);
        }

        if (getFos() != null) {
            try {
                getFos().close();

            } catch (IOException ex) {
                LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Clase {1}", new Object[]{ex.getLocalizedMessage(), ex.getClass()});
                MainErrorManager.ShowDError(" Error al cerrar un archivo.  " + ex.getLocalizedMessage());
            }
            setFos(null);
        }

    }

    /**
     *
     * @param s
     * @return
     */
    public boolean FileExists(String s) {
        boolean result = false;
        File fc = null;
        fc = new File(s);
        //spath=fc.getAbsolutePath();
        result = fc.exists();
        return result;

    }

    /**
     *
     * @param s
     * @return
     */
    public long FileSize(String s) {
        long result = 0;
        File fc = null;
        fc = new File(s);
        //spath=fc.getAbsolutePath();
        result = fc.length();
        return result;
    }

    /**
     *
     * @param s
     */
    public void CreateDir(String s) {
        File fc = null;
        fc = new File(s);
        //spath=fc.getAbsolutePath();
        fc.mkdirs();
    }

    /**
     * @return the ierrorcode
     */
    public int getErrorcode() {
        return ierrorcode;
    }

    /**
     * @param errorcode the errorcode to set
     */
    public void setErrorcode(int errorcode) {
        this.ierrorcode = errorcode;
    }

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
     * @return the fis
     */
    public FileInputStream getFis() {
        return fis;
    }

    /**
     * @param fis the fis to set
     */
    public void setFis(FileInputStream fis) {
        this.fis = fis;
    }

    /**
     * @return the brd
     */
    public BufferedReader getBrd() {
        return brd;
    }

    /**
     * @param brd the brd to set
     */
    public void setBrd(BufferedReader brd) {
        this.brd = brd;
    }

    /**
     * @return the fos
     */
    public FileOutputStream getFos() {
        return fos;
    }

    /**
     * @param fos the fos to set
     */
    public void setFos(FileOutputStream fos) {
        this.fos = fos;
    }

    /**
     * @return the ifmCreateWrite
     */
    public int getIfmCreateWrite() {
        return ifmCreateWrite;
    }

    /**
     * @param ifmCreateWrite the ifmCreateWrite to set
     */
    public void setIfmCreateWrite(int ifmCreateWrite) {
        this.ifmCreateWrite = ifmCreateWrite;
    }

    /**
     * @return the ifmRead
     */
    public int getIfmRead() {
        return ifmRead;
    }

    /**
     * @param ifmRead the ifmRead to set
     */
    public void setIfmRead(int ifmRead) {
        this.ifmRead = ifmRead;
    }

    /**
     * @return the ierrCodeFileNotExist
     */
    public int getIerrCodeFileNotExist() {
        return ierrCodeFileNotExist;
    }
}
