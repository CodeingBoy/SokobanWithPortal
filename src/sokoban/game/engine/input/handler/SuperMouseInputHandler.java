package sokoban.game.engine.input.handler;

import sokoban.game.engine.graphics.shapes.Shape;
import sokoban.game.engine.input.MouseInput;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class SuperMouseInputHandler extends MouseInputHandler {
    protected Map<String, Clickable> clickables = new HashMap<>();

    public SuperMouseInputHandler(MouseInput input) {
        super(input);
    }

    @Deprecated
    public void add(String name, Shape shape, Clickable clickable) {
        clickables.put(name, clickable);
    }

    public void add(String name, Clickable clickable) {
        clickables.put(name, clickable);
    }

    public void remove(Clickable clickable) {
        clickables.remove(clickable);
    }

    @Override
    public void processInput() {
        Point curPoint = input.getPosition();
        for (Clickable clickable : clickables.values()) {
            if (clickable.isPointInside(curPoint)) {
                clickable.onHover(curPoint);
                if (input.isButtonDownOnce(MouseEvent.BUTTON1))
                    clickable.onClick(curPoint);
            }
        }
    }
}
