
import java.awt.*;

public class Duck {


    public int xpos;                //the x position
    public int ypos;                //the y position
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public Rectangle rec;
    public Image pic;

    //movement booleans
    public boolean right;
    public boolean left;
    public boolean down;
    public boolean up;

    public boolean Collide;

    public Duck(int pXpos, int pYpos, int dxParameter, int dyParameter, Image picParameter) {

        xpos = pXpos;
        ypos = pYpos;
        width = 70;
        height = 55;
        dx = dxParameter;
        dy = dyParameter;
        pic = picParameter;
        isAlive = true;
        rec = new Rectangle(xpos, ypos, width, height);
        Collide=false;

    } // constructor

    //move( ) method for a keyboard controlled character
    public void move() {
        if (Collide==false){
            dy=4;
        }
        else{
            dy=-2;
        }
            //if collide == false,
                //make it fall
            //else collide == true,
                //dy is same as logs
        xpos = xpos + dx;
        ypos = ypos + dy;

        if(right == true){
           dx =3;
        } else if (left == true) {
            dx = -3;
        } else { // (right == false && left == false)
            dx = 0;
        }
//
//        if(down == true){
//            dy = 2;
//        } else if (up == true) {
//            dy = -2;
//        } else {
//            dy = 0;
//        }

        if(xpos>1000-width){ // right
            xpos = 1000-width;
        }
        if(xpos < 0) { // left
            xpos = 0;
        }
//        if(ypos>650-height){ // down
//            ypos = 650-height;
//        }
        if(ypos < -10) { // up
            ypos = -10;
        }


        //always put this after you've done all the changing of the xpos and ypos values
        rec = new Rectangle(xpos, ypos, width, height);

    }

//    public void move2() {
//        xpos = xpos + dx;
//        ypos = ypos + dy;
//
//        if (ypos < 250) {
//            dy = dy + 1;
//        }
//        if (ypos > 250) {
//            ypos = 250;
//        }
//
//        if(xpos>1000-width){ // right
//            xpos = 1000-width;
//        }
//        if(xpos < 0) { // left
//            xpos = 0;
//        }
//        if(ypos>650-height){ // down
//            ypos = 650-height;
//        }
//        if(ypos < 0) { // up
//            ypos = 0;
//        }
//    }

}
