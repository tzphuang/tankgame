package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Moving extends GameObject{

    //x velocity of object
    private int vx;
    //y velocity of object
    private int vy;

    //how fast object can move
    private final int OBJECTSPEED = 2;
    //how fast the object's rotation can be
    private final float ROTATIONSPEED = 3.0f;

    Moving(int currX, int currY, int currVX, int currVY, int currAngle, BufferedImage currImg){
        super(currX, currY, currAngle, currImg);
        this.vx = currVX;
        this.vy = currVY;
    }

    @Override
    public void drawImage(Graphics gameImage) {
        super.drawImage(gameImage);
    }
}
