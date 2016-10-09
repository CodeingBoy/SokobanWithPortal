package sokoban.map.objects;

import java.awt.*;

/**
 * 墙壁类
 */
public class Wall extends MapObject {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/wall.png");

    public Wall(Point pos) {
        super(pos, PIC);
    }

}
