import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;


public class FlappyBird extends JPanel implements ActionListener, KeyListener{
    int boardWidth = 360;
    int boardHeight = 640;

    //Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //Bird
    //to define the bird on the Panel, the x,y components must be defined and the offset values
    int birdX = boardHeight/8; //an eighth of the panel height
    int birdY = boardHeight/2; //half of the board screen
    int birdWidth = 34;
    int birdHeight = 24;

    //Class to hold the bird dimension
    class Bird{
        int x = birdX;
        int y = birdY;
        int height = birdHeight;
        int width = birdWidth;
        Image img;

        //Constructor and pass in the Image
        Bird(Image img){
            this.img = img;
        }
    }

    //Pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64; //scaled by 1/6
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img){
            this.img = img;
        }
    }

    //Game Logic
    Bird bird;
    int velocityX = -2; //move pipes to the left  (simulates bird moving right)

    //Make the bird move by adding a velocity
    //upward movement in the y axis is negative
    //downward movement in the y axis = positive
    //forward is +x
    //backward is -x
    int velocityY = 0;
    float gravity =  0.999999999999999f;

    ArrayList <Pipe> pipes;
    Random random = new Random();

    //Loop to draw the bird in each frame -60fps
    Timer gameLoopTimer;
    Timer placePipesTimer;
    boolean gameOver = false;
    double score = 0;

    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);

        //load Images
        backgroundImg = new ImageIcon(getClass().getResource("./Images/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./Images/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./Images/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./Images/bottompipe.png")).getImage();

        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();
    

        //place pipes Timer every 2s
        placePipesTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }
        });
        placePipesTimer.start();

        //CReate a Timer object in ms , 1000ms = 1sec
        //draw at 60 fpf
        gameLoopTimer = new Timer(1000/60, this);
        gameLoopTimer.start();
    }

    //function to place pipes
    public void placePipes(){
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random() * (pipeHeight/2));
        int openingSpace = boardHeight / 4;


        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe botttomPipe = new Pipe(bottomPipeImg);
        botttomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(botttomPipe);
    }

    //Draw the background
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public  void draw(Graphics g){
        //debugger for the timer 
        //System.out.println("draw");
        //draw the background
        g.drawImage(backgroundImg, 0,0,boardWidth, boardHeight, null);

        //draw Bird
        g.drawImage(bird.img, bird.x,bird.y,bird.width,bird.height,null);

        //draw Pipe
        for (int i=0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        //display score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN,32));
        if(gameOver){
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
        } 
        else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    //move function
    public void move(){
        //bird
        velocityY += gravity;
        //bird upward movement
        bird.y += velocityY;
        //stop movement at the top of the screen
        bird.y = Math.max(bird.y, 0);

        //pipes
        for (int i =0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if(!pipe.passed && bird.x > pipe.x + pipeWidth){
                pipe.passed = true;
                score += 0.5; //1 point for each set of pipes
            }
      
            if (collission(bird, pipe)){
                gameOver = true;
            }
        }

        if (bird.y > boardHeight){
            gameOver = true;
        }

    }

    //check collisison 
    public boolean collission (Bird a, Pipe b){
        return  a.x < b.x + b.width && 
                a.x  + a.width  > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    //paint component function these actions are  performed at 60fps in a loop
    @Override
    public void actionPerformed(ActionEvent e) {
        //UPDATE BIRD POSITION BEFORE REPAINT
        move();
        repaint();
        if (gameOver){
            placePipesTimer.stop();
            gameLoopTimer.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY = -9;
            if(gameOver) {
                //restart the game by reseting all conditions
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoopTimer.start();
                placePipesTimer.start();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
