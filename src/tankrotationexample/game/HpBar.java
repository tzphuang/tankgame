package tankrotationexample.game;

import java.awt.*;

public class HpBar implements Drawable {
    private Tank tank;

    HpBar(Tank currTank){
        this.tank = currTank;
    }

    @Override
    public void drawImage(Graphics gameImage) {

    }
}
