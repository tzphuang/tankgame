package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GodMode extends Powerup{

    GodMode(int currX, int currY, int currVX, int currVY, int currAngle, BufferedImage currImg){
        super(currX, currY, currVX, currVY, currAngle, currImg);
    }

    @Override
    public void drawImage(Graphics gameImage) {
        super.drawImage(gameImage);
    }
}
