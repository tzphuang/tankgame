package tankrotationexample.game;


import java.awt.*;

public abstract class PlayerStats implements Drawable{

    //this should be an aggregate class of the classes "ammo", "hpbar", "lives"
    //those 3 should not inherit any functionailty from PlayerStats
    //so it shoulder override the drawImage function
    //but still be able to draw its own image

    private HpBar hpBar;
    private Lives life;
    private Ammo ammo;
    private Tank tank;


    PlayerStats(HpBar currHpBar, Lives currLives, Ammo currAmmo, Tank currTank){
        this.hpBar = currHpBar;
        this.life = currLives;
        this.ammo = currAmmo;
        this.tank = currTank;
    }

    @Override
    public void drawImage(Graphics gameImage) {

    }
}
