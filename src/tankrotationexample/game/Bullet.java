package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Bullet extends Moving{

    private final int R = 7;
    private Rectangle hitBox;

    Bullet(int currX, int currY, int currVX, int currVY, float currAngle, BufferedImage currImg){
        super(currX, currY, currVX, currVY, currAngle, currImg);
    }

    @Override
    protected void moveForwards() {
        setVx( (int) Math.round( R * Math.cos( Math.toRadians( getAngle() ) ) ) );
        setVy( (int) Math.round( R * Math.sin( Math.toRadians( getAngle() ) ) ) );

        setX(getX() + getVx());
        setY(getY() + getVy());

        checkBorder();
    }

    public void update(){
        moveForwards();
    }

    @Override
    public void drawImage(Graphics gameImage) {
        super.drawImage(gameImage);
    }
}
