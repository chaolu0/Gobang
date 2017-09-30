package network.directional.client;

import bean.Message;
import com.google.gson.Gson;
import network.directional.GetMessageListener;
import network.directional.INet;
import network.directional.OtherCloseListener;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread implements INet {

    private GetMessageListener listener;
    private Socket mSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    private boolean go = false;
    private String ip;
    private int port;
    private OtherCloseListener olistener;//对方关闭回调
    @Override
    public void setOtherCloseListener(OtherCloseListener l){
        olistener = l;
    }

    public ClientThread() {
    }

    @Override
    public void run() {
        while (go) {
            try {
                String msgString = reader.readLine();
                Message msg = new Gson().fromJson(msgString, Message.class);
                System.out.println("client get message " + msgString);
                listener.getMsg(msg);
            } catch (IOException e) {
                System.out.println("error");
                olistener.close();
                go = false;
                try {
                    mSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息
     *
     * @param string 消息内容
     */
    @Override
    public void sendMessage(String string) {
        if (mSocket == null) {
            return;
        }
        System.out.println("client send message " + string);
        writer.println(string);
        writer.flush();

    }

    @Override
    public void begin() {
        go = true;
        start();
    }

    @Override
    public void end() {
        go = false;
        this.interrupt();
        mSocket = null;
    }

    @Override
    public void setNetAndConnect(String ip, int port) throws IOException {
        mSocket = new Socket(ip, port);
        writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())));
        reader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
    }

    @Override
    public void setGetMessageListener(GetMessageListener l) {
        this.listener = l;
    }
}
