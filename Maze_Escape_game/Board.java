import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
// import java.util.Random;
import javax.swing.*;

public class Board extends JPanel implements ActionListener, KeyListener {

    // controls the delay between each tick in ms
    private final int DELAY = 75;
    // controls the size of the board
    public static final int TILE_SIZE = 50;
    public static final int ROWS = 16;
    public static final int COLUMNS = 28;
    // controls how many coins appear on the board
    public static final int NUM_COINS = 5;
    // suppress serialization warning
    private static final long serialVersionUID = 490905409104883233L;
    
    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;
    static int[][] level;
    // objects that appear on the game board
    public static Player player;
    public static ArrayList<Coin> coins;
    public static ArrayList<Coin> coinList = new ArrayList<>();
    public static ArrayList<Wall> walls;
    public static ArrayList<Wall> wallList = new ArrayList<>();
    public static ArrayList<Door> doors;
    public static ArrayList<Door> doorList = new ArrayList<>();
    public static int doorAmount = 0;

    // variables for storing the door state
    public static int doorState;
    public static final int closed = 0;
    public static final int open = 1;


    // *****************************
    // previously used to create and draw a score based off of coins collected

    // coin initial value and inflation rate
    // private float coinValue = 100f;
    // private float inflationRate = 1.02f;
    // private int coinScore;


    public Board() {
        // set the game board size
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
        // set the game board background color
        setBackground(new Color(232, 232, 232));
        
        // initialize the game state
        player = new Player();
        doorList.add(new Door(1,1,0));
        level = Levels.getLevel(0);
        coinList.add(new Coin(1,1));
        wallList.add(new Wall(1,1));
        doors = doorList;
        coins = coinList;
        walls = wallList;
        populateLevel(level);

        // this timer will call the actionPerformed() method every DELAY ms
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        
        // prevent the player from disappearing off the board
        player.tick();
        
        // give the player points for collecting coins
        collectCoins();

        // calling repaint() will trigger paintComponent() to run again,
        // which will refresh/redraw the graphics.
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // when calling g.drawImage() we can use "this" for the ImageObserver
        // because Component implements the ImageObserver interface, and JPanel
        // extends from Component. So "this" Board instance, as a Component, can
        // react to imageUpdate() events triggered by g.drawImage()

        // draw our graphics.
        drawBackground(g);
        for (Wall wall : walls) {
            wall.draw(g, this);
        }
        player.draw(g, this);
        for (Coin coin : coins) {
            coin.draw(g, this);
        }
        for (Door door : doors) {
            door.draw(g, this);
        }
        // drawScore(g);

        // this smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // react to key down events
        player.pressedKey = e;
        player.isKeyPressed = 1;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // react to key up events
    }
    
