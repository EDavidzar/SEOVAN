/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/NetBeansModuleDevelopment-files/actionListener.java to edit this template
 */
package seovan.es.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import static org.openide.windows.WindowManager.getDefault;
import seovan.es.generalmods.PrincipalWindowTopComponent;
/**
 *
 * @author Emilio David Diaus López 2023-2025
 */
@ActionID(
        category = "File",
        id = "seovan.es.actions.ImportXMLFiles"
)
@ActionRegistration(
        iconBase = "seovan/es/generalmods/icon-import.png",
        displayName = "#CTL_ImportXMLFiles"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 900, separatorBefore = 850),
    @ActionReference(path = "Shortcuts", name = "DS-I"),
    @ActionReference(path = "Toolbars/File", position = 236, separatorBefore = 218)

})
@Messages("CTL_ImportXMLFiles=Importación")
public final class ImportAction implements ActionListener {

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Set<TopComponent> openTopComponents = getDefault().getRegistry().getOpened();
        PrincipalWindowTopComponent ptc = null;
        for (TopComponent stc : openTopComponents) {
            if ("Ventana Principal".equals(stc.getName())) {
                ptc = (PrincipalWindowTopComponent) stc;
            }
            if (ptc != null) {
                ptc.SelectandImportDocument();
                ptc.ImportAnalisysData();
                ptc.ImportEvalData();
                ptc.ReloadData();
            }
        }

    }
}
