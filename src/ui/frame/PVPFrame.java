package ui.frame;

import ui.PBoardPanel;
import ui.view.MyDialog;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 画板容器
 */
public class PVPFrame extends JFrame{

    private PBoardPanel PBoardPanel;//棋盘画布
    private JFrame parent;//传递来的Frame
    private JButton readyButton;//准备按钮
    private boolean isReady = false;

    public PVPFrame(JFrame frame){
        super("Game");
        parent = frame;
        this.setSize(1600, 1000);
        this.setLayout(null);
        PBoardPanel = new PBoardPanel();
        PBoardPanel.setLayout(new BorderLayout());
        PBoardPanel.setSize(800, 800);
        this.add(PBoardPanel);
        this.setResizable(false);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initButton();
        this.setVisible(true);
        PBoardPanel.setGraphics((Graphics2D) PBoardPanel.getGraphics());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parent.setVisible(true);
                setVisible(false);
            }
        });
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
                PBoardPanel.clear();
                PBoardPanel.begin();
            }
        });
        this.add(readyButton);

    }


    public void init() {
        PBoardPanel.drawBoard();
        System.out.println("ddd");
        PBoardPanel.addMouseListener(PBoardPanel);
        PBoardPanel.setOnSuccessListener(new PBoardPanel.OnSuccessListener() {
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
    private BufferedImage back = null;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(back == null){
            try {
                back = ImageIO.read(getClass().getResource("/background.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        g.drawImage(back,0,0,1600,1000,null);
        PBoardPanel.drawBoard();
        PBoardPanel.drawAllPiece();
    }

}
