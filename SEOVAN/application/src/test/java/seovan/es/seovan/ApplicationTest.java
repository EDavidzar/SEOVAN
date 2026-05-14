package seovan.es.seovan;

import java.util.logging.Level;
import junit.framework.Test;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbTestCase;

/**
 *
 * @author edavid
 */
public class ApplicationTest extends NbTestCase {

    /**
     *
     * @return
     */
    public static Test suite() {
        return NbModuleSuite.createConfiguration(ApplicationTest.class).
                gui(false).
                failOnMessage(Level.WARNING).
                failOnException(Level.INFO).
                enableClasspathModules(false). 
                clusters(".*").
                suite();
    }

    /**
     *
     * @param n
     */
    public ApplicationTest(String n) {
        super(n);
    }

    /**
     *
     */
    public void testApplication() {
        // pass if there are merely no warnings/exceptions
        /* Example of using Jelly Tools (additional test dependencies required) with gui(true):
        new ActionNoBlock("Help|About", null).performMenu();
        new NbDialogOperator("About").closeByButton();
         */
    }

}
