package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Bullet extends Moving{

    private final int R = 7;
    private int bulletDamage;
    private int bounceNum;

    Bullet(int currX, int currY, int currVX, int currVY, float currAngle, BufferedImage currImg){
        super(currX, currY, currVX, currVY, currAngle, currImg);
        this.bulletDamage = 0;
        this.bounceNum = 1;
    }

    public void setBulletDamage(int currBulletDamage){
        this.bulletDamage = currBulletDamage;
    }

    public int getBulletDamage(){
        return bulletDamage;
    }

    @Override
    protected void moveForwards() {
        setVx( (int) Math.round( R * Math.cos( Math.toRadians( getAngle() ) ) ) );
        setVy( (int) Math.round( R * Math.sin( Math.toRadians( getAngle() ) ) ) );

        setX(getX() + getVx());
        setY(getY() + getVy());

        checkBorder();
        this.setHitBox(getX(),getY());
    }

    public int getBounceNum(){
        return this.bounceNum;
    }

    public void setBounceNum(int currBounceNum){
        this.bounceNum = currBounceNum;
    }

    protected void bounceRotate(){
        setAngle(getAngle() + 60);
        this.bounceNum -= 1;
    }

    public void update(){
        moveForwards();
    }

    @Override
    public void drawImage(Graphics gameImage) {
        super.drawImage(gameImage);
    }

    public void collisionDetected(GameObject currentObjectCollided){
        if(currentObjectCollided instanceof Unbreakable){
            this.bounceRotate();
        }
    }
}
