package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Unbreakable extends Wall{

    Unbreakable(int currX, int currY, float currAngle, BufferedImage currImage){
        super(currX, currY, currAngle, currImage);
    }

    @Override
    public void drawImage(Graphics gameImage) {
        super.drawImage(gameImage);
    }
}
