package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class MiniMap implements Drawable{
    private BufferedImage worldImage;

    MiniMap(BufferedImage currWorldImage){
        this.worldImage = currWorldImage;
    }

    @Override
    public void drawImage(Graphics gameImage) {

        BufferedImage miniMap = worldImage.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        gameImage.drawImage(miniMap,GameConstants.MINIMAP_TOP_LEFT_CORNER_X * 5,GameConstants.MINIMAP_TOP_LEFT_CORNER_Y,null);


    }
}
