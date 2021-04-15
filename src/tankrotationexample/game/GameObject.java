package tankrotationexample.game;



import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    //x position on the map for object
    private int x;
    //y position on the map for object
    private int y;
    //x velocity of object
    private int vx;
    //y velocity of object
    private int vy;
    //angle of object
    private float angle;

    //how fast object can move
    private final int OBJECTSPEED = 2;
    //how fast the object's rotation can be
    private final float ROTATIONSPEED = 3.0f;

    //object's image
    private BufferedImage img;

    //object's hitBox
    private Rectangle hitBox;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    private void moveBound(){
        this.hitBox.setLocation(x,y);
    }

    public abstract void checkBorder();

    public void drawImage(Graphics currentImage){
        //takes current image and keeps its image "line straightness", will be used to rotate image
        AffineTransform currRotation = AffineTransform.getTranslateInstance(x, y);
        currRotation.rotate(Math.toRadians(angle),this.img.getWidth() / 2.0, this.img.getHeight() /2.0);

    }

    /*
        //takes current image and keeps its image "line straightness", will be used to rotate image
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        //rotate the image around the middle of the image (width/2 & height/2 = center of object)
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        //create new image to be used as buffered image
        Graphics2D hitBoxBorder = (Graphics2D) g;

        //below used to create a border around the tank to define its border
        hitBoxBorder.drawImage(this.img, rotation, null);
        hitBoxBorder.setColor(Color.GREEN);
        hitBoxBorder.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
        hitBoxBorder.drawRect(x,y,this.img.getWidth(),this.img.getHeight());

     */



}
