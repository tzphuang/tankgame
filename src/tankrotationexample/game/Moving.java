package tankrotationexample.game;

import java.awt.*;

public abstract class Moving extends GameObject{

    //x velocity of object
    private int vx;
    //y velocity of object
    private int vy;

    //how fast object can move
    private final int OBJECTSPEED = 2;
    //how fast the object's rotation can be
    private final float ROTATIONSPEED = 3.0f;

    @Override
    public void drawImage(Graphics gameImage) {
        super.drawImage(gameImage);
    }
}
