import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;  //the size of the object
    static final int GAME_UNIT = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;  //how many object we can fit on the screen
    static final int DELAY = 75; //higher number of delay, slower the game will be
    final int x[] = new int[GAME_UNIT];
    final int y[] = new int[GAME_UNIT];
    int bodyParts = 6;
    int appleEaten;
    int appleX;
    int appleY;
    char direction = 'R'; //the sneak starts with going Right by default
    boolean running = false;
    Timer timer;


    public GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.darkGray);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);

    }
    public void draw(Graphics graphics){
        for (int i = 0 ; i < SCREEN_HEIGHT/UNIT_SIZE ; i++){
            graphics.drawLine(i*UNIT_SIZE, 0,  i*UNIT_SIZE, SCREEN_HEIGHT);
            graphics.drawLine(0, i*UNIT_SIZE,  SCREEN_WIDTH, i*UNIT_SIZE);
        }

        graphics.setColor(Color.RED);
        graphics.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

        for (int i = 0 ; i < bodyParts ; i++) {
            if (i == 0 ){
                graphics.setColor(Color.green);
                graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            } else {
                graphics.setColor(new Color(45, 150,0));
                graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }


    }
    public void newApple(){
        appleX = ThreadLocalRandom.current().nextInt(1, (int)(SCREEN_WIDTH / UNIT_SIZE + 1))*UNIT_SIZE;
        appleY = ThreadLocalRandom.current().nextInt(1, (int)(SCREEN_WIDTH / UNIT_SIZE + 1))* UNIT_SIZE;


    }
    public void move(){
        for (int i = bodyParts; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction){
            case 'U' -> y[0] -= UNIT_SIZE;
            case 'D' -> y[0] += UNIT_SIZE;
            case 'L' -> x[0] -= UNIT_SIZE;
            case 'R' -> x[0] += UNIT_SIZE;
        }

    }
    public void checkApple(){}
    public void checkCollisions(){
        //check if the snake head has the same cooridnation with body, i.e. if head collise with body
        for(int i = bodyParts ; i > 0 ; i--){
            if ((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }

        //check if head touch left, right, top, bottom border
        if (x[0] < 0 ||
            x[0] > SCREEN_WIDTH ||
            y[0] < 0 ||
            y[0] > SCREEN_HEIGHT){
            running = false;
        }


        if (!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics graphics){}
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    //an inner class
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent event){

        }

    }
}
