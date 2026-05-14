/*
 * To change this template, choose Tools | Templates
 * *
 */
package beleris.es.finalinformationmanager;

import java.io.Serializable;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 ** @author Emilio David Diaus López 2014
 */
public class MainErrorManager {

    //TopComponent mytc = WindowManager.getDefault().findTopComponent("principalTopComponent");

    /**
     *
     */
    public static int mconfyes = 1,

    /**
     *
     */
    confno = 2,

    /**
     *
     */
    confcancel = 3;
    static String sYES = " Aceptar ", sNO = " No ", sCANCEL = " Cancelar ";
   
    /**
     *
     * @param sError
     */
    static public void ShowError(String sError) {
        //System.out.println(sError);
        LoggingManagerGenerator.getGlobalLogger().warning(sError);
    }

    /**
     *
     * @param sError
     */
    static public void ShowDError(String sError) {

        Util_CustomErrorInfoPanel myPanel = new Util_CustomErrorInfoPanel();
        myPanel.getjTAErrorMessage().setText(sError);
        NotifyDescriptor nd = new NotifyDescriptor(myPanel, "Error", // title of the dialog
                NotifyDescriptor.OK_CANCEL_OPTION, NotifyDescriptor.ERROR_MESSAGE, new Object[]{sYES, sCANCEL},
                NotifyDescriptor.OK_OPTION);
        Object notify = DialogDisplayer.getDefault().notify(nd);
        System.out.println(sError);
        LoggingManagerGenerator.getGlobalLogger().warning(sError);

    }

    /**
     *
     * @param sError
     */
    static public void ShowDWarning(String sError) {

        Util_CustomErrorInfoPanel myPanel = new Util_CustomErrorInfoPanel();
        myPanel.getjTAErrorMessage().setText(sError);
        NotifyDescriptor nd = new NotifyDescriptor(myPanel, "Advertencia", // title of the dialog
                NotifyDescriptor.OK_CANCEL_OPTION, NotifyDescriptor.WARNING_MESSAGE, new Object[]{sYES, sCANCEL},
                NotifyDescriptor.OK_OPTION);
        Object notify = DialogDisplayer.getDefault().notify(nd);
        System.out.println(sError);
        LoggingManagerGenerator.getGlobalLogger().warning(sError);

    }

    /**
     *
     * @param sInfo
     */
    static public void ShowInfo(String sInfo) {
        System.out.println(sInfo);
        LoggingManagerGenerator.getGlobalLogger().info(sInfo);
    }

    /**
     *
     * @param sInfo
     */
    static public void ShowDInfo(String sInfo) {
        Util_CustomErrorInfoPanel myPanel = new Util_CustomErrorInfoPanel();
        myPanel.getjTAErrorMessage().setText(sInfo);
        NotifyDescriptor nd = new NotifyDescriptor(myPanel, // instance of your panel
                "Información", NotifyDescriptor.OK_CANCEL_OPTION,
                NotifyDescriptor.INFORMATION_MESSAGE, new Object[]{sYES, sCANCEL},
                NotifyDescriptor.OK_OPTION
        );
        Object notify = DialogDisplayer.getDefault().notify(nd);
        System.out.println(sInfo);
        LoggingManagerGenerator.getGlobalLogger().info(sInfo);
    }

    /**
     *
     * @param sInfo
     * @return
     */
    static public int AcceptInformation(String sInfo) {

        Util_CustomErrorInfoPanel myPanel = new Util_CustomErrorInfoPanel();
        myPanel.getjTAErrorMessage().setText(sInfo);

        NotifyDescriptor nd = new NotifyDescriptor(myPanel, "Información",
                NotifyDescriptor.YES_NO_CANCEL_OPTION, NotifyDescriptor.QUESTION_MESSAGE,
                new Object[]{sYES, sNO, sCANCEL}, NotifyDescriptor.YES_OPTION
        );

        Object notify = DialogDisplayer.getDefault().notify(nd);
        LoggingManagerGenerator.getGlobalLogger().info(sInfo);
        int returnval = 0;

        if (notify == sYES) {
            returnval = mconfyes;
        }
        if (notify == sNO) {
            returnval = confno;
        }
        if (notify == sCANCEL) {
            returnval = confcancel;
        }
        return returnval;
    }

    /**
     *
     * @param sInfo
     * @param options
     * @param InitValue
     * @return
     */
    static public String GenerateNewSelectionButtons(String sInfo, String[] options, String InitValue) {

        Util_CustomErrorInfoPanel myPanel = new Util_CustomErrorInfoPanel();
        myPanel.getjTAErrorMessage().setText(sInfo);
        NotifyDescriptor nd = new NotifyDescriptor(myPanel, "Información",
                NotifyDescriptor.YES_NO_CANCEL_OPTION, NotifyDescriptor.QUESTION_MESSAGE,
                options, InitValue
        );

        String notify = (String) DialogDisplayer.getDefault().notify(nd);
        LoggingManagerGenerator.getGlobalLogger().info(sInfo);

        return notify;
    }

    /**
     *
     * @param sError
     */
    static public void ShowCriticalError(String sError) {
        // System.out.println(sError);
        LoggingManagerGenerator.getGlobalLogger().severe(sError);
    }

    /**
     *
     * @param sError
     */
    static public void ShowDCriticalError(String sError) {
        Util_CustomErrorInfoPanel myPanel = new Util_CustomErrorInfoPanel();
        myPanel.getjTAErrorMessage().setText(sError);
        NotifyDescriptor nd = new NotifyDescriptor(myPanel,
                "Error Crítico",
                NotifyDescriptor.OK_CANCEL_OPTION,
                NotifyDescriptor.ERROR_MESSAGE,
                new Object[]{sYES, sCANCEL}, NotifyDescriptor.OK_OPTION
        );

        Object notify = DialogDisplayer.getDefault().notify(nd);
        System.out.println(sError);
        LoggingManagerGenerator.getGlobalLogger().severe(sError);
    }

}
