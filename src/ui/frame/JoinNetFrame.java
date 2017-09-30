package ui.frame;

import bean.HttpResBean;
import com.google.gson.Gson;
import network.directional.INet;
import network.directional.client.ClientThread;
import network.undirectional.HttpService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.HashMap;

public class JoinNetFrame extends JoinFrame{

    JoinNetFrame(JFrame frame) {
        super(frame);
    }
    @Override
    protected void initIpText(){

    }
    @Override
    protected void initPortText(){

    }
    @Override
    protected void initNameText(){

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isConnecting){
            return;
        }
        String res = null;
        try {
            res = HttpService.get("add","");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        HttpResBean bean = new Gson().fromJson(res,HttpResBean.class);
        if (bean.getState()==0){
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "当前没有房间", "提示",JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.println("click");
        String ip = bean.getUser().getIp();
        String port = bean.getUser().getPort();
        String name = bean.getUser().getName();
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
            gameFrame = new GameFrame(JoinNetFrame.this, ip, name,port, false,iNet,false);
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
