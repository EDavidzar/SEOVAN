/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/NetBeansModuleDevelopment-files/actionListener.java to edit this template
 */
package seovan.es.actions;

import beleris.es.finaldbmanager.FDBMan;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Set;
import javax.swing.SwingUtilities;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import static org.openide.windows.WindowManager.getDefault;
import seovan.es.generalmods.PrincipalWindowTopComponent;
import seovan.es.panels.DialogOptionsPanel;
import static seovan.es.programconfig.PConfigManager.PConfig;

/**
 *
 * @author Emilio David Diaus López 2023-2026
 */
@ActionID(
        category = "Edit",
        id = "seovan.es.actions.Options"
)
@ActionRegistration(
        iconBase = "seovan/es/actions/O.png",
        displayName = "#CTL_Options"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1350),
    @ActionReference(path = "Toolbars/File", position = 300),
    @ActionReference(path = "Shortcuts", name = "DS-O")
})
@Messages("CTL_Options=Opciones")
public final class OptionsActions implements ActionListener {

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
       SwingUtilities.invokeLater(() -> {
            //FHelpPresenter DHelp = new FHelpPresenter("Registro de control de Seguridad", "help/NewGeneralDataIntroPanels-PSRC.html");
            DialogOptionsPanel OptionsDLG = new DialogOptionsPanel();
            DialogDescriptor dd = new DialogDescriptor(OptionsDLG, "Título del diálogo");
            Dialog dlg = DialogDisplayer.getDefault().createDialog(dd);
            dlg.setAlwaysOnTop(true);
            dlg.setVisible(true);
            if (dd.getValue() == DialogDescriptor.OK_OPTION) {
                PConfig.setDatabaseManagerinUse(OptionsDLG.getDatabasemanager());
                // TODO add your handling code here:
                if (OptionsDLG.getjRBMySQLsel().isSelected() == true) {
                    OptionsDLG.setDatabasemanager(FDBMan.DB_MYSQL_Selected);
                }
                if (OptionsDLG.getjRBSQLLiteSel().isSelected() == true) {
                    OptionsDLG.setDatabasemanager(FDBMan.DB_SQLite_Selected);
                }
                if (OptionsDLG.getjRBPostSQL().isSelected() == true) {
                    OptionsDLG.setDatabasemanager(FDBMan.DB_PostgreSQL_Selected);
                }
                if ((OptionsDLG.getJTFUserDB().getText() == null ? PConfig.getConfUserDB() != null : !OptionsDLG.getJTFUserDB().getText().equals(PConfig.getConfUserDB())) || (Arrays.toString(OptionsDLG.getJTFDBPass().getPassword()) == null ? PConfig.getConfPassDB() != null : !Arrays.toString(OptionsDLG.getJTFDBPass().getPassword()).equals(PConfig.getConfPassDB()))) {
                    String dbUser = OptionsDLG.getJTFUserDB().getText();
                    PConfig.setConfUserDB(dbUser);
                    String dbPass = Arrays.toString(OptionsDLG.getJTFDBPass().getPassword());
                    PConfig.setConfPassDB(dbPass);
                } // Acción al aceptar
            }
        }
        );
        
        Set<TopComponent> openTopComponents = getDefault().getRegistry().getOpened();
        PrincipalWindowTopComponent ptc = null;
        for (TopComponent stc : openTopComponents) {
            if ("Ventana Principal".equals(stc.getName())) {
                ptc = (PrincipalWindowTopComponent) stc;
            }
            if (ptc != null) {
                ptc.setDBToUse(PConfig.getDatabaseManagerinUse());
            }
        }
    }
}
