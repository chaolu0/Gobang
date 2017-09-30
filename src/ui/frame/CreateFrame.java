package ui.frame;

import bean.Message;
import network.directional.INet;
import network.directional.server.ServiceThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CreateFrame extends JFrame implements ActionListener {

//    public static void main(String[] args) {
//        CreateFrame frame = new CreateFrame();
//    }

    protected JTextField ipText;
    protected JTextField portText;
    protected JTextField nameText;

    protected JLabel ipLabel;
    protected JLabel portLabel;
    protected JLabel nameLabel;
    protected JButton createButton;
    protected JButton backButton;

    protected boolean running = false;
    protected JRadioButton firstButton;//先手按钮
    protected JRadioButton lastButton;//后手按钮

    protected JFrame parent;

    CreateFrame(JFrame frame) {
        super("欢迎");
        this.parent = frame;
        this.setSize(500, 500);
        this.setLayout(null);
        this.setResizable(false);
        setLocationRelativeTo(null);
        // this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parent.setVisible(true);
            }
        });

        initIpText();
        initPortText();
        initNameText();
        initFirstText();
        initCreateButton();
        initBackButton();
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

    protected void initCreateButton() {

        createButton = new JButton();
        createButton.setText("创建");
        createButton.setSize(150, 40);
        createButton.setFont(new java.awt.Font("Dialog", 1, 15));
        createButton.setBounds(50, 330, 150, 40);
        createButton.setVisible(true);
        createButton.addActionListener(this);
        this.add(createButton);
    }

    protected void initFirstText() {
        firstButton = new JRadioButton("first");
        firstButton.setFont(new java.awt.Font("Dialog", 1, 17));
        firstButton.setSize(60, 60);
        firstButton.setBounds(50, 260, 100, 60);
        lastButton = new JRadioButton("last");
        lastButton.setFont(new java.awt.Font("Dialog", 1, 17));
        lastButton.setSize(60, 60);
        lastButton.setBounds(180, 260, 100, 60);
        ButtonGroup group = new ButtonGroup();
        group.add(firstButton);
        group.add(lastButton);
        firstButton.getModel().setSelected(true);

        this.add(firstButton);
        this.add(lastButton);
    }

    protected void initIpText() {
        ipText = new JTextField();
        ipText.setSize(150, 40);
        ipText.setFont(new java.awt.Font("Dialog", 1, 15));
        ipText.setBounds(150, 50, 150, 40);
        ipText.setVisible(true);
        ipText.setFocusable(true);
        ipText.setEditable(false);
        try {
            ipText.setText("" + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
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
        if (running) {
            return;
        }
        System.out.println("click");
        String ip = ipText.getText();
        String port = portText.getText();
        String name = nameText.getText();
        boolean first = firstButton.getModel().isSelected() ? true : false;
        System.out.println(ip + "    " + port + "    " + first);
        if (null == ip || null == port || "".equals(ip) || "".equals(port)) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "ip or port is null", "ip or port is null", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (gameFrame != null) {
            gameFrame.dispose();
            gameFrame = null;
        }
        try {
            Message message = new Message(null,Message.CMD_FIRST_SEND_TO_CLIENT,ip,name,first);
            INet iNet = new ServiceThread(message);
            iNet.setNetAndConnect(ip, Integer.parseInt(port));
            gameFrame = new GameFrame(CreateFrame.this, ip, name, port, first, iNet,true);
            iNet.begin();
            setVisible(false);
            gameFrame.setVisible(true);
        } catch (IOException e1) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "ip or port is wrong", "ip or port is wrong", JOptionPane.ERROR_MESSAGE);
            e1.printStackTrace();
        }

    }


}
