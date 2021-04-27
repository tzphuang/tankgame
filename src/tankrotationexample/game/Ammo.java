package tankrotationexample.game;

import java.awt.*;

public class Ammo implements Drawable{

    private Tank tank;

    Ammo(Tank currTank){
        this.tank = currTank;
    }

    @Override
    public void drawImage(Graphics gameImage) {

    }
}
