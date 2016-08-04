package sokoban.scenes.mainmenu.inputhandler;

import sokoban.game.engine.input.MouseInput;
import sokoban.game.engine.input.handler.MouseAction;
import sokoban.game.engine.input.handler.MouseInputHandler;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-2-0002.
 */
public class MouseHandler extends MouseInputHandler {

    public MouseHandler(MouseInput input) {
        super(input);
    }

    @Override
    public void processInput() {
        Point curPoint = input.getPosition();
        for (MouseAction mouseAction : clickables.values()) {
            if (mouseAction.shape.isPointInside(curPoint))
                mouseAction.clickable.onHover(curPoint);
        }
    }

}