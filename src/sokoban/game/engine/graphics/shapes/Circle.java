package sokoban.game.engine.graphics.shapes;

import java.awt.*;

/**
 * 圆形图形类
 */
public class Circle extends Oval {

    public Circle(Point center, int radius) {
        super(center, radius, radius);
    }
}
