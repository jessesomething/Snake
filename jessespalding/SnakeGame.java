package jessespalding;

import java.util.Timer;

import javax.swing.*;


public class SnakeGame {

    public static int xPixelMaxDimension = 501;  //Pixels in window. 501 to have 50-pixel squares plus 1 to draw a border on last square
    public static int yPixelMaxDimension = 501;

    public static int xSquares ;
    public static int ySquares ;

    public static int squareSize = 50;

    protected static Snake snake;


    protected static Kibble kibble;

    protected static Score score;

    private static boolean firstGame = true;

    static final int BEFORE_GAME = 1;
    static final int DURING_GAME = 2;
    static final int GAME_OVER = 3;
    static final int GAME_WON = 4;   //The values are not important. The important thing is to use the constants
    //instead of the values so you are clear what you are setting. Easy to forget what number is Game over vs. game won
    //Using constant names instead makes it easier to keep it straight. Refer to these variables
    //using statements such as SnakeGame.GAME_OVER

    private static int gameStage = BEFORE_GAME;  //use this to figure out what should be happening.
    //Other classes like Snake and DrawSnakeGamePanel will need to query this, and change it's value

    public static long clockInterval = 300; //controls game speed
    //Every time the clock ticks, the snake moves
    //This is the time between clock ticks, in milliseconds
    //1000 milliseconds = 1  second.

    static JFrame snakeFrame;
    //Initiates the JFrame GUI
    static JFrame newSnakeFrame;
    static DrawSnakeGamePanel snakePanel;
    //Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too.
    //http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
    //http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html

    static GameControls gameControls;

    public static void createAndShowGUI() {
        //Create and set up the window.
        System.out.println("Window setup");

        // Checks if it's the first game and if not
        // changes frame visibility and removes them from the frame
        if (firstGame == false) {
            snakeFrame.getContentPane().removeAll();
            snakeFrame.setVisible(false);
            snakePanel.removeKeyListener(gameControls);
            snakePanel.removeAll();
//            setGameStage(GAME_OVER);
        }

        snakeFrame = new JFrame();
        gameControls = new GameControls(snake);
        snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension); // Sets the window size for the JFrame
        snakeFrame.setUndecorated(true); //hide title bar
        snakeFrame.setVisible(true);
        snakeFrame.setResizable(true);

        snakePanel = new DrawSnakeGamePanel(snake, kibble, score);
        snakePanel.setFocusable(true);
        snakePanel.requestFocusInWindow(); //required to give this component the focus so it can generate KeyEvents
//        snakeFrame.add(snakePanel);
        snakeFrame.getContentPane().add(snakePanel);
        snakePanel.addKeyListener(gameControls);

        snakeFrame.setVisible(true);

        firstGame = false;
    }

    public static void initializeGame() {


        //set up score, snake and first kibble
        xSquares = xPixelMaxDimension / squareSize;
        ySquares = yPixelMaxDimension / squareSize;



        snake = new Snake(xSquares, ySquares, squareSize);
        kibble = new Kibble(snake);
        score = new Score();
        System.out.println("Starting game");

        if (getGameStage() != GAME_OVER) {
            gameStage = BEFORE_GAME;
        }
    }

    // Begins a new game class
    public static void newGame() {
        // Creates a new timer
        System.out.println("New game");
        Timer timer = new Timer();
        GameClock clockTick = new GameClock(snake, kibble, score, snakePanel);
        timer.scheduleAtFixedRate(clockTick, 0 , clockInterval);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initializeGame();
                createAndShowGUI();
            }
        });
    }



    public static int getGameStage() {
        return gameStage;
    }

    public static boolean gameEnded() {
        if (gameStage == GAME_OVER || gameStage == GAME_WON){
            return true;
        }
        return false;
    }

    public static void setGameStage(int gameStage) {
        SnakeGame.gameStage = gameStage;
    }

    // Added by Jesse
    // Changes game speed and timer
    public static void changeGameSpeed(Long clockInterval, int speedChange) {
        if (speedChange == -1) {
            SnakeGame.clockInterval = clockInterval + 50;
            System.out.println(clockInterval);
        }
        if (speedChange == 1) {
            SnakeGame.clockInterval = clockInterval - 50;
            System.out.println(clockInterval);
        }
    }

    // Added by Jesse
    // Gets clock speed interval
    public static long getClockInterval() {
        return clockInterval;
    }

    // Added by Jesse
    // Gets the clock interval and converts itto 'gamespeed' string
    public static String getGameSpeed() {
        long clockConvert = clockInterval/50-1;
        String gameSpeed = String.valueOf(clockConvert);
        if (clockConvert != 5) {
            gameSpeed = String.valueOf(10-clockConvert);
        }
        return gameSpeed;
    }

    // Added by Jesse
    // Function that changes the board size
    public static void changeBoardSize(int boardSize, int boardChange){
        if (boardChange == -1) {
            SnakeGame.xPixelMaxDimension = SnakeGame.xPixelMaxDimension - 100;
            SnakeGame.yPixelMaxDimension = SnakeGame.yPixelMaxDimension - 100;
        } else if (boardChange == 1) {
            SnakeGame.xPixelMaxDimension = SnakeGame.xPixelMaxDimension + 100;
            SnakeGame.yPixelMaxDimension = SnakeGame.yPixelMaxDimension + 100;
        }
        snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
    }

    // Added by Jesse
    // Gets the board size and displays it as small/med/lg
    public static String getBoardSize() {
        int boardDimension = xPixelMaxDimension + yPixelMaxDimension;
        String boardSize = "";
        if (boardDimension == 1002) {
            boardSize = "Small";
        } else if (boardDimension == 1202) {
            boardSize = "Medium";
        } else if (boardDimension == 1402) {
            boardSize = "Large";
        }
        return boardSize;
    }

    // Added by Jesse
    // Gets the board dimensions
    public static int getBoardDimension() {
        int boardDimension = xPixelMaxDimension + yPixelMaxDimension;
        return boardDimension;
    }
}
