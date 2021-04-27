package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Tank extends Moving{

    private int hitPoints;
    private int lives;

    //heavy machinegun for 1
    //rocket launcher for 2
    private int currentAmmoNum;
    private BufferedImage ammo;

    //used in the TankControl for this tank to make sure we can only spawn
    //another ammo after a shoot button is pressed then released
    private boolean shootPressRelease = true;

    private ArrayList<Bullet> listOfBullets;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    Tank(int currX, int currY, int currVX, int currVY, float currAngle, BufferedImage currImg){
        super(currX, currY, currVX, currVY, currAngle, currImg);
        this.hitPoints = 100;
        this.lives = 3;
        currentAmmoNum = 1;//sets the tank to be on machinegun mode at creation
    }

    public void setShootPressed(boolean currShootPressed) {
        ShootPressed = currShootPressed;
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
            if(shootPressRelease) {
                this.spawnBullet();
            }
        }
    }

    private void spawnBullet(){
        Bullet currBullet;

        switch(currentAmmoNum){
            case 1:
                currBullet = new MachineGun(this.getX(),this.getY(),0, 0, this.getAngle(), ammo);
                break;

            case 2:
                currBullet = new RocketLauncher(this.getX(),this.getY(),0, 0, this.getAngle(), ammo);
                break;
        }

    }


    @Override
    public void drawImage(Graphics gameImage) {
        super.drawImage(gameImage);
    }
}
