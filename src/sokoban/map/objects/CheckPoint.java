package sokoban.map.objects;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-19-0019.
 */
public class CheckPoint extends MapObject implements Moveable {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/checkpoint.png");

    public CheckPoint(Point pos) {
        super(pos, PIC);
    }
}
