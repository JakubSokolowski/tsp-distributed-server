package com.tsp.socket;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private Socket clientSocket;
    private String serverText;

    ConnectionHandler(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }

    public void run() {
        try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            String str;

            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            PrintWriter pw = new PrintWriter(output, true);

            int counter = 0;
            while((str = br.readLine()) != null) {
                Thread.sleep(1000);
                counter++;
                str = "Server returns " + str + Integer.toString(counter);
                System.out.println(str);
                pw.println(str);
                System.out.println("Server send message...");
                long time = System.currentTimeMillis();
                System.out.println("Request processed: " + time);
            }
            System.out.println("Finished communication");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
