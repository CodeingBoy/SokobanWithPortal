package sokoban.scenes.mainmenu.inputhandler;

import sokoban.game.engine.input.MouseInput;
import sokoban.game.engine.input.handler.Clickable;
import sokoban.game.engine.input.handler.MouseInputHandler;

import java.awt.*;
import java.util.Map;

/**
 * Created by CodeingBoy on 2016-8-2-0002.
 */
public class MouseHandler extends MouseInputHandler {
    private Map<String, Clickable> clickables;

    public MouseHandler(MouseInput input) {
        super(input);
    }

    @Override
    public void processInput() {
        Point curPoint = input.getPosition();
    }
}
