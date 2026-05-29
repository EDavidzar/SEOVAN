/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seovan.es.actions;

import static beleris.es.finalutils.HelpUtils.HU;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;
import seovan.es.helpresenter.NewHelpPresenter;
import static seovan.es.programconfig.PConfigManager.PConfig;
/**
 *
 * @author Emilio David Diaus López 2023-2026
 */

@ActionID(
        category = "Edit",
        id = "seovan.es.actions.PresentHelp"
)
@ActionRegistration(
        displayName = "#CTL_PresentHelp"
)
@ActionReference(path = "Shortcuts", name = "F1")
@Messages("CTL_PresentHelp=Mostrar Ayuda")
public final class PresentHelp implements ActionListener {

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Frame f = WindowManager.getDefault().getMainWindow();
        //FHelpPresenter DHelp = new FHelpPresenter("Registro de control de Seguridad", "help/NewGeneralDataIntroPanels-PSRC.html");
        NewHelpPresenter DHelp = new NewHelpPresenter(f, true, "Ayuda del " + HU.getHelpTitleModule(), PConfig.getProgramPath() + "/help/" + HU.getHelpModule() + "/" + HU.getHelpFileModule());
        DHelp.setLocationRelativeTo(f);
        DHelp.setVisible(true);
    }
}
