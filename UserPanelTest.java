import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;


class UserPanelTest {

    @BeforeAll
    static void headless() {
        // lets Swing run without opening windows
        System.setProperty("java.awt.headless", "true");
    }

    @Test
    void defaultSymbolIsS() throws Exception {
        final UserPanel[] holder = new UserPanel[1];
        SwingUtilities.invokeAndWait(() -> holder[0] = new UserPanel("Blue Player", null));
        UserPanel panel = holder[0];

        assertEquals("S", panel.getSelectedSymbol());
    }

    @Test
    void switchToO_andResetBackToS() throws Exception {
        final UserPanel[] holder = new UserPanel[1];
        SwingUtilities.invokeAndWait(() -> holder[0] = new UserPanel("Red Player", null));
        UserPanel panel = holder[0];

        // click the "O" checkbox by finding it among the children
        SwingUtilities.invokeAndWait(() -> {
            for (Component c : panel.getComponents()) {
                if (c instanceof JCheckBox cb && "O".equals(cb.getText())) {
                    cb.doClick(); // triggers selectO()
                }
            }
        });
        assertEquals("O", panel.getSelectedSymbol());

        // now reset -> should go back to "S"
        SwingUtilities.invokeAndWait(panel::resetPlayerMode);
        assertEquals("S", panel.getSelectedSymbol());
    }
}
