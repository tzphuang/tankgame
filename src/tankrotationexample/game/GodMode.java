package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.StandardWatchEventKinds;

public class GodMode extends Powerup{

    GodMode(int currX, int currY, int currVX, int currVY, float currAngle, BufferedImage currImg){
        super(currX, currY, currVX, currVY, currAngle, currImg);
    }

    public void applyPowerup(Tank currTank){
        currTank.setHitPoints(250);
    }

    @Override
    public void drawImage(Graphics gameImage) {
        super.drawImage(gameImage);
    }

    @Override
    public void collisionDetected(GameObject currentObjectCollided) {
        if(currentObjectCollided instanceof Tank){
            applyPowerup((Tank) currentObjectCollided);
        }
    }
}
