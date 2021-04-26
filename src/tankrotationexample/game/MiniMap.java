package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class MiniMap implements Drawable{
    BufferedImage worldImage;

    MiniMap(BufferedImage currWorldImage){
        this.worldImage = currWorldImage;
    }

    @Override
    public void drawImage(Graphics gameImage) {

        BufferedImage miniMap = worldImage.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        gameImage.drawImage(miniMap,1800,0,null); //why is this X 1800?


    }
}
