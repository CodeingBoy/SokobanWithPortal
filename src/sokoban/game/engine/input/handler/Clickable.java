package sokoban.game.engine.input.handler;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-3-0003.
 */
public interface Clickable {
    boolean isPointInside(Point point);

    void onClick(Point p);

    void onHover(Point p);
}
