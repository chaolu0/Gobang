package ui.frame;

import bean.Message;
import com.google.gson.Gson;
import network.directional.GetMessageListener;
import network.directional.INet;
import network.directional.OtherCloseListener;
import ui.PVPBoardPanel;
import ui.view.MyDialog;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 画板容器
 */
public class GameFrame extends JFrame implements GetMessageListener , OtherCloseListener{

    private static final int TEXTVIEW_EIDTH = 200;
    private boolean service;//是否服务器端
    private PVPBoardPanel boardPanel;//棋盘画布
    private JFrame parent;//传递来的Frame
    private JButton readyButton;//准备按钮
    //private JButton restartButton;//重开按钮
    private boolean isReady = false;
    private boolean clientReady = false;

    private JLabel bTitle;
    private JLabel bIp;
    private JLabel bName;
    private JLabel wTitle;
    private JLabel wIp;
    private JLabel wName;
    private JLabel whoRun;

    private INet mNet; //通信类
    private boolean meBlack;//我是否执黑棋

    private String ip, port, name;
    private Message sendPacket;

    public static void main(String[] args) {
        // GameFrame frame = new GameFrame(null);
    }

    public GameFrame(JFrame frame, String ip, String name, String port, boolean first, INet iNet, boolean service) throws IOException {
        super("Game");
        this.service = service;
        this.mNet = iNet;
        iNet.setOtherCloseListener(this);
        iNet.setGetMessageListener(this);
        this.setSize(1600, 1000);
        this.setLayout(null);
        boardPanel = new PVPBoardPanel();
        boardPanel.setLayout(new BorderLayout());
        boardPanel.setSize(800, 800);

        this.add(boardPanel);
        this.setResizable(false);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sendPacket = new Message(null,Message.CMD_EXIT,ip,name,false);
                mNet.sendMessage(new Gson().toJson(sendPacket));
                parent.setVisible(true);
                mNet.end();
                setVisible(false);

            }
        });
        initButton();
        initUsers();
        this.setVisible(true);
        boardPanel.setGraphics((Graphics2D) boardPanel.getGraphics());
        init();
        resoveC(frame, ip, name, port, first);
    }

    private void resoveC(JFrame frame, String ip, String name, String port, boolean first) throws IOException {
        meBlack = first;
        this.ip = ip;
        this.port = port;
        this.name = name;
        parent = frame;
        if (service)
        if (first) {
            bTitle.setText("黑方   我");
           setBlackInfo(ip,name);
        } else {
            wTitle.setText("白方   我");
            setWhiteInfo(ip,name);
        }
    }


    private final int C_SIZE = 18;//字号

    private void initUsers() {
        bTitle = new JLabel();
        bTitle.setText("黑方");
        bTitle.setSize(TEXTVIEW_EIDTH, 40);
        bTitle.setFont(new java.awt.Font("Dialog", 1, C_SIZE));
        bTitle.setBounds(850, 100, 150, 40);
        //bTitle.setVisible(true);
        this.add(bTitle);

        bIp = new JLabel();
        bIp.setText("ip = ");
        //bIp.setSize(TEXTVIEW_EIDTH, 40);
        bIp.setFont(new java.awt.Font("Dialog", 1, C_SIZE));
        bIp.setBounds(850, 150, TEXTVIEW_EIDTH, 40);
        //bIp.setVisible(true);
        this.add(bIp);

        bName = new JLabel();
        bName.setText("name= ");
        //bName.setSize(TEXTVIEW_EIDTH, 40);
        bName.setFont(new java.awt.Font("Dialog", 1, C_SIZE));
        bName.setBounds(850, 200, TEXTVIEW_EIDTH, 40);
        // bName.setVisible(true);
        this.add(bName);

        wTitle = new JLabel();
        wTitle.setText("白方");
       // wTitle.setSize(TEXTVIEW_EIDTH, 40);
        wTitle.setFont(new java.awt.Font("Dialog", 1, C_SIZE));
        wTitle.setBounds(850, 350, TEXTVIEW_EIDTH, 40);
        //wTitle.setVisible(true);
        this.add(wTitle);

        wIp = new JLabel();
        wIp.setText("ip = ");
        //wIp.setSize(TEXTVIEW_EIDTH, 40);
        wIp.setFont(new java.awt.Font("Dialog", 1, C_SIZE));
        wIp.setBounds(850, 400, TEXTVIEW_EIDTH, 40);
        //wIp.setVisible(true);
        this.add(wIp);

        wName = new JLabel();
        wName.setText("name = ");
        //wName.setSize(TEXTVIEW_EIDTH, 40);
        wName.setFont(new java.awt.Font("Dialog", 1, C_SIZE));
        wName.setBounds(850, 450, TEXTVIEW_EIDTH, 40);
        // wName.setVisible(true);
        this.add(wName);

        whoRun = new JLabel();
        whoRun.setText("当前白方行棋");
       // whoRun.setSize(TEXTVIEW_EIDTH, 40);
        whoRun.setFont(new java.awt.Font("Dialog", 1, C_SIZE));
        whoRun.setBounds(850, 700, TEXTVIEW_EIDTH, 40);
        // wName.setVisible(true);
        //this.add(whoRun);
    }

    private void initButton() {
        readyButton = new JButton();
        readyButton.setText("准备");
        readyButton.setSize(150, 40);
        readyButton.setFont(new java.awt.Font("Dialog", 1, 15));
        readyButton.setBounds(300, 800, 150, 40);
        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isReady)
                    return;
                isReady = true;
                boardPanel.clear();
                if (service) {
                    if (clientReady) {
                        //可以开始游戏了~
                        sendPacket = new Message(null, Message.CMD_BEGIN, ip, name, isReady);
                        mNet.sendMessage(new Gson().toJson(sendPacket));
                        boardPanel.begin();
                    }
                } else {
                    sendPacket = new Message(null, Message.CMD_REDAY, ip, name, isReady);
                    mNet.sendMessage(new Gson().toJson(sendPacket));
                }
            }
        });
        this.add(readyButton);

    }


    public void init() {
        boardPanel.drawBoard();
        boardPanel.addMouseListener(boardPanel);
        boardPanel.setOnSuccessListener(new PVPBoardPanel.OnSuccessListener() {
            @Override
            public void win(boolean isBlack) {
                if (isBlack) {
                    System.out.println("black win");
                    new MyDialog("black is winner");
                } else {
                    System.out.println("white win");
                    new MyDialog("white is winner");
                }
                clientReady = false;
                isReady = false;
                boardPanel.myFirst(meBlack);
            }
        });
        boardPanel.setOnDrawPieceListener(new PVPBoardPanel.OnDrawPieceListener() {
            @Override
            public void point(int x, int y) {
                sendPacket = new Message(new Point(x, y), Message.CMD_STEP, "", "", false);
                mNet.sendMessage(new Gson().toJson(sendPacket));
            }
        });
    }
    private BufferedImage back = null;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
