/*
 @author Emilio David Diaus López 2008-2026
 * *
 * *
 */
package seovan.es.generalmods;



import java.util.Set;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Emilio David Diaus López 2008-2026
 */
//"Ventana de Acci\u00f3n Principal"
public class SearchPanelActions {

    static public void CloseWSearchWindow() {
        Set<TopComponent> openTopComponents = WindowManager.getDefault().getRegistry().getOpened();
        TopComponent vatc = null;
        for (TopComponent stc : openTopComponents) {
            if ("Ventana de Búsqueda".equals(stc.getName())) {
                vatc = stc;
                if (vatc.isOpened()) {
                    vatc.close();
                    vatc.setVisible(false);
                }

            }
        }

    }

    static public void OpenSearchWindow(int IndexDataTotal) {
        // Set<TopComponent> openTopComponents = WindowManager.getDefault().getRegistry().getOpened();
        SearchSpaceTopComponent vatc = (SearchSpaceTopComponent) WindowManager.getDefault().findTopComponent("SearchSpaceTopComponent");
        if (vatc != null) {
            if ("Pestaña de búsqueda".equals(vatc.getName())) {

                if (!vatc.isOpened()) {
                    vatc.setVisible(true);
                    vatc.setIndexDataTotal(IndexDataTotal);
                    vatc.open();
                    vatc.requestActive();
                }
            }
        }
    }

    static public void ActivateSearchWindow() {
        TopComponent vatc = WindowManager.getDefault().findTopComponent("SearchSpaceTopComponent");
        if (vatc != null) {
            if ("Pestaña de búsqueda".equals(vatc.getName())) {
                if (vatc.isOpened()) {
                    vatc.requestActive();
                   // vatc.setVisible(true);
                }
            }
        }
    }

    static public void ActivatePrincipalWindow() {
        TopComponent vatc = WindowManager.getDefault().findTopComponent("PrincipalWindowTopComponent");
        if (vatc != null) {
            if ("Ventana Principal".equals(vatc.getName())) {
                if (vatc.isOpened()) {
                    vatc.requestActive();
                }
            }
        }
    }
}
