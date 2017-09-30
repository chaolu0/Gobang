package ui.frame;

import ui.PBoardPanel;
import ui.PVEBoardPanel;
import ui.view.MyDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PVEFrame extends JFrame{

    private PVEBoardPanel pveBoardPanel;//棋盘画布
    private JButton readyButton;//准备按钮
    private boolean isReady = false;
    private JFrame parent;
    public PVEFrame(JFrame f){
        super("PVE");
        parent = f;
        this.setSize(1600, 1000);
        this.setLayout(null);
        pveBoardPanel = new PVEBoardPanel();
        pveBoardPanel.setLayout(new BorderLayout());
        pveBoardPanel.setSize(800, 800);
        this.add(pveBoardPanel);
        this.setResizable(false);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initButton();
        this.setVisible(true);
        pveBoardPanel.setGraphics((Graphics2D) pveBoardPanel.getGraphics());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parent.setVisible(true);
                setVisible(false);
            }
        });
        pveBoardPanel.setUserColorBlack(true);
        init();
    }
    private void initButton() {
        readyButton = new JButton();
        readyButton.setText("开始");
        readyButton.setSize(150, 40);
        readyButton.setFont(new Font("Dialog", 1, 15));
        readyButton.setBounds(300, 800, 150, 40);
        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isReady)
                    return;
                pveBoardPanel.clear();
                pveBoardPanel.begin();
            }
        });
        this.add(readyButton);

    }


    public void init() {
        pveBoardPanel.drawBoard();
        System.out.println("ddd");
        pveBoardPanel.addMouseListener(pveBoardPanel);
        pveBoardPanel.setOnSuccessListener(new PBoardPanel.OnSuccessListener() {
            @Override
            public void win(boolean isBlack) {
                if (isBlack) {
                    System.out.println("black win");
                    new MyDialog("black is winner");
                } else {
                    System.out.println("white win");
                    new MyDialog("white is winner");
                }
                isReady = false;
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        pveBoardPanel.drawBoard();
        pveBoardPanel.drawAllPiece();
    }

}
