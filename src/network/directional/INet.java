package network.directional;

import java.io.IOException;

public interface INet{
    void sendMessage(String s);
    void begin();
    void end();
    void setNetAndConnect(String ip,int port) throws IOException;
    void setGetMessageListener(GetMessageListener l);
    void setOtherCloseListener(OtherCloseListener l);
}
