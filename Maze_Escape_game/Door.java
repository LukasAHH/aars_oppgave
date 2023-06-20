import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Door {
    
    // image that represents the door's position on the board
    private BufferedImage closed;
    private BufferedImage open;
    private BufferedImage[] doorImage = {closed, open};
    // 0 = door closed | 1 = door open
    public int doorState = 0;
    // decides what level the door will lead to
    public int doorLevel;
    // current position of the door on the board grid
    public Point pos;

    public Door(int x, int y, int lvl) {
        // load the assets
        loadImage();

        // initialize the state
        pos = new Point(x, y);

        doorLevel = lvl;
    }

    private void loadImage() {
        try {
            // load door image
            closed = ImageIO.read(new File("images/door_closed.png"));
            open = ImageIO.read(new File("images/door_open.png"));
            doorImage[0] = closed;
            doorImage[1] = open;
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        // using pos.x instead of pos.getX() in order to recieve an int rather than a double
        g.drawImage(
            doorImage[doorState], 
            pos.x * Board.TILE_SIZE, 
            pos.y * Board.TILE_SIZE, 
            observer
        );
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(int x, int y) {
        pos.x = x;
        pos.y = y;
    }

    public int setDoorState(int x) {
        doorState = x;
        return x;
    }
}
