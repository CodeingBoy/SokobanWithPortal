package sokoban.game.engine.input.handler;

import sokoban.game.engine.graphics.shapes.Shape;

/**
 * Created by CodeingBoy on 2016-8-3-0003.
 */
public class MouseAction {
    public Shape shape;
    public Clickable clickable;

    public MouseAction(Shape shape, Clickable clickable) {
        this.shape = shape;
        this.clickable = clickable;
    }
}
