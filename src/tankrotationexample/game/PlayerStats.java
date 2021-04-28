package tankrotationexample.game;


import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerStats implements Drawable{

    //this should be an aggregate class of the classes "ammo", "hpbar", "lives"
    //those 3 should not inherit any functionailty from PlayerStats
    //so it shoulder override the drawImage function
    //but still be able to draw its own image

    private HpBar hpBar;
    private Lives life;
    private Ammo ammo;
    private Tank tank;

    PlayerStats(Tank currTank){
        this.hpBar = new HpBar(currTank);
        this.life = new Lives(currTank);
        this.ammo = new Ammo(currTank);
        this.tank = currTank;
    }

    public void update(){
        this.hpBar.update();
        this.life.update();
        this.ammo.update();
    }

    @Override
    //this takes a blank buffered image to draw to
    public void drawImage(Graphics gameImage) {

        BufferedImage statsScreenTank;

    }
}
