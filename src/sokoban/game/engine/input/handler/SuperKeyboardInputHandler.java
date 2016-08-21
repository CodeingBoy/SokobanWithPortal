package sokoban.game.engine.input.handler;

import sokoban.game.engine.input.KeyboardInput;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeingBoy on 2016-7-11-0011.
 */
public class SuperKeyboardInputHandler extends KeyboardInputHandler {
    protected Map<KeyEvent, Enterable> enterables = new HashMap<>();

    public SuperKeyboardInputHandler(KeyboardInput input) {
        super(input);
    }

    public void add(KeyEvent keyEvent, Enterable enterable) {
        enterables.put(keyEvent, enterable);
    }

    public void remove(KeyEvent keyEvent) {
        enterables.remove(keyEvent);
    }

    @Override
    public void processInput() {
        for (Map.Entry<KeyEvent, Enterable> entry : enterables.entrySet()) {
            if (getInput().isKeyDownOnce(entry.getKey().getKeyCode())) {
                entry.getValue().onEnter();
            }
        }
    }
}
