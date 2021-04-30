package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RocketLauncherMode extends Powerup{
    BufferedImage rktBulletImg;

    RocketLauncherMode(int currX, int currY, int currVX, int currVY, float currAngle, BufferedImage currImg, BufferedImage currBulletImg){
        super(currX, currY, currVX, currVY, currAngle, currImg);
        this.rktBulletImg = currBulletImg;
    }

    public void applyPowerup(Tank currTank){
        currTank.setCurrentAmmoNum(2);
        currTank.setAmmo(rktBulletImg);
    }

    @Override
    public void drawImage(Graphics gameImage) {
        super.drawImage(gameImage);
    }

    @Override
    public void collisionDetected(GameObject currentObjectCollided) {
        applyPowerup((Tank) currentObjectCollided);
    }
}
