package magicpad.tikisync.com.androidmagicpad;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.conn.util.InetAddressUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;


public class ServerActivity extends Activity {

    float startX = 0.0f;
    float startY = 0.0f;
    String moveType_X=null;
    String moveType_Y=null;

    private TextView serverStatus;
    private Button btnRightClick;
    private Button btnLeftClick;
    private View magicPad;

    Socket client;
    // default ip
    public static String SERVERIP = "10.0.2.15";

    // designate a port
    public static final int SERVERPORT = 8080;

    private Handler handler = new Handler();
    // Server Socket
    private ServerSocket serverSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server);
        init();
    }

    public void init() {
        serverStatus = (TextView) findViewById(R.id.serverstatus);
        btnLeftClick = (Button) findViewById(R.id.btnLeftClick);
        btnLeftClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendObject(new CommandSet("l_click", 0.0f, 0.0f,null,null));
            }
        });
        btnRightClick = (Button) findViewById(R.id.btnRightClick);
        btnRightClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendObject(new CommandSet("r_click", 0.0f, 0.0f,null,null));

            }
        });
        magicPad = findViewById(R.id.magicpad);
        magicPad.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {




                int pointerindex = event.getActionIndex();
                int pointerid = event.getPointerId(pointerindex);
                int maskedAction = event.getActionMasked();

                switch (maskedAction) {
                    case MotionEvent.ACTION_POINTER_UP:

                        break;
                    case MotionEvent.ACTION_UP:

                      break;
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        Log.d("down",String.valueOf(event.getX()));
                       break;
                    case MotionEvent.ACTION_MOVE:

                        float diffX = getTotalMoveX(startX, event.getX());
                        float diffY = getTotalMoveY(startY, event.getY());
                        Log.d("different",String.valueOf(startX));
                        sendObject(new CommandSet("m_move", diffX, diffY,moveType_X,moveType_Y));
                      break;



                }
                return true;

            }

        });
        SERVERIP = getLocalIpAddress();

        Thread fst = new Thread(new ServerThread());
        fst.start();
        Log.d("hello", "ds");
    }

    public int getTotalMoveX(float startpoint_x, float movepoint_x) {
        int diff_X = 0;
        if (movepoint_x < startpoint_x) {
            diff_X = (Integer) (Math.round(startpoint_x - movepoint_x));
            moveType_X="TO_LEFT";
        } else if (movepoint_x > startpoint_x) {
            diff_X = (Integer) (Math.round(movepoint_x - startpoint_x));
            moveType_X="TO_RIGHT";
            Log.d("here",String.valueOf(diff_X));
        }
        return diff_X;
    }

    public int getTotalMoveY(float startpoint_y, float movepoint_y) {
        int diff_Y = 0;
        if (movepoint_y < startpoint_y) {
            diff_Y = (Integer) (Math.round(startpoint_y - movepoint_y));
            moveType_Y="TO_UP";
        } else if (movepoint_y > startpoint_y) {
            diff_Y = (Integer) (Math.round(movepoint_y - startpoint_y));
            moveType_Y="TO_DOWN";
        }
        return diff_Y;
    }

    public void sendObject(Object obj) {
        if (client.isConnected()) {
            OutputStream os = null;
            try {
                os = client.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);

                oos.writeObject(obj);

            } catch (IOException e) {
                e.printStackTrace();
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