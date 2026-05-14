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
 * @author edavid
 */
@ActionID(
        category = "SEARCHING",
        id = "seovan.es.actions.Searching"
)
@ActionRegistration(
        iconBase = "seovan/es/actions/B.png",
        displayName = "#CTL_Searching"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1355),
    @ActionReference(path = "Toolbars/File", position = 305),
    @ActionReference(path = "Shortcuts", name = "DO-S")
})
@Messages("CTL_Searching=Buscar")
public final class Searching implements ActionListener {

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
                ptc.SearchingElement();
            }
        }

    }
}
