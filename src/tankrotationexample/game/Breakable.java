package tankrotationexample.game;

public class Breakable extends Wall{

    int state = 1;

    @Override
    public void checkBorder() {

    }

    @Override
    public void drawImage() {
        //draw the image of the wall if wall is not destroyed (wall state 0 or less means destroyed)
        if(state > 0){

        }

    }
}
