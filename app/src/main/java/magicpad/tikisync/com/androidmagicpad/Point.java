package magicpad.tikisync.com.androidmagicpad;

import java.io.Serializable;

/**
 * Created by Admin on 8/6/2015.
 */
public class Point implements Serializable {
    String command;
    float x, y;

    public Point(String cmd,float x_pos, float y_pos) {
        this.command=cmd;
        this.x = x_pos;
        this.y = y_pos;
    }

}
