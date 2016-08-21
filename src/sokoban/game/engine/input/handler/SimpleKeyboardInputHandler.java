package sokoban.game.engine.input.handler;

import sokoban.game.engine.input.KeyboardInput;
import sokoban.utils.Log;

import java.awt.event.KeyEvent;

/**
 * Created by CodeingBoy on 2016-7-11-0011.
 */
public class SimpleKeyboardInputHandler extends SuperKeyboardInputHandler {

    public SimpleKeyboardInputHandler(KeyboardInput input) {
        super(input);
    }

    public void processInput() {
        if (getInput().isKeyDownOnce(KeyEvent.VK_SPACE))
            Log.i("Space down");
    }

}
