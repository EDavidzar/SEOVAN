/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/NetBeansModuleDevelopment-files/actionListener.java to edit this template
 */
package seovan.es.actions;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import static org.openide.windows.WindowManager.getDefault;
import seovan.es.generalmods.PrincipalWindowTopComponent;
import seovan.es.programconfig.ProgramOptions;
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
        Frame f = WindowManager.getDefault().getMainWindow();
        //FHelpPresenter DHelp = new FHelpPresenter("Registro de control de Seguridad", "help/NewGeneralDataIntroPanels-PSRC.html");
        ProgramOptions OptionsDLG = new ProgramOptions(f, true);
        OptionsDLG.setLocationRelativeTo(f);
        OptionsDLG.setVisible(true);
        Set<TopComponent> openTopComponents = getDefault().getRegistry().getOpened();
        PrincipalWindowTopComponent ptc = null;
        for (TopComponent stc : openTopComponents) {
            if ("Ventana Principal".equals(stc.getName())) {
                ptc = (PrincipalWindowTopComponent) stc;
            }
            if (ptc != null) {
                ptc.setDBToUse(OptionsDLG.getDatabasemanager());
            }
        }
    }
}
