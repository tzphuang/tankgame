package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MachineGun extends NonExplosive{

    MachineGun(int currX, int currY, int currVX, int currVY, float currAngle, BufferedImage currImg){
        super(currX, currY, currVX, currVY, currAngle, currImg);
        setBulletDamage(5);
    }

    @Override
    public void drawImage(Graphics gameImage) {
        super.drawImage(gameImage);
    }

    public void collisionDetected(GameObject currentObjectCollided){

    }
}