//        if(back == null){
//            try {
//                back = ImageIO.read(getClass().getResource("/background.jpg"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        g.drawImage(back,0,0,1600,1000,null);
        boardPanel.drawBoard();
        boardPanel.drawAllPiece();
    }


    @Override
    public void getMsg(Message message) {
        String cmd = message.getCmd();
        switch (cmd) {
            case Message.CMD_FIRST_SEND_TO_CLIENT:
                sendPacket = new Message(null, Message.CMD_FIRST_SEND_TO_SERVER, ip, name, !message.isBlack());
                mNet.sendMessage(new Gson().toJson(sendPacket));
                //客户端将收到这个请求
                //message 是服务器信息
                meBlack = !message.isBlack();
                //如果服务器是黑棋
                if (message.isBlack()) {
                    wTitle.setText("白方   我");
                    setBlackInfo(message.getIp(),message.getName());
                    setWhiteInfo(ip,name);
                } else {
                    bTitle.setText("黑方   我");
                    setBlackInfo(ip,name);
                    setWhiteInfo(message.getIp(),message.getName());
                }
                boardPanel.myFirst(!message.isBlack());
                break;
            case Message.CMD_FIRST_SEND_TO_SERVER:
                //服务器将收到这个请求
                //如果服务器是白棋
                //message 是客户端信息
                if (!meBlack) {
                    setBlackInfo(message.getIp(),message.getName());
                    setWhiteInfo(ip,name);
                } else {
                    setBlackInfo(ip,name);
                    setWhiteInfo(message.getIp(),message.getName());
                }
                boardPanel.myFirst(meBlack);
                break;
            case Message.CMD_REDAY:
                clientReady = true;
                if (service) {
                    if (isReady) {
                        //可以开始游戏了~
                        sendPacket = new Message(null, Message.CMD_BEGIN, ip, name, meBlack);
                        mNet.sendMessage(new Gson().toJson(sendPacket));
                        boardPanel.begin();
                    }
                }
                break;
            case Message.CMD_STEP:
                //客户端，服务端均可收到该消息
                boardPanel.drawIndexPiece(message.getPoint());
                changeNow();
                break;
            case Message.CMD_EXIT:
                //当对方正常退出，会发出此消息
                MyDialog dialog =  new MyDialog("对方已经断开连接");
                dialog.setOnClickListener(new MyDialog.Listener() {
                    @Override
                    public void click() {
                        mNet.end();
                        GameFrame.this.setVisible(false);
                        parent.setVisible(true);
                    }
                });
                break;
            case Message.CMD_BEGIN:
                //只有客户端可以收到该消息
                boardPanel.begin();
                break;
        }
    }

    private void setBlackInfo(String ip,String name){
        bIp.setText("ip:   " + ip);
        bName.setText("name:   "+name);
    }
    private void setWhiteInfo(String ip,String name){
        wIp.setText("ip:   " + ip);
        wName.setText("name:   "+name);
    }
    private void changeNow(){
//        if (boardPanel.getTurn()&&meBlack || !meBlack && !boardPanel.getTurn()){
//            whoRun.setText("当前黑方行棋");
//        }else{
//            whoRun.setText("当前白方行棋");
//        }
    }

    @Override
    public void close() {
        System.out.println("call");
       MyDialog dialog =  new MyDialog("对方已经断开连接");
       dialog.setOnClickListener(new MyDialog.Listener() {
           @Override
           public void click() {
               mNet.end();
               GameFrame.this.setVisible(false);
               parent.setVisible(true);
           }
       });

    }
}
