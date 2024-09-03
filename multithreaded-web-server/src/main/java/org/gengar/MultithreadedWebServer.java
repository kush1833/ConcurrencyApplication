package org.gengar;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MultithreadedWebServer {
    public String performClientTask() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("Task completed");
        return "Hello client!!";
    }

    public void startServer() throws IOException {
        ServerSocket server = new ServerSocket(9999);
        while (true) {
            System.out.println("Waiting for client request");
            try {
                Socket client = server.accept();
                OutputStream outputStream = client.getOutputStream();
                Runnable runnable = () -> {
                    try {
                        outputStream.write(("HTTP/1.1 200 OK\r\n\r\n" + performClientTask() + "\r\n").getBytes(StandardCharsets.UTF_8));
                    } catch (InterruptedException | IOException e) {
                        throw new RuntimeException(e);
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            } catch (Exception e) {
                System.out.println("Kuch to gadbad hai Daya!!");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        MultithreadedWebServer webServer = new MultithreadedWebServer();
        webServer.startServer();
    }
}