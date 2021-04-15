package tankrotationexample.menus;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TankControlsPanel extends JPanel{

    private BufferedImage menuBackground;
    private JButton controls;
    private Launcher lf;

    public TankControlsPanel(Launcher lf) {
        this.lf = lf;
        try {
            menuBackground = ImageIO.read(this.getClass().getClassLoader().getResource("controls.jpg"));
        } catch (IOException e) {
            System.out.println("Error cant read menu background");
            e.printStackTrace();
            System.exit(-3);
        }
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        controls = new JButton("Main Menu");
        controls.setFont(new Font("Courier New", Font.BOLD ,24));
        controls.setBounds(0,325,150,50);
        controls.addActionListener((actionEvent -> {
            this.lf.setFrame("start");
        }));


        this.add(controls);

}

    @Override
    public void paintComponent(Graphics g){
        //use a graphics2d object (g2)
        //and draw the image passed in (graphic g) which is a buffered image
        //onto the current menu's background image
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground,0,0,null);
    }
}
