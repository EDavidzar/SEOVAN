/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seovan.es.generalmods;

import javax.swing.JPanel;
import java.util.Set;
import org.openide.windows.TopComponent;
import static org.openide.windows.WindowManager.getDefault;

/**
 *
 * @author Emilio David Diaus López 2008-2021
 */
public class AddPanelToSC {

    AddPanelToSC() {

    }

    public void AddPaneltoTopCom(JPanel JPA) {

        SearchWindowTopComponent ftc = null;
        Set<TopComponent> openTopComponents = getDefault().getRegistry().getOpened();

        for (TopComponent stc : openTopComponents) {
            if ("Pestaña Principal".equals(stc.getName())) {
                ftc = (SearchWindowTopComponent) stc;
                ftc.getjRightPanel().invalidate();
                ftc.getjRightPanel().removeAll();
                ftc.getjRightPanel().add(JPA);
                ftc.getjRightPanel().revalidate();
                ftc.getjRightPanel().repaint();
            }

        }
    }

    /**
     *
     */
    public static AddPanelToSC AddPanelToTCUtil = new AddPanelToSC();
}