    private void drawBackground(Graphics g) {
        // draw a checkered background
        g.setColor(new Color(214, 214, 214));
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                // only color every other tile
                if ((row + col) % 2 == 1) {
                    // draw a square tile at the current row/column position
                    g.fillRect(
                        col * TILE_SIZE,
                        row * TILE_SIZE,
                        TILE_SIZE,
                        TILE_SIZE
                        );
                    }
                }
            }
        }
    // ****************************************************
    // 
    //     private void drawScore(Graphics g) {
    //         // set the text to be displayed
    //         String text = "$" + player.getScore();
    //         // we need to cast the Graphics to Graphics2D to draw nicer text
    //         Graphics2D g2d = (Graphics2D) g;
    //         g2d.setRenderingHint(
    //             RenderingHints.KEY_TEXT_ANTIALIASING,
    //             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    //             g2d.setRenderingHint(
    //                 RenderingHints.KEY_RENDERING,
    //                 RenderingHints.VALUE_RENDER_QUALITY);
    //                 g2d.setRenderingHint(
    //                     RenderingHints.KEY_FRACTIONALMETRICS,
    //                     RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    //                     // set the text color and font
    //                     g2d.setColor(new Color(30, 201, 139));
    //                     g2d.setFont(new Font("Lato", Font.BOLD, 25));
    //                     // draw the score in the bottom center of the screen
    //                     // https://stackoverflow.com/a/27740330/4655368
    //                     FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
    //                     // the text will be contained within this rectangle.
    //     // here I've sized it to be the entire bottom row of board tiles
    //     Rectangle rect = new Rectangle(0, TILE_SIZE * (ROWS - 1), TILE_SIZE * COLUMNS, TILE_SIZE);
    //     // determine the x coordinate for the text
    //     int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
    //     // determine the y coordinate for the text
    //     // (note we add the ascent, as in java 2d 0 is top of the screen)
    //     int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
    //     // draw the string
    //     g2d.drawString(text, x, y);
    // }
    
    // ******************************
    // old code from old version

    // private ArrayList<Coin> populateCoins() {
    //     ArrayList<Coin> coinList = new ArrayList<>();
    //     Random rand = new Random();
        
    //     // create the given number of coins in random positions on the board.
    //     // note that there is not check here to prevent two coins from occupying the same
    //     // spot, nor to prevent coins from spawning in the same spot as the player
    //     for (int i = 0; i < NUM_COINS; i++) {
    //         int coinX = rand.nextInt(COLUMNS);
    //         int coinY = rand.nextInt(ROWS);
    //         coinList.add(new Coin(coinX, coinY));
    //         }
    //         return coinList;
    //     }
    // ******************************

    private void collectCoins() {
        // allow player to pickup coins
        
        ArrayList<Coin> collectedCoins = new ArrayList<>();
        // OLD -- Random rand = new Random();

        for (Coin coin : coins) {
            // if the player is on the same tile as a coin, collect it
            if (player.getPos().equals(coin.getPos())) {
                collectedCoins.add(coin);

                
                // Keeping this in case I want to use it later | give the player some points for picking this up
                // coinScore = (int) coinValue;
                // player.addScore(coinScore);
                // coinValue *= inflationRate;
                
                // Will no longer be used | move coin to new spot to simulate it respawning
                
                // while (true) {
                    
                    //     int posX = rand.nextInt(COLUMNS);
                    //     int posY = rand.nextInt(ROWS);
                    
                    //     if (level[posY][posX] != 1) {
                        //         coin.pos.x = posX;
                        //         coin.pos.y = posY;
                        //         break;
                        //     }
                        //     System.out.println("coin moved");
                        // }
                        
                        
            }
        }
        // remove collected coins from the board
        coins.removeAll(collectedCoins);
        if (coins.isEmpty()) { 
            doorState = open;
            for (Door carl : doors) {
                carl.doorState = doorState;
            }
        }
    }
    public static boolean checkIfPlayerOnWall() {
        boolean switchLevel = false;
        boolean playerOnWall = false;
        // currently using the coins as a substitute for walls
        for (Door door : doors) { 
            if (player.getPos().equals(door.getPos())) {
                switch (doorState) {
                    case closed:
                        playerOnWall = true;
                        return playerOnWall;
                    case open:
                        level = Levels.getLevel(door.doorLevel);
                        switchLevel = true;
                }
            }
        }

        if (switchLevel) {
            populateLevel(level);
        }

        for (Wall wall : walls) {
            if (player.getPos().equals(wall.getPos())) {
                playerOnWall = true;
            }
        }
        
        return playerOnWall;
    }
    private static void populateLevel(int[][] level) {
        doorState = closed;
        doorAmount = 0;
        doors.removeAll(doors);
        coins.removeAll(coins);
        walls.removeAll(walls);
        doorList.removeAll(doorList);
        coinList.removeAll(coinList);
        wallList.removeAll(wallList);
        for (int y = 0; y < level.length; ++y) {
            for (int x = 0; x < level[y].length; ++x) {
                switch (level[y][x]) {
                    case 0:
                        break;
                    case 1:
                        wallList.add(new Wall(x, y));
                        break;
                    case 2:
                        player.setPos(x, y);
                        break;
                    case 3:
                        coinList.add(new Coin(x, y));
                        break;
                    case 9:
                        doorList.add(new Door(x, y, doorAmount));
                        doorAmount += 1;
                }
            }
        }
        doors = doorList;
        coins = coinList;
        walls = wallList;
    }

}
