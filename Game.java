import javax.swing.*;
import java.awt.*;

class Game extends JPanel {
    private static final long serialVersionUID = 1L;
    private final UserPanel bluePanel;
    private final UserPanel redPanel;
    private boolean modeS = true; // true for Simple mode, false for General mode
    private boolean playerTurn = true; // true for Blue, false for Red
    private Board board;
    private JLabel turnLabel;
    private JLabel blueScoreLabel; // Optional: Display Blue player's score
    private JLabel redScoreLabel;  // Optional: Display Red player's score
    private int blueSOSCount = 0;
    private int redSOSCount = 0;

    public Game() {
        setLayout(new BorderLayout());
        int boardSize = takeGameInfo();
        bluePanel = new UserPanel("Blue Player", this);
        redPanel = new UserPanel("Red Player", this);

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("SOS Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        topPanel.add(titlePanel);

        JPanel infoPanel = new JPanel();
        turnLabel = new JLabel("Current Player: Blue");
        turnLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(turnLabel);

        // Optional: Add score labels
        blueScoreLabel = new JLabel("Blue SOS: 0");
        redScoreLabel = new JLabel("Red SOS: 0");
        infoPanel.add(blueScoreLabel);
        infoPanel.add(redScoreLabel);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetGame());
        infoPanel.add(resetButton);

        topPanel.add(infoPanel);

        add(topPanel, BorderLayout.NORTH);
        add(bluePanel, BorderLayout.WEST);
        add(redPanel, BorderLayout.EAST);

        board = new Board(boardSize, this);
        add(board, BorderLayout.CENTER);
    }

    private int takeGameInfo() {
        String boardSize = (String) JOptionPane.showInputDialog(this, "Choose the board size", "Board Size", JOptionPane.QUESTION_MESSAGE, null, new String[]{"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"}, "8");
        if (boardSize == null) {
            System.exit(0); // Exit if no selection is made
        }
        String[] gameModes = {"Simple", "General"};
        int gameModeChoice = JOptionPane.showOptionDialog(this, "Choose the game mode", "Game Mode", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, gameModes, gameModes[0]);
        modeS = gameModeChoice == 0; // Set modeS to true for Simple, false for General

        return Integer.parseInt(boardSize);
    }

    public boolean isModeS() {
        return modeS;
    }

    public void alterTurn() {
        playerTurn = !playerTurn;
        turnLabel.setText(playerTurn ? "Current Player: Blue" : "Current Player: Red");
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    private void resetGame() {
        playerTurn = true;
        blueSOSCount = 0;
        redSOSCount = 0;
        turnLabel.setText("Current Player: Blue");
        blueScoreLabel.setText("Blue SOS: 0");
        redScoreLabel.setText("Red SOS: 0");

        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                board.getBoardLabels()[i][j].setText("");
            }
        }

        bluePanel.resetPlayerMode();
        redPanel.resetPlayerMode();

        revalidate();
        repaint();
    }

    public String getCurrentPlayerSymbol() {
        // Get the selected symbol for the current player (Blue or Red)
        return playerTurn ? bluePanel.getSelectedSymbol() : redPanel.getSelectedSymbol();
    }

    public void playerWon(String playerName) {
        String message = playerName + " won with an SOS!";
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }

    public void playerWonWithCount(String playerName, int sosCount) {
        String message = playerName + " won with " + sosCount + " SOS!";
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }

    public void checkForDraw() {
        if (isBoardFull()) {
            if (modeS) {
                if (blueSOSCount == 0 && redSOSCount == 0) {
                    // In Simple mode, if no SOS was formed and the board is full, it's a draw
                    JOptionPane.showMessageDialog(this, "It's a draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    resetGame();
                }
                // Else, a player has already won, so no action needed
            } else {
                // In General mode, compare SOS counts
                if (blueSOSCount > redSOSCount) {
                    playerWonWithCount("Blue Player", blueSOSCount);
                } else if (redSOSCount > blueSOSCount) {
                    playerWonWithCount("Red Player", redSOSCount);
                } else {
                    JOptionPane.showMessageDialog(this, "It's a draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    resetGame();
                }
            }
        }
    }

    public boolean isBoardFull() {
        BoardLabel[][] boardLabels = board.getBoardLabels();
        for (int i = 0; i < boardLabels.length; i++) {
            for (int j = 0; j < boardLabels[i].length; j++) {
                if (boardLabels[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void incrementBlueScore() {
        blueSOSCount++;
        blueScoreLabel.setText("Blue SOS: " + blueSOSCount);
    }

    public void incrementRedScore() {
        redSOSCount++;
        redScoreLabel.setText("Red SOS: " + redSOSCount);
    }
}
