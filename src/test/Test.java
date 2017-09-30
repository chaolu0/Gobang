package test;

import network.undirectional.HttpService;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws IOException {
        Map<String,String> map = new HashMap<>();
        map.put("ip", InetAddress.getLocalHost().getHostAddress());
//        System.out.println(HttpService.get("find",map));

    }




}
