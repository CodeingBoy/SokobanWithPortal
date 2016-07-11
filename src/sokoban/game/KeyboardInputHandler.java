package sokoban.game;

import sokoban.game.engine.input.KeyboardInput;
import sokoban.utils.Log;

import java.awt.event.KeyEvent;

/**
 * Created by CodeingBoy on 2016-7-11-0011.
 */
public class KeyboardInputHandler {
    private KeyboardInput input;

    public KeyboardInputHandler(KeyboardInput input) {
        this.input = input;
    }

    public void processInput(){
        input.poll();

        if (input.isKeyDownOnce(KeyEvent.VK_SPACE))
            Log.i("Space down");
    }

}
