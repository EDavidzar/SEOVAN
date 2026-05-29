/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beleris.es.finalutils;

/**
 *
 * @author Emilio David Diaus López 2008-2026
 */
public class HelpUtils {

    /**
     *
     */
    public static HelpUtils HU = new HelpUtils();

    private String HelpModule = "";
    // Variables de la ayuda
    private String HelpFileModule = "";
    private String HelpTitleModule = "";

    private String BackupHelpModule = "";
    // Variables de la ayuda
    private String BackupHelpFileModule = "";
    private String BackupHelpTitleModule = "";
   
    /**
     *
     */
    public HelpUtils() {
        HelpModule = "";
        // Variables de la ayuda
        HelpFileModule = "";
        HelpTitleModule = "";

        BackupHelpModule = "";
        // Variables de la ayuda
        BackupHelpFileModule = "";
        BackupHelpTitleModule = "";
    }

    /**
     * @return the HelpModule
     */
    public String getHelpModule() {
        return HelpModule;
    }

    /**
     * @param HelpModule the HelpModule to set
     */
    public void setHelpModule(String HelpModule) {
        this.HelpModule = HelpModule;
    }

    /**
     * @return the HelpFileModule
     */
    public String getHelpFileModule() {
        return HelpFileModule;
    }

    /**
     * @param HelpFileModule the HelpFileModule to set
     */
    public void setHelpFileModule(String HelpFileModule) {
        this.HelpFileModule = HelpFileModule;
    }

    /**
     * @return the HelpTitleModule
     */
    public String getHelpTitleModule() {
        return HelpTitleModule;
    }

    /**
     * @param HelpTitleModule the HelpTitleModule to set
     */
    public void setHelpTitleModule(String HelpTitleModule) {
        this.HelpTitleModule = HelpTitleModule;
    }

    /**
     *
     */
    public void BackupHelpconfig() {
        setBackupHelpModule(getHelpModule());
        setBackupHelpFileModule(getHelpFileModule());
        setBackupHelpTitleModule(getHelpTitleModule());
    }

    /**
     *
     */
    public void RestoreHelpconfig() {
        setHelpModule(getBackupHelpModule());
        setHelpFileModule(getBackupHelpFileModule());
        setHelpTitleModule(getBackupHelpTitleModule());
    }

    /**
     * @return the BackupHelpModule
     */
    public String getBackupHelpModule() {
        return BackupHelpModule;
    }

    /**
     * @param BackupHelpModule the BackupHelpModule to set
     */
    public void setBackupHelpModule(String BackupHelpModule) {
        this.BackupHelpModule = BackupHelpModule;
    }

    /**
     * @return the BackupHelpFileModule
     */
    public String getBackupHelpFileModule() {
        return BackupHelpFileModule;
    }

    /**
     * @param BackupHelpFileModule the BackupHelpFileModule to set
     */
    public void setBackupHelpFileModule(String BackupHelpFileModule) {
        this.BackupHelpFileModule = BackupHelpFileModule;
    }

    /**
     * @return the BackupHelpTitleModule
     */
    public String getBackupHelpTitleModule() {
        return BackupHelpTitleModule;
    }

    /**
     * @param BackupHelpTitleModule the BackupHelpTitleModule to set
     */
    public void setBackupHelpTitleModule(String BackupHelpTitleModule) {
        this.BackupHelpTitleModule = BackupHelpTitleModule;
    }

    /**
     *
     * @param title
     * @param tempHM
     * @param tempHFM
     */
    public void setHelpConfig(String title, String tempHM, String tempHFM) {
        BackupHelpconfig();
        setHelpTitleModule(title);
        setHelpModule(tempHM);
        setHelpFileModule(tempHFM);
    }

}
