package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SplitScreen implements Drawable{
    Tank t1;
    Tank t2;
    BufferedImage worldImage;
    int leftSubImageX;
    int leftSubImageY;
    int rightSubImageX;
    int rightSubImageY;

    SplitScreen(Tank currT1, Tank currT2, BufferedImage currWorldImage){
        this.t1 = currT1;
        this.t2 = currT2;
        this.worldImage = currWorldImage;
    }

    private void setTankCoordinates(){
        leftSubImageX = this.t2.getX() - (GameConstants.SPLIT_SCREEN_WIDTH/2);
        leftSubImageY = this.t2.getY() - (GameConstants.SPLIT_SCREEN_HEIGHT/2);
        rightSubImageX = this.t1.getX() - (GameConstants.SPLIT_SCREEN_WIDTH/2);
        rightSubImageY = this.t1.getY() - (GameConstants.SPLIT_SCREEN_HEIGHT/2);
    }

    private void checkSplitScreenBounds(){

        if(leftSubImageX < GameConstants.SCREEN_LEFT_BOUND){ //checks left side bound
            leftSubImageX = GameConstants.SCREEN_LEFT_BOUND;
        }
        else if(leftSubImageX > GameConstants.SCREEN_RIGHT_BOUND){ //checks right side bound
            leftSubImageX = GameConstants.SCREEN_RIGHT_BOUND;
        }

        if(leftSubImageY < GameConstants.SCREEN_CEILING_BOUND){ //checks vertical bound
            leftSubImageY = GameConstants.SCREEN_CEILING_BOUND;
        }
        else if(leftSubImageY > GameConstants.SCREEN_FLOOR_BOUND){ //checks vertical floor
            leftSubImageY = GameConstants.SCREEN_FLOOR_BOUND;
        }

        if(rightSubImageX < GameConstants.SCREEN_LEFT_BOUND){
            rightSubImageX = GameConstants.SCREEN_LEFT_BOUND;
        }
        else if(rightSubImageX > GameConstants.SCREEN_RIGHT_BOUND){
            rightSubImageX = GameConstants.SCREEN_RIGHT_BOUND;
        }

        if(rightSubImageY < GameConstants.SCREEN_CEILING_BOUND){
            rightSubImageY = GameConstants.SCREEN_CEILING_BOUND;
        }
        else if(rightSubImageY > GameConstants.SCREEN_FLOOR_BOUND){
            rightSubImageY = GameConstants.SCREEN_FLOOR_BOUND;
        }
    }

    @Override
    public void drawImage(Graphics gameImage) {
        this.setTankCoordinates();
        this.checkSplitScreenBounds();

        //draws left tank's game screen
        //where the left tank is the center of the left screen
        BufferedImage leftTankScreen = this.worldImage.getSubimage(leftSubImageX, leftSubImageY,GameConstants.SPLIT_SCREEN_WIDTH, GameConstants.SPLIT_SCREEN_HEIGHT);

        //draws right tank's game screen
        //where the right tank is the center of the right screen
        BufferedImage rightTankScreen = this.worldImage.getSubimage(rightSubImageX, rightSubImageY,GameConstants.SPLIT_SCREEN_WIDTH, GameConstants.SPLIT_SCREEN_HEIGHT);

        gameImage.drawImage(leftTankScreen, GameConstants.LEFT_TANK_TOP_LEFT_CORNER_X, GameConstants.LEFT_TANK_TOP_LEFT_CORNER_Y, null);
        gameImage.drawImage(rightTankScreen, GameConstants.RIGHT_TANK_TOP_LEFT_CORNER_X, GameConstants.RIGHT_TANK_TOP_LEFT_CORNER_Y, null);

    }
}
