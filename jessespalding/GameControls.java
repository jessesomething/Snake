package jessespalding;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameControls implements KeyListener{

    Snake snake;

    // GameControl constructor
    GameControls(Snake s){
        this.snake = s;
    }

    public void keyPressed(KeyEvent ev) {
        //keyPressed events are for catching events like function keys, enter, arrow keys
        //We want to listen for arrow keys to move snake
        //Has to id if user pressed arrow key, and if so, send info to Snake object

        //is game running? No? tell the game to draw grid, start, etc.

        //Get the component which generated this event
        //Hopefully, a DrawSnakeGamePanel object.
        //It would be a good idea to catch a ClassCastException here.

        DrawSnakeGamePanel panel = (DrawSnakeGamePanel)ev.getComponent();

        if (SnakeGame.getGameStage() == SnakeGame.BEFORE_GAME){
            //Start the game
            SnakeGame.newGame();
            SnakeGame.setGameStage(SnakeGame.DURING_GAME);
            panel.repaint();
            return;
        }

        if (SnakeGame.getGameStage() == SnakeGame.GAME_OVER){
            snake.reset();
            Score.resetScore();

            //Need to start the timer and start the game again
            SnakeGame.newGame();
            SnakeGame.setGameStage(SnakeGame.DURING_GAME);
            panel.repaint();
            return;
        }


        if (ev.getKeyCode() == KeyEvent.VK_DOWN) {
            //System.out.println("snake down");
            snake.snakeDown();
        }
        if (ev.getKeyCode() == KeyEvent.VK_UP) {
            //System.out.println("snake up");
            snake.snakeUp();
        }
        if (ev.getKeyCode() == KeyEvent.VK_LEFT) {
            //System.out.println("snake left");
            snake.snakeLeft();
        }
        if (ev.getKeyCode() == KeyEvent.VK_RIGHT) {
            //System.out.println("snake right");
            snake.snakeRight();
        }

    }


    @Override
    public void keyReleased(KeyEvent ev) {
        //We don't care about keyReleased events, but are required to implement this method anyway.
    }


    @Override
    public void keyTyped(KeyEvent ev) {
        //keyTyped events are for user typing letters on the keyboard, anything that makes a character display on the screen
        char keyPressed = ev.getKeyChar();
        char q = 'q';
        // Adds decrease speed button
        char d = 'd';
        // Adds increase speed button
        char f = 'f';
        // Board size decrease button
        char z = 'z';
        // Board size increase button
        char x = 'x';
        if( keyPressed == q){
            System.exit(0);    //quit if user presses the q key.
        }
        if(keyPressed == d) {
            if (SnakeGame.getClockInterval() > 500) {
                System.out.println("Snake can't move any slower");
            } else {
                System.out.println("Decreased speed");
                SnakeGame.changeGameSpeed(SnakeGame.getClockInterval(), -1);
            }
        }
        if(keyPressed == f) {
            if (SnakeGame.getClockInterval() < 100) {
                System.out.println("Snake can't move any faster");
            } else {
                System.out.println("Increased speed");
                SnakeGame.changeGameSpeed(SnakeGame.getClockInterval(), 1);
            }
        }
        if(keyPressed == z) {
            if (SnakeGame.getBoardDimension() == 1002) {
                System.out.println("Board size is the smallest");
            } else {
                System.out.println("Decreased board size");
                SnakeGame.changeBoardSize(SnakeGame.getBoardDimension(), -1);
            }
        }
        if(keyPressed == x) {
            if (SnakeGame.getBoardDimension() > 1402) {
                System.out.println("Board size is the largest");
            } else {
                System.out.println("Increased board size");
                SnakeGame.changeBoardSize(SnakeGame.getBoardDimension(), 1);
            }
        }
    }

}
