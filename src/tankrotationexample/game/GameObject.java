package tankrotationexample.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObject implements Drawable{

    //x position on the map for object
    private int x;
    //y position on the map for object
    private int y;

    private boolean drawHitBox = true;

    //angle of object
    private float angle;

    //object's image
    private BufferedImage objectImg;

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


    public void drawImage(Graphics gameImage){

        //takes current image and keeps its image "line straightness", will be used to rotate image
        AffineTransform currRotation = AffineTransform.getTranslateInstance(x, y);
        currRotation.rotate(Math.toRadians(angle),this.objectImg.getWidth() / 2.0, this.objectImg.getHeight() /2.0);
        Graphics2D currImage = (Graphics2D) gameImage;
        currImage.drawImage(this.objectImg, currRotation, null);

        if(drawHitBox){
            //here i will draw the hitbox in the same x,y as the game object above
        }

    }

    /*
        //takes current image and keeps its image "line straightness", will be used to rotate image
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        //rotate the image around the middle of the image (width/2 & height/2 = center of object)
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        //create new image to be used as buffered image
        Graphics2D hitBoxBorder = (Graphics2D) g;
        g.drawImage(this.img, rotation, null);

        //below used to create a border around the tank to define its border
        hitBoxBorder.setColor(Color.GREEN);
        hitBoxBorder.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
        hitBoxBorder.drawRect(x,y,this.img.getWidth(),this.img.getHeight());

     */



}
