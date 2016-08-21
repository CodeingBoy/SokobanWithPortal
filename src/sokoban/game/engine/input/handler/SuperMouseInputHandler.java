package sokoban.game.engine.input.handler;

import sokoban.game.engine.graphics.shapes.Shape;
import sokoban.game.engine.input.MouseInput;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class SuperMouseInputHandler extends MouseInputHandler {
    protected Map<String, MouseAction> clickables = new HashMap<>();

    public SuperMouseInputHandler(MouseInput input) {
        super(input);
    }

    public void add(String name, Shape shape, Clickable clickable) {
        clickables.put(name, new MouseAction(shape, clickable));
    }

    public void remove(String name) {
        clickables.remove(name);
    }

    @Override
    public void processInput() {
        Point curPoint = input.getPosition();
        for (MouseAction mouseAction : clickables.values()) {
            if (mouseAction.shape.isPointInside(curPoint)) {
                mouseAction.clickable.onHover(curPoint);
                if (input.isButtonDownOnce(MouseEvent.BUTTON1))
                    mouseAction.clickable.onClick(curPoint);
            }
        }
    }
}
