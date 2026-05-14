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
        id = "seovan.es.actions.ExportAction"
)
@ActionRegistration(
        iconBase = "seovan/es/generalmods/icon-export.png",
        displayName = "#CTL_ExportAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1000, separatorAfter = 1050),
    @ActionReference(path = "Toolbars/File", position = 254, separatorAfter = 272),
    @ActionReference(path = "Shortcuts", name = "DO-X"),
    @ActionReference(path = "JButtonSeparator", position = 273)
})
@Messages("CTL_ExportAction=Exportación")
public final class ExportAction implements ActionListener {

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
                ptc.LoadXMLTemplate();
                ptc.UpdateXMLTemplate();
                ptc.ExportXMLDocument();
            }
        }
    }
}
