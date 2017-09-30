package test;

import javax.swing.*;

public class MFrame2 extends JPanel {
    public MFrame2(){
        setLayout(null);
        setSize(800,800);
        JButton button = new JButton();
        button.setSize(50,50);
        this.add(button);
        setVisible(true);
    }
}