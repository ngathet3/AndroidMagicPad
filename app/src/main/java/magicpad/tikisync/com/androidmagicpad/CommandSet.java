package magicpad.tikisync.com.androidmagicpad;

import java.io.Serializable;

/**
 * Created by Admin on 8/6/2015.
 */
public class CommandSet implements Serializable {
    String command;
    String movetype_x;
    String movetype_y;
    float x, y;

    public CommandSet(String cmd,float x_pos, float y_pos,String move_x,String move_y) {
        this.command=cmd;
        this.x = x_pos;
        this.y = y_pos;
        this.movetype_x=move_x;
        this.movetype_y=move_y;
    }

}
