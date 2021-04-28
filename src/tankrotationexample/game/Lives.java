package tankrotationexample.game;

import java.awt.*;

public class Lives implements Drawable{

    private Tank tank;
    private int lives;

    Lives(Tank currTank){
        this.tank = currTank;
        this.lives = this.tank.getLives();
    }

    public void update(){
        this.lives = this.tank.getLives();
    }

    @Override
    public void drawImage(Graphics gameImage) {

    }
}
