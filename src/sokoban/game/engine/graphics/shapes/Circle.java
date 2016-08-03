package sokoban.game.engine.graphics.shapes;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-2-0002.
 */
public class Circle extends Oval {

    public Circle(Point center, int radius) {
        super(center, radius, radius);
    }
}
