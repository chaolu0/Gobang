package bean;

import java.awt.*;

public class Message {

    public static final String CMD_FIRST_SEND_TO_SERVER = "send_to_server";
    public static final String CMD_FIRST_SEND_TO_CLIENT = "send_to_client";
    public static final String CMD_REDAY = "ready";
    public static final String CMD_STEP = "step";
    public static final String CMD_EXIT = "exit";
    public static final String CMD_BEGIN = "begin";

    private Point point;
    private String cmd;
    private String ip;
    private String name;
    private boolean isBlack;

    public Message(){}
    public Message(Point point,String cmd,String ip,String name,boolean isBlack){
        this.point = point;
        this.cmd = cmd;
        this.ip = ip;
        this.name = name;
        this.isBlack = isBlack;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }
}
