package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Tank extends Moving{

    private final int MAXNUMBULLETSPERTANK = 15;

    private int hitPoints;
    private int lives;

    //heavy machinegun for 1
    //rocket launcher for 2
    private int currentAmmoNum;
    //this is the buffered image for ammo
    private BufferedImage ammo;

    //used in the TankControl for this tank to make sure we can only spawn
    //another ammo after a shoot button is pressed then released
    private boolean shootPressRelease = true;
    private long tickTimer;

    private int autoTimer;

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
        listOfBullets = new ArrayList<>();
        autoTimer = 144;
    }

    public void setShootPressRelease(boolean currShootPressed) {
        ShootPressed = currShootPressed;
    }

    //sets ammo image
    public void setAmmo(BufferedImage ammo) {
        this.ammo = ammo;
    }

    public void setCurrentAmmoNum(int newAmmoNum){
        currentAmmoNum = newAmmoNum;
    }

    public void setTickTimer(long currtTimer){
        this.tickTimer = currtTimer;
    }

    public void setHitPoints(int currHitPoints){
        this.hitPoints = currHitPoints;
    }

    public void setLives(int currLives){
        this.lives = currLives;
    }

    //gets ammo image
    public BufferedImage getAmmo() {
        return ammo;
    }

    public int getCurrentAmmoNum() {
        return currentAmmoNum;
    }

    public int getHitPoints(){
        return this.hitPoints;
    }

    public int getLives() {
        return this.lives;
    }

    public ArrayList<Bullet> getListOfBullets(){
        return this.listOfBullets;
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
        this.shootPressRelease = true;
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

        //checks if hp is 0 or below and will compensate with an exchange of a life for full hp
        if(0 >= this.hitPoints){
            this.lives -= 1;
            this.hitPoints = 100;
        }

        //hp will automatically lower by itself on each update back to 100
        if(100 < this.hitPoints){
            this.hitPoints -= 2;

            //makes sure that your hitpoints dont get below 100 from the autoloss of hp
            if(100 >= this.hitPoints){
                this.hitPoints = 100;
            }
        }

        //autofire rate for different weapons is slower or faster depending on weapon type
        if(this.currentAmmoNum == 1){//machinegun autoTimer setter
            this.autoTimer = 48;
        }else if(this.currentAmmoNum == 2){//rocketLauncher autoTimer
            this.autoTimer = 144;
        }
        else{
            this.autoTimer = 72;
        }

        if (this.ShootPressed) {
            //spawn a bullet on first check of shooting button being pressed
            //shootPressRelease will only be true after the user releases the shoot button
            if(shootPressRelease || (tickTimer % autoTimer == 0)) {
                this.spawnBullet();
                shootPressRelease = false;
                //System.out.println("bullet spawned");
                //System.out.println("Number of Bullets is:" + listOfBullets.size());
            }

        }
        //every game tic we will update each bullet's distance
        this.listOfBullets.forEach(Bullet -> Bullet.update());
    }

    private void spawnBullet(){
        //used as a single reference for creation purposes
        Bullet currBullet;

        //each tank gets 15 bullets
        //maybe implement if game lags really hard
        if (MAXNUMBULLETSPERTANK >= listOfBullets.size()){

        }

        switch(currentAmmoNum){
            case 1:
                currBullet = new MachineGun(this.getX(),this.getY(),0, 0, this.getAngle(), ammo);
                listOfBullets.add(currBullet);
                break;

            case 2:
                currBullet = new RocketLauncher(this.getX(),this.getY(),0, 0, this.getAngle(), ammo);
                listOfBullets.add(currBullet);
                break;

            default:
                System.out.println("spawn bullet went to default, something is wrong");
                break;
        }
    }


    @Override
    public void drawImage(Graphics gameImage) {
        super.drawImage(gameImage);
        this.listOfBullets.forEach(Bullet -> Bullet.drawImage(gameImage));
    }

    public void collisionDetected(GameObject currentObjectCollided){
        if (currentObjectCollided instanceof Bullet){
            this.hitPoints -= ((Bullet) currentObjectCollided).getBulletDamage();

            //I can just delete it in the TRE? itll be better i think
            //delete bullet since it has collided with the tank already
            //currentObjectCollided = null;
        }
        else if (currentObjectCollided instanceof Stationary){
            this.moveBackwards();
        }
        else if (currentObjectCollided instanceof Powerup){
            ((Powerup) currentObjectCollided).applyPowerup(this);
        }
        else{
            //this shouldnt ever be activated
            System.out.println("else statement found in collision detected from tank class");
        }

    }
}
