package sokoban.map.objects;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class Wall extends MapObject {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/wall.png");

    public Wall(Point pos) {
        super(pos, PIC);
    }

}
