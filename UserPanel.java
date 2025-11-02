import javax.swing.*;
import java.awt.*;

class UserPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JCheckBox sCheckBox;
    private final JCheckBox oCheckBox;
    private boolean isSSelected = true; // Default to "S"

    public UserPanel(String name, Game game) {
        setLayout(new GridLayout(3, 1));

        JLabel playerLabel = new JLabel(name);
        playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        sCheckBox = new JCheckBox("S");
        oCheckBox = new JCheckBox("O");

        sCheckBox.setSelected(true); // Default selection
        oCheckBox.setSelected(false);

        sCheckBox.addActionListener(e -> selectS());
        oCheckBox.addActionListener(e -> selectO());

        add(playerLabel);
        add(sCheckBox);
        add(oCheckBox);
    }

    private void selectS() {
        isSSelected = true;
        sCheckBox.setSelected(true);
        oCheckBox.setSelected(false);
    }

    private void selectO() {
        isSSelected = false;
        sCheckBox.setSelected(false);
        oCheckBox.setSelected(true);
    }

    public String getSelectedSymbol() {
        return isSSelected ? "S" : "O";
    }

    public void resetPlayerMode() {
        selectS(); // Reset to default selection "S"
    }
}
