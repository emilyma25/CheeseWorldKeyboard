//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.*;

/***
 * Step 0 for keyboard control - Import
 */
import java.awt.event.*;

/***
 * Step 1 for keyboard control - implements KeyListener
 */
public class DuckWorld implements Runnable, KeyListener {

    //Variable Definition Section

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    public boolean gamePlaying;
    public boolean gameOver;


    //Declare the variables needed for images
    public Image floorPic;
    public Image backgroundPic;
    public Image duckPic;
    public Image explosionPic;

    //Declare the character objects
    public Background background1;
    public Background background2;
    public Duck duck;
    public Log [] Logs;
    public Log [] RightLogs;
    public boolean Collide=false;
    public double distanceCounter;

    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        DuckWorld myApp = new DuckWorld();   //creates a new instance of the game
        new Thread(myApp).start();               //creates a threads & starts up the code in the run( ) method
    }

    // Constructor Method - setup portion of the program
    // Initialize your variables and construct your program objects here.
    public DuckWorld() {

        setUpGraphics();

        /***
         * Step 2 for keyboard control - addKeyListener(this) to the canvas
         */
        canvas.addKeyListener(this);

        //load images
        floorPic = Toolkit.getDefaultToolkit().getImage("log.png");
        backgroundPic = Toolkit.getDefaultToolkit().getImage("dirt.png");
        duckPic = Toolkit.getDefaultToolkit().getImage("real duck.png");
        explosionPic = Toolkit.getDefaultToolkit().getImage("explosion.png");

        //create (construct) the objects needed for the game

        background2 = new Background(0, 1, 0, -2, backgroundPic);
        background1 = new Background(0, 700, 0, -2, backgroundPic);
        duck = new Duck(200, 540, 0, -1, duckPic);

        Logs = new Log[7];
        for (int i=0; i<Logs.length; i++){
            Logs [i] = new Log(0,i*150,0,-2,(int)(100+(Math.random()*600)), floorPic);
        }

        RightLogs = new Log[7];
        for (int i=0; i<Logs.length;i++){
            int RightWidth = 800-Logs[i].width;
            RightLogs[i]=new Log(1000-RightWidth,i*150,0,-2,RightWidth,floorPic);

        }

    } // CheeseWorld()


//*******************************************************************************
//User Method Section

    // main thread
    // this is the code that plays the game after you set things up
    public void moveThings() {
        background1.move();
        background2.move();
        duck.move();
        for (int i=0; i<Logs.length; i++){
            Logs[i].move();
            RightLogs[i].width=800-Logs[i].width;
            RightLogs[i].xpos=1000-RightLogs[i].width;
            RightLogs[i].move();
        }


    }




    public void checkIntersections() {
        if (duck.ypos < 0 || duck.ypos>700) {
            duck.isAlive = false;
        }
        duck.Collide = false;
        for (int i = 0; i < 7; i++) {
            if (duck.rec.intersects(Logs[i].rec) || duck.rec.intersects(RightLogs[i].rec)) {
                duck.Collide = true;
            }
        }
    }

    public void run() {
        while (true) {
            if(!gamePlaying){
                moveThings();           //move all the game objects
            }
            checkIntersections();   // check character crashes
            render();               // paint the graphics
            pause(20);         // sleep for 20 ms
            counter();
        }
    }

    //paints things on the screen using bufferStrategy
    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw characters to the screen
        g.drawImage(background1.pic, background1.xpos, background1.ypos, background1.width, background1.height, null);
        g.drawImage(background2.pic, background2.xpos, background2.ypos, background2.width, background2.height, null);
       // g.drawImage(theFloor.pic, theFloor.xpos+100, theFloor.ypos, theFloor.width, theFloor.height, null);

        //g.drawImage(theFloor.pic, theFloor.xpos, theFloor.ypos, theFloor.width, theFloor.height, null);

        for (int i=0; i<7; i++){

            g.drawImage(floorPic, Logs[i].xpos, Logs[i].ypos, Logs[i].width, Logs[i].height, null);}


        for (int i=0; i<7; i++){
            g.drawImage(floorPic, RightLogs[i].xpos, RightLogs[i].ypos, RightLogs[i].width, RightLogs[i].height, null);
        }
        g.drawImage(duck.pic, duck.xpos, duck.ypos, duck.width, duck.height, null);
        g.dispose();
        bufferStrategy.show();

        if (duck.isAlive==false){
            duck.dy=0;

            if (duck.ypos<0){
                duck.ypos=0;
            }
            else{
                duck.ypos=700-duck.height;
            }
            duck.pic=explosionPic;
            background2 = new Background(0, background2.ypos, 0, 0, backgroundPic);
            background1 = new Background(0, background1.ypos, 0, 0, backgroundPic);
           // Logs = new Log[7];
            for (int i=0; i<Logs.length; i++){
                Logs[i].dx=0; //= new Log(0,Logs[i].ypos,0,0,Logs[i].width, floorPic);
                Logs[i].dy=0;
            }
            //RightLogs = new Log[7];
            for (int i=0; i<RightLogs.length; i++){
                RightLogs[i].dx=0; //= new Log(0,Logs[i].ypos,0,0,Logs[i].dy, floorPic);
                RightLogs[i].dy=0;
            }

        }

        if (!gamePlaying){
            //start screen
        }
        else if (gamePlaying && !gameOver){
            //playing game
        }

        else {
            //game over
        }
    }

    /***
     * Step 3 for keyboard control - add required methods
     * You need to have all 3 even if you aren't going to use them all
     */
    public void keyPressed(KeyEvent event) {
        //This method will do something whenever any key is pressed down.
        //Put if( ) statements here
        char key = event.getKeyChar();     //gets the character of the key pressed
        int keyCode = event.getKeyCode();  //gets the keyCode (an integer) of the key pressed
        System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

        if (keyCode == 68) { // d
            duck.right = true;
        }
        if (keyCode == 65) { // a
            duck.left = true;
        }

        if (keyCode == 83) { // s
            duck.down = true;
        }
        if (keyCode == 87) { // w
            duck.up = true;
        }
    }//keyPressed()

    public void keyReleased(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        //This method will do something when a key is released
        if (keyCode == 68) { // d
            duck.right = false;
        }
        if (keyCode == 65) { // a
            duck.left = false;
        }
        if (keyCode == 83) { // s
            duck.down = false;
        }
        if (keyCode == 87) { // w
            duck.up = false;
        }

    }//keyReleased()





    public void keyTyped(KeyEvent event) {
        // handles a press of a character key (any key that can be printed but not keys like SHIFT)
        // we won't be using this method, but it still needs to be in your program
    }//keyTyped()

    public void counter(){
        if(duck.isAlive==true){
            distanceCounter=(distanceCounter+1);
            System.out.println(distanceCounter);
        }

    }


    //Graphics setup method
    public void setUpGraphics() {
        frame = new JFrame("DuckWorld");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

}//class
