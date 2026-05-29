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
 * @author Emilio David Diaus López 2023-2026
 */
@ActionID(
        category = "File",
        id = "seovan.es.actions.Evaluation"
)
@ActionRegistration(
        iconBase = "seovan/es/generalmods/icon-eval.png",
        displayName = "#CTL_Evaluation"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1200, separatorAfter = 1250),
    @ActionReference(path = "Toolbars/File", position = 218, separatorAfter = 236),
    @ActionReference(path = "Shortcuts", name = "DS-E")
})
@Messages("CTL_Evaluation=Evaluación")
public final class EvaluationAction implements ActionListener {

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
                ptc.SelectEvaluationPanel();
            }
        }
    }
}
