package sokoban.game.engine.input.handler;

import sokoban.game.engine.graphics.shapes.Shape;
import sokoban.game.engine.input.MouseInput;
import sokoban.utils.Log;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeingBoy on 2016-7-11-0011.
 */
public class SimpleMouseInputHandler extends MouseInputHandler {

    protected Map<String, MouseAction> clickables = new HashMap<>();

    public SimpleMouseInputHandler(MouseInput input) {
        super(input);
    }

    @Override
    public void processInput() {
        if (input.isButtonDownOnce(MouseEvent.BUTTON1))
            Log.d("BTN1 CLICKED");
    }

    public void add(String name, Shape shape, Clickable clickable) {
        clickables.put(name, new MouseAction(shape, clickable));
    }

    public void remove(String name) {
        clickables.remove(name);
    }
}
