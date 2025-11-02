import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Board extends JPanel {
    private static final long serialVersionUID = 1L; 
    private final BoardLabel[][] boardLabels;
    private final Game game;

    public Board(int size, Game game) {
        this.game = game;
        setLayout(new GridLayout(size, size));
        boardLabels = new BoardLabel[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardLabels[i][j] = new BoardLabel(i, j);
                boardLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                boardLabels[i][j].setPreferredSize(new Dimension(50, 50));
                boardLabels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                boardLabels[i][j].setFont(new Font("Arial", Font.BOLD, 24));
                boardLabels[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        BoardLabel label = (BoardLabel) e.getSource();
                        if (!label.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(Board.this, "Invalid Move: This cell is already occupied. Try again!", "Invalid Move", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // Place the current player's selected symbol on the board
                        String symbol = game.getCurrentPlayerSymbol();
                        label.setText(symbol);

                        boolean sosFormed = checkForSOS(label.getI(), label.getJ());

                        if (sosFormed) {
                            // Immediately declare a winner in Simple Mode on the first SOS formation
                            if (game.isModeS()) {  // Simple Mode: First SOS wins
                                String playerName = game.isPlayerTurn() ? "Blue Player" : "Red Player";
                                game.playerWon(playerName); // Declare winner and end game
                                return; // Exit to prevent further actions
                            } else {
                                // In General Mode, update the score and continue the game
                                if (game.isPlayerTurn()) { // Blue player
                                    game.incrementBlueScore();
                                } else { // Red player
                                    game.incrementRedScore();
                                }
                            }
                        }

                        // Check for a draw before switching turns
                        game.checkForDraw();

                        // Switch turns automatically after each valid move
                        game.alterTurn();
                    }
                });
                add(boardLabels[i][j]);
            }
        }
    }

    /**
     * Returns the size of the board.
     */
    public int getBoardSize() {
        return boardLabels.length;
    }

    /**
     * Provides access to the board labels.
     */
    public BoardLabel[][] getBoardLabels() { 
        return boardLabels;
    }

    /**
     * Checks if placing a symbol at (x, y) forms an "S-O-S" sequence in any direction.
     */
    private boolean checkForSOS(int x, int y) {
        // Check all eight possible directions for 'S-O-S'
        return (checkPattern(x, y, -1,  0, "S", "O", "S") || // Left
                checkPattern(x, y,  1,  0, "S", "O", "S") || // Right
                checkPattern(x, y,  0, -1, "S", "O", "S") || // Up
                checkPattern(x, y,  0,  1, "S", "O", "S") || // Down
                checkPattern(x, y, -1, -1, "S", "O", "S") || // Top-Left Diagonal
                checkPattern(x, y,  1,  1, "S", "O", "S") || // Bottom-Right Diagonal
                checkPattern(x, y, -1,  1, "S", "O", "S") || // Top-Right Diagonal
                checkPattern(x, y,  1, -1, "S", "O", "S"));  // Bottom-Left Diagonal
    }

    /**
     * Checks for a specific "S-O-S" pattern starting from (x, y) in the direction (dx, dy).
     */
    private boolean checkPattern(int x, int y, int dx, int dy, String first, String second, String third) {
        int midX = x + dx;
        int midY = y + dy;
        int endX = x + 2 * dx;
        int endY = y + 2 * dy;
        
        // Ensure all positions are within bounds
        if (midX >= 0 && midY >= 0 && midX < boardLabels.length && midY < boardLabels.length &&
            endX >= 0 && endY >= 0 && endX < boardLabels.length && endY < boardLabels.length) {
            String midSymbol = boardLabels[midX][midY].getText();
            String endSymbol = boardLabels[endX][endY].getText();
            return boardLabels[x][y].getText().equals(first) &&
                   midSymbol.equals(second) &&
                   endSymbol.equals(third);
        }
        return false;
    }

    /**
     * (Optional) Counts the number of SOS sequences for a given symbol.
     * 
     * Note: This method is currently not used since SOS counts are tracked incrementally.
     * You can remove or modify this method based on your requirements.
     */
    public int countSOS(String symbol) {
        // Implementation not needed as counts are updated incrementally
        return 0;
    }
}
