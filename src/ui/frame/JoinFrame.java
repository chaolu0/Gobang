package ui.frame;

import network.directional.INet;
import network.directional.client.ClientThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class JoinFrame extends JFrame implements ActionListener {
//    public static void main(String[] args) {
//        JoinFrame frame = new JoinFrame();
//    }

    protected JTextField ipText;
    protected JTextField portText;
    protected JTextField nameText;

    protected JLabel ipLabel;
    protected JLabel portLabel;
    protected JLabel nameLabel;

    protected JButton joinButton;
    protected JButton backButton;

    protected boolean isConnecting = false;
    protected JFrame parent;
    JoinFrame(JFrame frame) {
        super("加入");
        parent = frame;
        this.setSize(500, 500);
        this.setLayout(null);
        this.setResizable(false);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parent.setVisible(true);
            }
        });

        initIpText();
        initPortText();
        initNameText();
        initJoinButton();
        initBackButton();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        invalidate();
        this.setVisible(true);
    }

    protected void initBackButton() {
        backButton = new JButton();
        backButton.setText("返回");
        backButton.setSize(150, 40);
        backButton.setFont(new java.awt.Font("Dialog", 1, 15));
        backButton.setBounds(300, 330, 150, 40);
        backButton.setVisible(true);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                parent.setVisible(true);
            }
        });
        this.add(backButton);
    }

    protected void initJoinButton() {
        joinButton = new JButton();
        joinButton.setSize(150, 40);
        joinButton.setFont(new java.awt.Font("Dialog", 1, 15));
        joinButton.setBounds(50, 330, 150, 40);
        joinButton.setVisible(true);
        joinButton.setFocusable(true);
        joinButton.setText("join");
        joinButton.addActionListener(this);
        this.add(joinButton);
    }

    protected void initIpText() {
        ipText = new JTextField();
        ipText.setSize(150, 40);
        ipText.setFont(new java.awt.Font("Dialog", 1, 15));
        ipText.setBounds(150, 50, 150, 40);
        ipText.setVisible(true);
        ipText.setFocusable(true);
        this.add(ipText);

        ipLabel = new JLabel();
        ipLabel.setSize(50, 40);
        ipLabel.setFont(new java.awt.Font("Dialog", 1, 15));
        ipLabel.setBounds(50, 50, 50, 40);
        ipLabel.setVisible(true);
        ipLabel.setFocusable(true);
        ipLabel.setText("IP:");
        this.add(ipLabel);
    }

    protected void initPortText() {
        portText = new JTextField();
        portText.setSize(150, 40);
        portText.setFont(new java.awt.Font("Dialog", 1, 15));
        portText.setBounds(150, 120, 150, 40);
        portText.setVisible(true);
        portText.setFocusable(true);
        this.add(portText);

        portLabel = new JLabel();
        portLabel.setSize(50, 40);
        portLabel.setFont(new java.awt.Font("Dialog", 1, 15));
        portLabel.setBounds(50, 120, 50, 40);
        portLabel.setVisible(true);
        portLabel.setFocusable(true);
        portLabel.setText("port:");
        this.add(portLabel);

    }

    protected void initNameText() {
        nameText = new JTextField();
        nameText.setSize(150, 40);
        nameText.setFont(new java.awt.Font("Dialog", 1, 15));
        nameText.setBounds(150, 190, 150, 40);
        nameText.setVisible(true);
        nameText.setFocusable(true);
        this.add(nameText);

        nameLabel = new JLabel();
        nameLabel.setSize(50, 40);
        nameLabel.setFont(new java.awt.Font("Dialog", 1, 15));
        nameLabel.setBounds(50, 190, 50, 40);
        nameLabel.setVisible(true);
        nameLabel.setFocusable(true);
        nameLabel.setText("name:");
        this.add(nameLabel);
    }

    protected GameFrame gameFrame;
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isConnecting){
            return;
        }
        System.out.println("click");
        String ip = ipText.getText();
        String port = portText.getText();
        String name = nameText.getText();
        System.out.println(ip + "    " + port);
        if (null == ip || null == port || "".equals(ip) || "".equals(port)) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "ip or port is null", "ip or port is null",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (gameFrame != null) {
            gameFrame.dispose();
            gameFrame = null;
        }
        try {
            INet iNet = new ClientThread();
            iNet.setNetAndConnect(ip, Integer.parseInt(port));
            gameFrame = new GameFrame(JoinFrame.this, ip, name,port, false,iNet,false);
            iNet.begin();
            setVisible(false);
            gameFrame.setVisible(true);
        } catch (IOException e1) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "ip or port is wrong", "ip or port is wrong", JOptionPane.ERROR_MESSAGE);
            //e1.printStackTrace();
        }

    }
}
