package network.directional.server;

import bean.Message;
import com.google.gson.Gson;
import network.directional.GetMessageListener;
import network.directional.INet;
import network.directional.OtherCloseListener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public final class ServiceThread extends Thread implements INet {

    private GetMessageListener listener;//得到信息回调
    private ServerSocket serverSocket;
    private Socket mSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    private Message firstPacket;//第一个包
    private OtherCloseListener olistener;//对方关闭回调
    @Override
    public void setOtherCloseListener(OtherCloseListener l){
        olistener = l;
    }

    public ServiceThread(Message message) {
        firstPacket = message;
    }

    private boolean go = false;

    @Override
    public void run() {
        System.out.println("run");
        try {
            mSocket = serverSocket.accept();
            System.out.println(new Gson().toJson(firstPacket));
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())));
            reader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            writer.println(new Gson().toJson(firstPacket));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (go) {
            try {
                String msgString = reader.readLine();
                Message msg = new Gson().fromJson(msgString, Message.class);
                System.out.println("server get message " + msgString);
                listener.getMsg(msg);
            } catch (IOException e) {
                System.out.println("error  " );
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
        System.out.println("server send message " + string);
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
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setNetAndConnect(String ip, int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void setGetMessageListener(GetMessageListener l) {
        listener = l;
    }


}
