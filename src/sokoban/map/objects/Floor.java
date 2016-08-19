package sokoban.map.objects;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class Floor extends MapObject {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/floor.png");

    public Floor(Point pos) {
        super(pos, PIC);
    }
}
