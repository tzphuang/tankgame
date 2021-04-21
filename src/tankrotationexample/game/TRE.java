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
                if(this.tick > 2000){
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

        this.t1.setX(724);
        this.t1.setY(468);

        this.t2.setX(300);
        this.t2.setY(300);

        //clears my game world objects so I can reinitialize it later
        overWorldObjects.clear();
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                                       GameConstants.GAME_SCREEN_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        BufferedImage t1img = null;
        BufferedImage t2img = null;
        BufferedImage unBreakWall = null;
        BufferedImage breakWall = null;
        BufferedImage heavyMgun = null;
        BufferedImage rLauncher = null;
        BufferedImage godMode = null;

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
                    //for each int representing a game object
                    switch(mapBit[currentColumn]){
                        //unbreakable wall
                        case "1":
                            Unbreakable curUnbreakWall = new Unbreakable();
                            curUnbreakWall.setX(currentColumn * 50);
                            curUnbreakWall.setY(currentRow * 50);
                            curUnbreakWall.setImg(unBreakWall);
                            this.overWorldObjects.add(curUnbreakWall);
                            break;

                        //breakable wall
                        case "2":
                            Breakable curBreakableWall = new Breakable();
                            curBreakableWall.setX(currentColumn * 50);
                            curBreakableWall.setY(currentRow * 50);
                            curBreakableWall.setImg(breakWall);
                            this.overWorldObjects.add(curBreakableWall);
                            break;

                        //heavy machine gun
                        case "3":
                            MachineGun curMgun = new MachineGun();
                            curMgun.setX(currentColumn * 50);
                            curMgun.setY(currentRow * 50);
                            curMgun.setImg(heavyMgun);
                            this.overWorldObjects.add(curMgun);
                            break;

                        //rocket launcher
                        case "4":
                            RocketLauncher curRLauncher = new RocketLauncher();
                            curRLauncher.setX(currentColumn * 50);
                            curRLauncher.setY(currentRow * 50);
                            curRLauncher.setImg(rLauncher);
                            this.overWorldObjects.add(curRLauncher);
                            break;

                        //god mode
                        case "5":
                            GodMode curPwrUp = new GodMode();
                            curPwrUp.setX(currentColumn * 50);
                            curPwrUp.setY(currentRow * 50);
                            curPwrUp.setImg(godMode);
                            this.overWorldObjects.add(curPwrUp);
                            break;
                    }
                }
            }



        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        t1 = new Tank(724, 468, 0, 0, 0, t1img);
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);

        t2 = new Tank (300, 300, 0, 0, 0, t2img);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc2);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_SCREEN_HEIGHT);

        //does not yet draw any images as my gameObject is not fully finished
        //draws all objects for the overWorld stored in overWorldObjects
        this.overWorldObjects.forEach(GameObject -> GameObject.drawImage(buffer));

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        g2.drawImage(world,0,0,null);
    }

}
