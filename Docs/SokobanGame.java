import java.awt.*;
import javax.swing.*;

public class SokobanGame extends JFrame {
    private JPanel gamePanel;
    private JLabel[][] board;

    // game board size
    private int numRows = 10;
    private int numCols = 10;

    // player position
    private int playerRow = 5;
    private int playerCol = 5;

    public SokobanGame() {
        // create the game board
        gamePanel = new JPanel(new GridLayout(numRows, numCols));
        board = new JLabel[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                board[row][col] = new JLabel("", SwingConstants.CENTER);
                board[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gamePanel.add(board[row][col]);
            }
        }

        // initialize the game board with some walls, boxes, and targets
        board[2][2].setText("#"); // wall
        board[2][3].setText("$"); // box
        board[2][4].setText("@"); // player
        board[3][3].setText("."); // target

        // add the game board to the JFrame
        add(gamePanel);

        // set up the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sokoban Game");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        // handle keyboard events
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                movePlayer(evt.getKeyCode());
            }
        });
        setFocusable(true);
    }

    private void movePlayer(int keyCode) {
        int newRow = playerRow;
        int newCol = playerCol;
        switch (keyCode) {
            case KeyEvent.VK_UP:
                newRow--;
                break;
            case KeyEvent.VK_DOWN:
                newRow++;
                break;
            case KeyEvent.VK_LEFT:
                newCol--;
                break;
            case KeyEvent.VK_RIGHT:
                newCol++;
                break;
        }
        if (isValidMove(newRow, newCol)) {
            // update the game board
            board[playerRow][playerCol].setText("");
            board[newRow][newCol].setText("@");
            playerRow = newRow;
            playerCol = newCol;
        }
    }

    private boolean isValidMove(int newRow, int newCol) {
        //
