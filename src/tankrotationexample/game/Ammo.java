package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Ammo implements Drawable{

    private Tank tank;
    private BufferedImage ammoImg;

    Ammo(Tank currTank){
        this.tank = currTank;
        this.ammoImg = this.tank.getAmmo();
    }

    public void update(){
        this.ammoImg = this.tank.getAmmo();
    }

    @Override
    public void drawImage(Graphics gameImage) {

    }
}
