import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {

    // image that represents the player's position on the board
    private BufferedImage image;
    // current position of the player on the board grid
    private Point pos;
    // keep track of the player's score
    private int score;
    // recording movement to stop character at walls
    private int[] lastPos = {0,0};

    private int key;

    public Player() {
        // load the assets
        loadImage();

        // initialize the state
        pos = new Point(0, 0);
        score = 0;
    }

    private void loadImage() {
        try {
            // loading player icon
            image = ImageIO.read(new File("images/player.jpg"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        // used to resize the image used as player icon
        // Image.SCALE_SMOOTH used instead of Image.SCALE_DEFAULT or others for a better quality image when resizing
        Image newImage = image.getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, Image.SCALE_SMOOTH);
        // using pos.x instead of pos.getX() in order to recieve an int rather than a double
        g.drawImage(
            newImage,
            pos.x * Board.TILE_SIZE,
            pos.y * Board.TILE_SIZE,
            observer
        );
    }

    public void keyPressed(KeyEvent e) {
        // keyboard event so that we can compare it to KeyEvent constants
        key = e.getKeyCode();
        // recording position before movement to send the player back if entering a wall
        lastPos[0] = pos.x;
        lastPos[1] = pos.y;
        // checking what way to move based on the key pressed
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            pos.translate(0, -1);
        } else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            pos.translate(-1, 0);
        } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            pos.translate(0, 1);
        } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            pos.translate(1, 0);
        } 
        // prevent the player from moving off the edge of the board sideways
        if (pos.x < 0 || pos.x >= Board.COLUMNS || pos.y < 0 || pos.y >= Board.ROWS) {
            pos.x = lastPos[0];    
            pos.y = lastPos[1];    
        } 
        // will allow me to add walls for the maze later
        // if (Board.checkIfPlayerOnWall()) {
        //     pos.x = lastPos[0];
        //     pos.y = lastPos[1];
        // }

        // ************ OLD CODE **********************
        // switch (key) {
        //     case (KeyEvent.VK_UP):
        //         pos.translate(0, -1);
        //         lastMove = 1;
        //         lastAxis = 0;
        //         break;
        //     case (KeyEvent.VK_W):
        //         pos.translate(0, -1);
        //         lastMove = 1;
        //         lastAxis = 0;
        //         break;
        //     case (KeyEvent.VK_LEFT):
        //         pos.translate(-1, 0);
        //         lastMove = 1;
        //         lastAxis = 1;
        //         break;
        //     case (KeyEvent.VK_A):
        //         pos.translate(-1, 0);
        //         lastMove = 1;
        //         lastAxis = 1;
        //         break;
        //     case (KeyEvent.VK_DOWN):
        //         pos.translate(0, 1);
        //         lastMove = -1;
        //         lastAxis = 0;
        //         break;
        //     case (KeyEvent.VK_S):
        //         pos.translate(0, 1);
        //         lastMove = -1;
        //         lastAxis = 0;
        //         break;
        //     case (KeyEvent.VK_RIGHT):
        //         pos.translate(1, 0);
        //         lastMove = -1;
        //         lastAxis = 1;
        //         break;
        //     case (KeyEvent.VK_D):
        //         pos.translate(1, 0);
        //         lastMove = -1;
        //         lastAxis = 1;
        //         break;
        //     }
        // ******************************************
    }
    
    public void tick() {
        
    }

    public String getScore() {
        return String.valueOf(score);
    }

    public void addScore(int amount) {
        score += amount;
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(int x, int y) {
        pos.x = x;
        pos.y = y;
    }

}
