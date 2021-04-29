package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HpBar implements Drawable {
    private Tank tank;
    private int currHpValue;

    HpBar(Tank currTank){
        this.tank = currTank;
        currHpValue = this.tank.getHitPoints();
    }

    public void update(){
        currHpValue = this.tank.getHitPoints();
    }

    public void drawBufferedImage(BufferedImage currBuffer){

    }

    @Override
    public void drawImage(Graphics gameImage) {

    }
}
