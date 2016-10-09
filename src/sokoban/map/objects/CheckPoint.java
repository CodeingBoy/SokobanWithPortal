package sokoban.map.objects;

import java.awt.*;

/**
 * 检查点类
 */
public class CheckPoint extends Floor implements Placeable {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/checkpoint.png");

    public CheckPoint(Point pos) {
        super(pos, PIC);
    }
}
