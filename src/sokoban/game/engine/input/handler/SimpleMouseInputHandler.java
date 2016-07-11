package sokoban.game.engine.input.handler;

import sokoban.game.engine.input.MouseInput;
import sokoban.utils.Log;

import java.awt.event.MouseEvent;

/**
 * Created by CodeingBoy on 2016-7-11-0011.
 */
public class SimpleMouseInputHandler extends MouseInputHandler {

    public SimpleMouseInputHandler(MouseInput input) {
        super(input);
    }

    @Override
    public void processInput() {
        if (input.isButtonDownOnce(MouseEvent.BUTTON1))
            Log.d("BTN1 CLICKED");
    }

}
