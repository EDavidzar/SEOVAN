/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/NetBeansModuleDevelopment-files/actionListener.java to edit this template
 */
package seovan.es.actions;

import static beleris.es.finalinformationmanager.MainErrorManager.AcceptInformation;
import static beleris.es.finalinformationmanager.MainErrorManager.mconfyes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.LifecycleManager;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import static seovan.es.generalmods.ProgramInstaller.Instalador;

/**
 *
 * @author Emilio David Diaus López 2023-2026
 */
@ActionID(
        category = "File",
        id = "seovan.es.actions.ExitAction"
)
@ActionRegistration(
        iconBase = "seovan/es/generalmods/icon-salir.png",
        displayName = "#CTL_ExitAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1360),
    @ActionReference(path = "Toolbars/File", position = 310),
    @ActionReference(path = "Shortcuts", name = "DS-X")
})
@Messages("CTL_ExitAction=Salir")
public final class ExitAction implements ActionListener {

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Instalador.WriteConfiguration();
        int ntf = AcceptInformation("¿Está seguro de que quiere salir del programa?");
        if (ntf == mconfyes) {
            Instalador.WriteConfiguration();
            LifecycleManager.getDefault().exit();
        }     
    }
}
