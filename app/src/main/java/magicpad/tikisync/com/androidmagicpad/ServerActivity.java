package magicpad.tikisync.com.androidmagicpad;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import org.apache.http.conn.util.InetAddressUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Admin on 8/5/2015.
 */
public class ServerActivity extends Activity {

    private TextView serverStatus;
    private View magicPad;

    Socket client;
    // default ip
    public static String SERVERIP = "10.0.2.15";

    // designate a port
    public static final int SERVERPORT = 8080;

    private Handler handler = new Handler();

    private ServerSocket serverSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server);
        init();
    }

    public void init() {
        serverStatus = (TextView) findViewById(R.id.serverstatus);
        magicPad = (View) findViewById(R.id.magicpad);
        magicPad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int pointerindex = event.getActionIndex();
                int pointerid = event.getPointerId(pointerindex);
                int maskedAction = event.getActionMasked();

                switch (maskedAction) {
                    case MotionEvent.ACTION_POINTER_UP:
                        sendCommand("r_click");

                        break;
                    case MotionEvent.ACTION_UP:
                        sendCommand("l_click");
                        break;
                }
                return true;

            }

        });
        SERVERIP = getLocalIpAddress();

        Thread fst = new Thread(new ServerThread());
        fst.start();
        Log.d("hello","dd");
    }

    public void sendCommand(String command) {
        if (client.isConnected() == true) {
            try {

                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client
                        .getOutputStream())), true);
                // where you issue the commands
                out.println(command);

            } catch (Exception e) {

            }
        }
    }

    public class ServerThread implements Runnable {

        public void run() {
            try {
                if (SERVERIP != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            serverStatus.setText("Listening on IP: " + SERVERIP);
                        }
                    });
                    serverSocket = new ServerSocket(SERVERPORT);
                    while (true) {
                        // listen for incoming clients
                        client = serverSocket.accept();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                serverStatus.setText("Connected.");
                            }
                        });


                    }
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            serverStatus.setText("Couldn't detect internet connection.");
                        }
                    });
                }
            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        serverStatus.setText("Error");
                    }
                });
                e.printStackTrace();
            }
        }
    }

    // gets the ip address of your phone's network
    private String getLocalIpAddress() {
        String ipv4;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipv4 = inetAddress.getHostAddress())) {
                        return ipv4;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("ServerActivity", ex.toString());
        }
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            // make sure you close the socket upon exiting
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}