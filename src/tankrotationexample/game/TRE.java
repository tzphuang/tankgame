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
    ArrayList<GameObject> overWorldObjects;
    SplitScreen tankSplitScreen;
    MiniMap gameMiniMap;

    public TRE(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
                this.tick++;
                this.t1.update(); // update tank
                this.t2.update();
                this.repaint();   // redraw game
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
                if(this.tick > 6000){
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

        BufferedImage t1img = null;
        BufferedImage t2img = null;
        BufferedImage unBreakWall = null;
        BufferedImage breakWall = null;
        BufferedImage heavyMgun = null;
        BufferedImage rLauncher = null;
        BufferedImage godMode = null;
        BufferedImage mgBulletImg = null;
        BufferedImage rocketBulletImg = null;

        overWorldObjects = new ArrayList<>();

        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            t1img = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tankred.png")));
            t2img = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tankblue.png")));
            unBreakWall = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("unbreakable.png")));
            breakWall = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("breakable.png")));
            heavyMgun = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("heavy machine gun.png")));
            rLauncher = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("rocket launcher.png")));
            godMode = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tankgold.png")));
            mgBulletImg = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("bullet.png")));
            rocketBulletImg = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("rocket.png")));

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
                            Unbreakable curUnbreakWall = new Unbreakable(currentColumn * 50, currentRow * 50, 0,unBreakWall);
                            this.overWorldObjects.add(curUnbreakWall);
                            break;

                        //breakable wall
                        case "2":
                            Breakable curBreakableWall = new Breakable(currentColumn * 50, currentRow * 50, 0, breakWall);
                            this.overWorldObjects.add(curBreakableWall);
                            break;

                        //heavy machine gun
                        case "3":
                            MachineGunMode curMgun = new MachineGunMode(currentColumn * 50, currentRow * 50, 0, 0, 0, heavyMgun);
                            this.overWorldObjects.add(curMgun);
                            break;

                        //rocket launcher
                        case "4":
                            RocketLauncherMode curRLauncher = new RocketLauncherMode(currentColumn * 50, currentRow * 50, 0, 0, 0, rLauncher);
                            this.overWorldObjects.add(curRLauncher);
                            break;

                        //god mode
                        case "5":
                            GodMode curPwrUp = new GodMode(currentColumn * 50, currentRow * 50, 0, 0, 0, godMode);
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
        t1 = new Tank(GameConstants.RIGHT_TANK_STARTING_LOCATION_X, GameConstants.RIGHT_TANK_STARTING_LOCATION_Y, 0, 0, 0, t1img);
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        t1.setCurrentAmmoNum(1); //1 represents machine gun ammo, while 2 is rockets
        t1.setAmmo(mgBulletImg);

        //left tank
        t2 = new Tank (GameConstants.LEFT_TANK_STARTING_LOCATION_X, GameConstants.LEFT_TANK_STARTING_LOCATION_Y, 0, 0, 0, t2img);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.setBackground(Color.CYAN);
        this.lf.getJf().addKeyListener(tc2);
        t2.setCurrentAmmoNum(1); //1 represents machine gun ammo, while 2 is rockets
        t2.setAmmo(mgBulletImg);

        //adds the references into the tank split screen inside this initialization block to create the split screen later
        tankSplitScreen = new SplitScreen(t1, t2, world);

        //creating a new minimap with the world image reference stored
        gameMiniMap = new MiniMap(world);
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

        //draws finished buffered image to the screen after every component is added
        //g2.drawImage(world,0,0,null);

        tankSplitScreen.drawImage(g2);

        g2.scale(.2, .1);
        gameMiniMap.drawImage(g2);
        //g2.scale(1.0,1.0);
    }

}
