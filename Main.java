import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game(); // The constructor of Game handles mode selection and board size
            JFrame frame = new JFrame("SOS Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(game);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
