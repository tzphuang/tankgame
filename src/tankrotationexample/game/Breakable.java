package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Breakable extends Wall{

    Breakable(int currX, int currY, int currAngle, BufferedImage currImage){
        super(currX, currY, currAngle, currImage);
    }

    int state = 1;

    @Override
    public void drawImage(Graphics gameImage) {
        super.drawImage(gameImage);
    }
}
