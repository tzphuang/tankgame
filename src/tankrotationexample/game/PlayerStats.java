package tankrotationexample.game;


import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerStats implements Drawable{

    //this should be an aggregate class of the classes "ammo", "hpbar", "lives"
    //those 3 should not inherit any functionailty from PlayerStats
    //so it shoulder override the drawImage function
    //but still be able to draw its own image

    //right tank
    private Tank tank1;
    //left tank
    private Tank tank2;

    PlayerStats(Tank currTank1, Tank currTank2){
        this.tank1 = currTank1;
        this.tank2 = currTank2;
    }

    @Override
    //this takes a blank buffered image to draw to
    public void drawImage(Graphics gameImage) {

        BufferedImage leftStatsImg = new BufferedImage(GameConstants.STATS_SCREEN_WIDTH, GameConstants.STATS_SCREEN_HEIGHT,BufferedImage.TYPE_INT_RGB);
        Graphics2D leftG2d = leftStatsImg.createGraphics();

        //changing colored hp bar
        int currTank2Hp = this.tank2.getHitPoints();
        if(100 >= currTank2Hp && 60 < currTank2Hp){
            leftG2d.setColor(Color.GREEN);
        }
        else if(60 >= currTank2Hp && 30 < currTank2Hp){
            leftG2d.setColor(Color.ORANGE);
        }
        else if(30 >= currTank2Hp && 0 < currTank2Hp){
            leftG2d.setColor(Color.RED);
        }
        else{
            leftG2d.setColor(Color.YELLOW);
        }

        String leftHp;
        if(100 >= currTank2Hp) {
            leftG2d.fillRect(150, 32, currTank2Hp * 2, 30);
            leftHp = "HP: " + currTank2Hp;
        }else{
            leftHp = "HP: INVULNERABLE";
        }

        leftG2d.setFont(new Font("Helvetica", Font.BOLD, 36));
        leftG2d.setColor(Color.WHITE);
        leftG2d.drawString(leftHp, 10, 60);

        String leftLife = "LIVES: " + tank2.getLives();
        leftG2d.drawString(leftLife, 10, 120);

        String leftAmmo = "AMMO:";
        leftG2d.drawString(leftAmmo, 10, 180);

        leftG2d.drawImage(tank2.getAmmo(), 180, 170, null);


        BufferedImage rightStatsImg = new BufferedImage(GameConstants.STATS_SCREEN_WIDTH, GameConstants.STATS_SCREEN_HEIGHT,BufferedImage.TYPE_INT_RGB);
        Graphics2D rightG2d = rightStatsImg.createGraphics();

        //changing colored hp bar
        int currTank1Hp = this.tank1.getHitPoints();
        if(100 >= currTank1Hp && 60 < currTank1Hp){
            rightG2d.setColor(Color.GREEN);
        }
        else if(60 >= currTank1Hp && 30 < currTank1Hp){
            rightG2d.setColor(Color.ORANGE);
        }
        else if(30 >= currTank1Hp && 0 < currTank1Hp){
            rightG2d.setColor(Color.RED);
        }
        else{
            rightG2d.setColor(Color.YELLOW);
        }

        String rightHp;
        if(100 >= currTank1Hp) {
            rightG2d.fillRect(150, 32, currTank1Hp * 2, 30);
            rightHp = "HP: " + currTank1Hp;
        }else{
            rightHp = "HP: INVULNERABLE";
        }

        rightG2d.setFont(new Font("Helvetica", Font.BOLD, 36));
        rightG2d.setColor(Color.WHITE);
        rightG2d.drawString(rightHp, 10, 60);

        String rightLife = "LIVES: " + tank1.getLives();
        rightG2d.drawString(rightLife, 10, 120);

        String rightAmmo = "AMMO:";
        rightG2d.drawString(rightAmmo, 10, 180);

        rightG2d.drawImage(tank1.getAmmo(), 180, 170, null);

        //draw both buffered image to game screen
        gameImage.drawImage(leftStatsImg, GameConstants.LEFT_STATS_TOP_LEFT_CORNER_X, GameConstants.LEFT_STATS_TOP_LEFT_CORNER_Y, null);
        gameImage.drawImage(rightStatsImg, GameConstants.RIGHT_STATS_TOP_LEFT_CORNER_X, GameConstants.RIGHT_STATS_TOP_LEFT_CORNER_Y, null);
    }
}
