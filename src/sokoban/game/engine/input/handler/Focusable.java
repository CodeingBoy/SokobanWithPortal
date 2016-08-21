package sokoban.game.engine.input.handler;

import java.awt.*;

public interface Focusable {
    void onEnter(Point p);

    void onFocus(Point p);
}
