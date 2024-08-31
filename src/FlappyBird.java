import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FlappyBird extends JPanel implements ActionListener{
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

    //Game Logic
    Bird bird;

    //Make the bird move by adding a velocity
    //upward movement in the y axis is negative
    //downward movement in the y axis = positive
    //forward is +x
    //backward is -x
    int velocityY = -6;
    int velocityX = 6;

    //Loop to draw the bird in each frame -60fps
    Timer gameLoopTimer;

    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.blue);

        //load Images
        backgroundImg = new ImageIcon(getClass().getResource("./Images/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./Images/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./Images/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./Images/bottompipe.png")).getImage();

        bird = new Bird(birdImg);

        //CReate a Timer object in ms , 1000ms = 1sec
        //draw at 60 fpf
        gameLoopTimer = new Timer(1000/60, this);
        gameLoopTimer.start();
    }

    //Draw the background
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public  void draw(Graphics g){
        //debugger for the timer 
        System.out.println("draw");
        //draw the background
        g.drawImage(backgroundImg, 0,0,boardWidth, boardHeight, null);

        //draw Bird
        g.drawImage(bird.img, bird.x,bird.y,bird.width,bird.height,null);
    }

    //move function
    public void move(){
        //bird 
        bird.y += velocityY;
        bird.x += velocityX;
    }

    //paint component function these actions are  performed at 60fps in a loop
    @Override
    public void actionPerformed(ActionEvent e) {
        //UPDATE BIRD POSITION BEFORE REPAINT
        move();
        repaint();
    }
}
