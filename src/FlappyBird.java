import javax.swing.*;
import java.awt.*;

public class FlappyBird extends JPanel {
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

    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.blue);

        //load Images
        backgroundImg = new ImageIcon(getClass().getResource("./Images/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./Images/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./Images/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./Images/bottompipe.png")).getImage();

        bird = new Bird(birdImg);
    }

    //Draw the background
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public  void draw(Graphics g){
        //draw the background
        g.drawImage(backgroundImg, 0,0,boardWidth, boardHeight, null);

        //draw Bird
        g.drawImage(bird.img, bird.x,bird.y,bird.width,bird.height,null);
    }
}
