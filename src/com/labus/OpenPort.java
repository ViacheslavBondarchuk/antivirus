package com.labus;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class OpenPort {
    public OpenPort(int port) {
        try {
            ServerSocket servsock = new ServerSocket(port);
            Socket sock = servsock.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
