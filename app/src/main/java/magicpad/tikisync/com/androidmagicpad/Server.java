package magicpad.tikisync.com.androidmagicpad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Admin on 7/30/2015.
 */
public class Server {
    public static void main(String[] args) {
        System.out.println("Server Started");
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
            ServerSocket SRVSOCK = new ServerSocket(333);
            Socket SOCK = SRVSOCK.accept();
            InputStreamReader ir = new InputStreamReader(SOCK.getInputStream());
            BufferedReader bf = new BufferedReader(ir);

            String MESSAGE = bf.readLine();
            System.out.println(MESSAGE);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
