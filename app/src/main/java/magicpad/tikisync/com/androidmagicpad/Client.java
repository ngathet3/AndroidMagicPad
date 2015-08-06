/*
package magicpad.tikisync.com.androidmagicpad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

*/
/**
 * Created by Admin on 7/30/2015.
 *//*

public class Client {

    Robot robot=null;
    private String serverIpAddress = "192.168.0.111";

    private boolean connected = false;


    public static void main(String[] args) throws AWTException {
        System.out.println("Client Join");
        Client c = new Client();
        c.robot=new Robot();
        if (!c.connected) {

            if (!c.serverIpAddress.equals("")) {

                Thread cThread = new Thread(new ClientThread(c));
                cThread.start();
            }
        }

    }

    public static class ClientThread implements Runnable {
        Client c;

        public ClientThread(Client temp) {
            c = temp;
        }

        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(c.serverIpAddress);
                Socket socket = new Socket(serverAddr, 8080);
                c.connected = true;
                while (c.connected) {
                    try {
                        */
/*BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String line = null;
                        while ((line = in.readLine()) != null) {

                            final String finalLine = line;
							switch(line)
							{
								case "l_click":
								leftClick(c);
								break;
								case "r_click":
								rightClick(c);
								break;
							}
							System.out.println(line);
                        }*//*


                        String command=null;
                        InputStream is = socket.getInputStream();
                        ObjectInputStream ois = new ObjectInputStream(is);



                        //command=((String)ois.readObject());
                        //System.out.println(command);
                        // Point to = (Point)ois.readObject();
                        //if (to!=null){System.out.println(to.x);}



                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                }
                //   socket.close();

            } catch (Exception e) {

                c.connected = false;
            }
        }
    }
    private static void leftClick(Client c)
    {
        c.robot.mousePress(InputEvent.BUTTON1_MASK);
        c.robot.delay(200);
        c.robot.mouseRelease(InputEvent.BUTTON1_MASK);
        c.robot.delay(200);
    }
    private static void rightClick(Client c)
    {
        c.robot.mousePress(InputEvent.BUTTON3_MASK);
        c.robot.delay(200);
        c.robot.mouseRelease(InputEvent.BUTTON3_MASK);
        c.robot.delay(200);
    }

}*/
