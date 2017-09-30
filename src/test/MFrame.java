package test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MFrame extends JFrame {
    BufferedImage bufferedImage;
    MFrame(){
        setLayout(null);
        setSize(1600,1000);
        setLocationRelativeTo(null);
        MFrame2 frame2 = new MFrame2();
        add(frame2);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (bufferedImage == null){
            try {
                bufferedImage = ImageIO.read(getClass().getResource("/background.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        g.drawImage(bufferedImage,0,0,1600,1000,null);
        System.out.println("draw");
    }
}