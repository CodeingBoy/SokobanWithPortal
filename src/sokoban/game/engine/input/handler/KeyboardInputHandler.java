package sokoban.game.engine.input.handler;

import sokoban.game.engine.input.KeyboardInput;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeingBoy on 2016-7-11-0011.
 */
public abstract class KeyboardInputHandler implements InputHandler {
    protected KeyboardInput input;
    protected Map<KeyEvent, Enterable> enterables = new HashMap<>();

    public KeyboardInputHandler(KeyboardInput input) {
        this.input = input;
    }

    @Override
    public final void poll() {
        input.poll();
    }

    public KeyboardInput getInput() {
        return input;
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
            if (input.isKeyDownOnce(entry.getKey().getKeyCode())) {
                entry.getValue().onEnter();
            }
        }
    }
}
