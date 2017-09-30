package ui.frame;

import bean.Message;
import network.directional.INet;
import network.directional.server.ServiceThread;
import network.undirectional.HttpService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class CreateNetFrame extends CreateFrame implements ActionListener {


    public CreateNetFrame(JFrame frame) {
        super(frame);
    }



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
            String get = "?ip=" + ip + "&port=" + port + "&name=" + name;
            HttpService.get("find",get);
            Message message = new Message(null,Message.CMD_FIRST_SEND_TO_CLIENT,ip,name,first);
            INet iNet = new ServiceThread(message);
            iNet.setNetAndConnect(ip, Integer.parseInt(port));
            gameFrame = new GameFrame(CreateNetFrame.this, ip, name, port, first, iNet,true);
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
