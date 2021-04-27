package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tank extends Moving{

    private int hitPoints;
    private int lives;
    private BufferedImage ammo;

    private long timeOfLastShootPress;
    private long timeCurrent;

    //heavy machinegun for 1
    //rocket launcher for 2
    int currentBullet;


    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    Tank(int currX, int currY, int currVX, int currVY, int currAngle, BufferedImage currImg){
        super(currX, currY, currVX, currVY, currAngle, currImg);
        this.hitPoints = 100;
        this.lives = 3;
    }

    public void setAmmo(BufferedImage ammo) {
        this.ammo = ammo;
    }

    public BufferedImage getAmmo() {
        return ammo;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {
        this.ShootPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed) {
            //System.out.println("shooting button pressed");
            if(timeCurrent > timeOfLastShootPress + 60) {
                this.spawnBullet();
            }
        }
    }

    private void spawnBullet(){

    }


    @Override
    public void drawImage(Graphics gameImage) {
        super.drawImage(gameImage);
    }
}
