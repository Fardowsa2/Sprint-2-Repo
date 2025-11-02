import javax.swing.*;

class BoardLabel extends JLabel {
    private final int i;
    private final int j;

    public BoardLabel(int i, int j) {
        super("");
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
