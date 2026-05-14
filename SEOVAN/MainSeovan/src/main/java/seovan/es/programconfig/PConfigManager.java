/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seovan.es.programconfig;

import static beleris.es.finaldbmanager.FDBMan.DB_MYSQL_Selected;
import static beleris.es.finalinformationmanager.MainErrorManager.ShowDError;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 *
 * @author Emilio David Diaus López 2023-2025
 */
public class PConfigManager implements Serializable {

    private String ProgramHomePath = System.getProperty("user.home");
    private String ProgramPath = "";
    private String ProgramXMLTemplatePath = "";
    private boolean installed = false;
    private int DatabaseManagerinUse = DB_MYSQL_Selected;

    /**
     *
     */
    public static PConfigManager PConfig = new PConfigManager();
    private static final long serialVersionUID = 1621L;
    final String ConfigPath = ProgramHomePath;
    private String confUserDB="root";
    private String confPassDB="FR6rjcwMeVCgdWQzDxG5-";
   
    /**
     *
     */
    public PConfigManager() {
        Path path = Paths.get("");
        String ThePath = path.toAbsolutePath().toString();
        //setProgramPath(ThePath);b
        ProgramPath=ThePath;           
        setProgramXMLTemplatePath(getProgramPath() + File.separator + "config" + File.separator + "xml-export-template.xml");
        setDatabaseManagerinUse(DatabaseManagerinUse);
    }

    /**
     * @return the ProgramPath
     */
    public String getProgramPath() {
        return ProgramPath;
    }

    /**
     * @param ProgramPath the ProgramPath to set
     */
    public void setProgramPath(String ProgramPath) {
        this.ProgramPath = ProgramPath;
    }

    /**
     * @return the ProgramXMLTemplatePath
     */
    public String getProgramXMLTemplatePath() {
        return ProgramXMLTemplatePath;
    }

    /**
     * @param ProgramXMLTemplatePath the ProgramXMLTemplatePath to set
     */
    public void setProgramXMLTemplatePath(String ProgramXMLTemplatePath) {
        this.ProgramXMLTemplatePath = ProgramXMLTemplatePath;
    }

    /**
     * @return the installed
     */
    public boolean isInstalled() {
        return installed;
    }

    /**
     * @param installed the installed to set
     */
    public void setInstalled(boolean installed) {
        this.installed = installed;
    }

    /**
     *
     * @return
     */
    public boolean ProgramConfigured() {
        String ConfigFilePath = ConfigPath + File.separator + "seovan.cfg";
        boolean value = false;
        File FiletoCheck = new File(ConfigFilePath);
        if (FiletoCheck.exists() && FiletoCheck.isFile()) {
            value = true;
        }
        return value;
    }

    /**
     *
     */
    public void InstalationComplete() {
        setInstalled(true);
        String ConfigFilePath = ConfigPath + File.separator + "seovan.cfg";
        boolean filecreated = CreateFile(ConfigFilePath);
    }

    boolean CreateFile(String filepath) {
        boolean value = false;
        try {
            File FiletoCreate = new File(filepath);
            value = FiletoCreate.createNewFile();
        } catch (IOException ex) {
            ShowDError("Error al crear archivo de confguración " + ex.getLocalizedMessage());
        }
        return value;
    }

    /**
     * @return the ProgramHomePath
     */
    public String getProgramHomePath() {
        return ProgramHomePath;
    }

    /**
     * @param ProgramHomePath the ProgramHomePath to set
     */
    public void setProgramHomePath(String ProgramHomePath) {
        this.ProgramHomePath = ProgramHomePath;
    }

    /**
     * @return the DatabaseManagerinUse
     */
    public int getDatabaseManagerinUse() {
        return DatabaseManagerinUse;
    }

    /**
     * @param DatabaseManagerinUse the DatabaseManagerinUse to set
     */
    public void setDatabaseManagerinUse(int DatabaseManagerinUse) {
        this.DatabaseManagerinUse = DatabaseManagerinUse;
    }

    /**
     * @return the confUserDB
     */
    public String getConfUserDB() {
        return confUserDB;
    }

    /**
     * @param confUserDB the confUserDB to set
     */
    public void setConfUserDB(String confUserDB) {
        this.confUserDB = confUserDB;
    }

    /**
     * @return the confPassDB
     */
    public String getConfPassDB() {
        return confPassDB;
    }

    /**
     * @param confPassDB the confPassDB to set
     */
    public void setConfPassDB(String confPassDB) {
        this.confPassDB = confPassDB;
    }

}
