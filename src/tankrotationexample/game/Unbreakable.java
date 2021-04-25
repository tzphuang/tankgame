package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Unbreakable extends Wall{

    Unbreakable(int currX, int currY, int currAngle, BufferedImage currImage){
        super(currX, currY, currAngle, currImage);
    }

    @Override
    public void checkBorder() {

    }

    @Override
    public void drawImage(Graphics gameImage) {
        super.drawImage(gameImage);
    }
}
