package ui.frame;

import network.undirectional.HttpService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class WelcomeFrame extends JFrame{

    public static void main(String[] args){
        WelcomeFrame frame = new WelcomeFrame(null);
    }

    private JButton createButton;
    private JButton joinButton;
    private JButton twoButton;
    private JButton createNetButton;
    private JButton joinNetButton;
    private JButton pveButton;

    private JLabel label;
    private JFrame parent;
    public WelcomeFrame(JFrame frame){
        super("welcome");
        this.parent = frame;
        this.setSize(500, 500);
        this.setLayout(null);
        this.setResizable(false);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initLabel();
        initButton();

        this.setVisible(true);
    }

    private void initLabel() {
        label = new JLabel();
        label.setText("五子棋对战");
        label.setSize(150, 40);
        label.setFont(new java.awt.Font("Dialog", 1, 20));
        label.setBounds(190, 100, 150, 40);
        //label.setHorizontalAlignment(SwingConstants.CENTER);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        label.setVisible(true);
        this.add(label);
    }
    private JoinFrame joinFrame;
    private CreateFrame createFrame;
    private PVPFrame pvpFrame;
    private CreateNetFrame createNetFrame;
    private JoinNetFrame joinNetFrame;
    private PVEFrame pveFrame;
    private void initButton() {
        createNetButton = new JButton();
        createNetButton.setText("创建网络对局");
        createNetButton.setSize(150, 40);
        createNetButton.setFont(new java.awt.Font("Dialog", 1, 15));
        createNetButton.setBounds(50, 260, 150, 40);
        createNetButton.setVisible(true);
        createNetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (createNetFrame == null) {
                    createNetFrame = new CreateNetFrame(WelcomeFrame.this);
                }
                createNetFrame.setVisible(true);
                setVisible(false);
            }
        });
        this.add(createNetButton);

        joinNetButton = new JButton();
        joinNetButton.setText("加入网络对局");
        joinNetButton.setSize(150, 40);
        joinNetButton.setFont(new java.awt.Font("Dialog", 1, 15));
        joinNetButton.setBounds(300, 260, 150, 40);
        joinNetButton.setVisible(true);
        joinNetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (joinNetFrame == null) {
                    joinNetFrame = new JoinNetFrame(WelcomeFrame.this);
                }
                joinNetFrame.setVisible(true);
                setVisible(false);
            }
        });
        this.add(joinNetButton);

        createButton = new JButton();
        createButton.setText("创建联机对局");
        createButton.setSize(150, 40);
        createButton.setFont(new java.awt.Font("Dialog", 1, 15));
        createButton.setBounds(50, 330, 150, 40);
        createButton.setVisible(true);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //跳转，创建
                if (createFrame == null) {
                    createFrame = new CreateFrame(WelcomeFrame.this);
                }
                createFrame.setVisible(true);
                setVisible(false);
            }
        });
        this.add(createButton);

        joinButton = new JButton();
        joinButton.setText("加入联机对局");
        joinButton.setSize(150, 40);
        joinButton.setFont(new java.awt.Font("Dialog", 1, 15));
        joinButton.setBounds(300, 330, 150, 40);
        joinButton.setVisible(true);
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //跳转，加入
                if (joinFrame == null) {
                    joinFrame = new JoinFrame(WelcomeFrame.this);
                }
                joinFrame.setVisible(true);
                setVisible(false);
            }
        });
        this.add(joinButton);

        twoButton = new JButton();
        twoButton.setText("双人对战");
        twoButton.setSize(150, 40);
        twoButton.setFont(new java.awt.Font("Dialog", 1, 15));
        twoButton.setBounds(50, 400, 150, 40);
        twoButton.setVisible(true);
        twoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //跳转，创建
                pvpFrame = new PVPFrame(WelcomeFrame.this);
                pvpFrame.setVisible(true);
                setVisible(false);
            }});
        this.add(twoButton);

        pveButton = new JButton();
        pveButton.setText("人机对战");
        pveButton.setSize(150, 40);
        pveButton.setFont(new java.awt.Font("Dialog", 1, 15));
        pveButton.setBounds(300, 400, 150, 40);
        pveButton.setVisible(true);
        pveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //跳转，创建
                pveFrame = new PVEFrame(WelcomeFrame.this);
                pveFrame.setVisible(true);
                setVisible(false);
            }});
        this.add(pveButton);
    }

}

