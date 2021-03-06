/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;


import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class TRE extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    private Launcher lf;
    private long tick = 0;
    private ArrayList<GameObject> overWorldObjects;
    private SplitScreen tankSplitScreen;
    private MiniMap gameMiniMap;
    private PlayerStats tankStats;
    private ArrayList<Bullet> bulletArrayCollisionDetection;

    public TRE(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
                this.tick++;

                //updates the tick timer for auto shooting
                this.t1.setTickTimer(tick);
                this.t2.setTickTimer(tick);

                // update tank
                this.t1.update();
                this.t2.update();
                //checks if things are colliding with each other
                checkCollisions();

                this.repaint();   // redraw game
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 6000 frames have been drawn
                 * also ends the game when tank1 has no more life and no more hp
                 * or ends the game when t2 has no more life and no more hp
                 */
                //if(this.tick > 15000 || (0 >= t1.getLives() && 0 >= t1.getHitPoints()) || (0 >= t2.getLives() && 0 >= t2.getHitPoints())){
                if((0 >= t1.getLives() && 0 >= t1.getHitPoints()) || (0 >= t2.getLives() && 0 >= t2.getHitPoints())){
                    this.lf.setFrame("end");
                    return;
                }
            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;

        //right tank
        this.t1.setX(GameConstants.RIGHT_TANK_STARTING_LOCATION_X);
        this.t1.setY(GameConstants.RIGHT_TANK_STARTING_LOCATION_Y);

        //left tank
        this.t2.setX(GameConstants.LEFT_TANK_STARTING_LOCATION_X);
        this.t2.setY(GameConstants.LEFT_TANK_STARTING_LOCATION_Y);

    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                                       GameConstants.WORLD_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        overWorldObjects = new ArrayList<>();

        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */

            //Reading in Map.txt to load in the map to game
            InputStreamReader isr = new InputStreamReader(TRE.class.getClassLoader().getResourceAsStream("Map.txt"));
            BufferedReader mapReader = new BufferedReader(isr);
            //read in the first row in Map.txt, which is "48    48", which are the dimensions of my map
            String mapCurRow = mapReader.readLine();
            //checks to see if the first line is null
            if(mapCurRow == null){
                throw new IOException("no data in Map.txt");
            }
            String[] mapBit = mapCurRow.split("\t");
            int numColumns = Integer.parseInt(mapBit[0]);
            int numRows = Integer.parseInt(mapBit[1]);

            for(int currentRow = 0; currentRow < numRows; currentRow++){

                //read next row in map
                mapCurRow = mapReader.readLine();
                mapBit = mapCurRow.split("\t");

                for(int currentColumn = 0; currentColumn < numColumns; currentColumn++){

                    //@@@CHECKER to see if it works
                    //System.out.print(mapBit[currentColumn] + " ");
                    //this creates different over world objects such as walls/unbreakable walls/weapon&other powerups
                    switch(mapBit[currentColumn]){

                        //unbreakable wall
                        case "1":
                            Unbreakable curUnbreakWall = new Unbreakable(currentColumn * 50, currentRow * 50, 0,Resource.getResourceImg("unBreakWall"));
                            this.overWorldObjects.add(curUnbreakWall);
                            break;

                        //breakable wall
                        case "2":
                            Breakable curBreakableWall = new Breakable(currentColumn * 50, currentRow * 50, 0, Resource.getResourceImg("breakWall"));
                            this.overWorldObjects.add(curBreakableWall);
                            break;

                        //heavy machine gun
                        case "3":
                            MachineGunMode curMgun = new MachineGunMode(currentColumn * 50, currentRow * 50, 0, 0, 0, Resource.getResourceImg("heavyMgun"), Resource.getResourceImg("mgBulletImg"));
                            this.overWorldObjects.add(curMgun);
                            break;

                        //rocket launcher
                        case "4":
                            RocketLauncherMode curRLauncher = new RocketLauncherMode(currentColumn * 50, currentRow * 50, 0, 0, 0, Resource.getResourceImg("rLauncher"), Resource.getResourceImg("rocketBulletImg"));
                            this.overWorldObjects.add(curRLauncher);
                            break;

                        //god mode
                        case "5":
                            GodMode curPwrUp = new GodMode(currentColumn * 50, currentRow * 50, 0, 0, 0, Resource.getResourceImg("godMode"));
                            this.overWorldObjects.add(curPwrUp);
                            break;

                        //default:
                        //    System.out.println("current column is:" + currentColumn + "current mapbit is:" + mapBit[currentColumn]);
                        //    break;
                    }
                }
                //@@@CHECKER to see if it works
                //System.out.println();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        //right tank
        t1 = new Tank(GameConstants.RIGHT_TANK_STARTING_LOCATION_X, GameConstants.RIGHT_TANK_STARTING_LOCATION_Y, 0, 0, 0, Resource.getResourceImg("t1img"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        t1.setCurrentAmmoNum(1); //1 represents machine gun ammo, while 2 is rockets
        t1.setAmmo(Resource.getResourceImg("mgBulletImg"));

        //left tank
        t2 = new Tank (GameConstants.LEFT_TANK_STARTING_LOCATION_X, GameConstants.LEFT_TANK_STARTING_LOCATION_Y, 0, 0, 0, Resource.getResourceImg("t2img"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.setBackground(Color.CYAN);
        this.lf.getJf().addKeyListener(tc2);
        t2.setCurrentAmmoNum(1); //1 represents machine gun ammo, while 2 is rockets
        t2.setAmmo(Resource.getResourceImg("mgBulletImg"));

        //adds the references into the tank split screen inside this initialization block to create the split screen later
        tankSplitScreen = new SplitScreen(t1, t2, world);

        //creating a new minimap with the world image reference stored
        gameMiniMap = new MiniMap(world);

        //initializing the stats for tank1
        tankStats = new PlayerStats(t1, t2);

    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);

        //does not yet draw any images as my gameObject is not fully finished
        //draws all objects for the overWorld stored in overWorldObjects
        this.overWorldObjects.forEach(GameObject -> GameObject.drawImage(buffer));

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);

        tankSplitScreen.drawImage(g2); //what happens if i change this to buffer?

        tankStats.drawImage(g2);

        g2.scale(.2, .1);
        gameMiniMap.drawImage(g2); ////what happens if i change this to buffer?

        //draws finished buffered image to the screen after every component is added
        //g2.drawImage(world,0,0,null);
        //g2.scale(1.0,1.0);
    }

    public void checkCollisions(){
        //checks through bullet arrays to see if their bounce number is 0 that means they have exceeded their bounces
        //meaning they will be removed
        bulletArrayCollisionDetection = t1.getListOfBullets();
        for(int index = 0; index < bulletArrayCollisionDetection.size(); index++){
            if(0 >= bulletArrayCollisionDetection.get(index).getBounceNum()){
                bulletArrayCollisionDetection.remove(index);
                index--;
            }
        }

        bulletArrayCollisionDetection = t2.getListOfBullets();
        for(int index = 0; index < bulletArrayCollisionDetection.size(); index++){
            if(0 >= bulletArrayCollisionDetection.get(index).getBounceNum()){
                bulletArrayCollisionDetection.remove(index);
                index--;
            }
        }

        if(this.t1.getHitBox().intersects(this.t2.getHitBox())){
            //produces bug where if you ram a tank from behind itll make the tank go backwards
            this.t1.moveBackwards();
            this.t1.moveBackwards();
            this.t2.moveBackwards();
            this.t2.moveBackwards();
        }

        //check collisions will loop through all objects and call their respective collisionDetected method

        //compares tank1's bullets to tank2's hitbox
        bulletArrayCollisionDetection = t1.getListOfBullets();
        for(int index = 0; index < bulletArrayCollisionDetection.size(); index++){
            if( this.t2.getHitBox().intersects( bulletArrayCollisionDetection.get(index).getHitBox() ) ) {
                t2.collisionDetected(bulletArrayCollisionDetection.get(index));
                bulletArrayCollisionDetection.remove(bulletArrayCollisionDetection.get(index));//removes collided bullet
                index--; //updates index since one bullet is now removed
            }
        }

        //compares tank2's bullets to tank1's hitbox
        bulletArrayCollisionDetection = t2.getListOfBullets();
        for(int index = 0; index < bulletArrayCollisionDetection.size(); index++){
            if( this.t1.getHitBox().intersects( bulletArrayCollisionDetection.get(index).getHitBox() ) ) {
                t1.collisionDetected(bulletArrayCollisionDetection.get(index));
                bulletArrayCollisionDetection.remove(bulletArrayCollisionDetection.get(index));//removes collided bullet
                index--; //updates index since one bullet is now removed
            }
        }

        //checks every moving object with overworld object and applies collisionDetected
        for(int index = 0; index < overWorldObjects.size(); index++){
            if(this.t1.getHitBox().intersects( this.overWorldObjects.get(index).getHitBox() ) ){
                this.t1.collisionDetected(this.overWorldObjects.get(index));
            }
            else if(this.t2.getHitBox().intersects( this.overWorldObjects.get(index).getHitBox() ) ){
                this.t2.collisionDetected(this.overWorldObjects.get(index));
            }

            //checking tank1's bullets against the overworld object
            bulletArrayCollisionDetection = t1.getListOfBullets();
            for(int bulletIndex = 0; bulletIndex < bulletArrayCollisionDetection.size(); bulletIndex++){
                if(bulletArrayCollisionDetection.get(bulletIndex).getHitBox().intersects(overWorldObjects.get(index).getHitBox())){
                    bulletArrayCollisionDetection.get(bulletIndex).collisionDetected(overWorldObjects.get(index));
                    if(overWorldObjects.get(index) instanceof Breakable){
                        bulletArrayCollisionDetection.remove(bulletIndex);
                        bulletIndex--;
                        overWorldObjects.remove(index);
                        index--;
                    }
                }
            }

            bulletArrayCollisionDetection = t2.getListOfBullets();
            for(int bulletIndex = 0; bulletIndex < bulletArrayCollisionDetection.size(); bulletIndex++){
                if(bulletArrayCollisionDetection.get(bulletIndex).getHitBox().intersects(overWorldObjects.get(index).getHitBox())){
                    bulletArrayCollisionDetection.get(bulletIndex).collisionDetected(overWorldObjects.get(index));
                    if(overWorldObjects.get(index) instanceof Breakable){
                        bulletArrayCollisionDetection.remove(bulletIndex);
                        bulletIndex--;
                        overWorldObjects.remove(index);
                        index--;
                    }
                }
            }

        }


    }

}
