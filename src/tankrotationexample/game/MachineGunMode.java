package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class MachineGunMode extends Powerup{
    BufferedImage mgBulletImg;

    MachineGunMode(int currX, int currY, int currVX, int currVY, float currAngle, BufferedImage currImg, BufferedImage currBulletImg){
        super(currX, currY, currVX, currVY, currAngle, currImg);
        this.mgBulletImg = currBulletImg;
    }

    public void applyPowerup(Tank currTank){
        currTank.setCurrentAmmoNum(1);
        currTank.setAmmo(mgBulletImg);
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

