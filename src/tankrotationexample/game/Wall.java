package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall extends Stationary{

    Wall(int currX, int currY, float currAngle, BufferedImage currImage){
        super(currX, currY, currAngle, currImage);
    }

    @Override
    public void drawImage(Graphics gameImage) {
        super.drawImage(gameImage);
    }
}
