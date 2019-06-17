package com.example.joystickapplication;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient implements Serializable {

    private PrintWriter writer;
    private Socket socket;
    private boolean isConnected;
    private String ip;
    private int port;

    public TcpClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    //final String ServerIp, final int port
    public void Connect() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress serverAddr = InetAddress.getByName(ip);
                    // creating  new connection.
                    socket = new Socket(serverAddr, port);
                    try {
                        isConnected = true;
                        writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    } catch (Exception e) {
                        System.out.println("writer creation Failed");
                        socket.close();
                    }
                } catch (Exception e) {
                    System.out.println("connection Failed");
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }

    public void SendMessege(final String msg) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (writer != null && isConnected) {
                    writer.println(msg);
                    writer.flush();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void CloseClient() {
        isConnected = false;
        if (writer != null) {
            writer.flush();
            writer.close();
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println("cannot close socket");
            }
        }
    }
}
