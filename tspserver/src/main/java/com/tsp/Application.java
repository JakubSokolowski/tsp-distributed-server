package com.tsp;


import com.tsp.socket.ClusterServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ClusterServer cs = new ClusterServer(42069);
        SpringApplication.run(Application.class, args);
        new Thread(cs).start();
        try {
            Thread.sleep(1000 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping Server");
        cs.stop();

    }
}