package sokoban.game.engine.input.handler;

import sokoban.game.engine.graphics.shapes.Shape;
import sokoban.game.engine.input.MouseInput;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 鼠标输入器的常用实现
 */
public class SuperMouseInputHandler extends MouseInputHandler {
    protected Map<String, Clickable> clickables = new HashMap<>();
    private ArrayList<Clickable> lastHovering = new ArrayList<>();
    private Clickable lastClicking = null;

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

        Iterator<Clickable> iterator = lastHovering.iterator();
        while (iterator.hasNext()) {
            Clickable clickable = iterator.next();
            if (!clickable.isPointInside(curPoint)) {
                clickable.onExitingHover(curPoint);
                iterator.remove();
            }
        }

        for (Clickable clickable : clickables.values()) {
            if (clickable.isPointInside(curPoint)) {
                if (!lastHovering.contains(clickable)) {
                    clickable.onEnteringHover(curPoint);
                    lastHovering.add(clickable);
                }

                clickable.onHover(curPoint);
                if (input.isButtonDownOnce(MouseEvent.BUTTON1))
                    clickable.onClick(curPoint);
            }
        }

    }
}
