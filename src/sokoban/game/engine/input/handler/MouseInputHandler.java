package sokoban.game.engine.input.handler;

import sokoban.game.engine.graphics.shapes.Shape;
import sokoban.game.engine.input.MouseInput;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeingBoy on 2016-7-11-0011.
 */
public abstract class MouseInputHandler implements InputHandler {
    protected MouseInput input;
    protected Map<String, MouseAction> clickables = new HashMap<>();

    public MouseInputHandler(MouseInput input) {
        this.input = input;
    }

    @Override
    public final void poll() {
        input.poll();
    }

    public MouseInput getInput() {
        return input;
    }

    public void add(String name,Shape shape, Clickable clickable) {
        clickables.put(name, new MouseAction(shape, clickable));
    }

    public void remove(String name) {
        clickables.remove(name);
    }
}

