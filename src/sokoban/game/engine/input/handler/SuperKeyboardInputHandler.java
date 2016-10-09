package sokoban.game.engine.input.handler;

import sokoban.game.engine.input.KeyboardInput;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * 键盘处理器的常用实现
 */
public class SuperKeyboardInputHandler extends KeyboardInputHandler {
    protected Map<Integer, Enterable> enterables = new HashMap<>();

    public SuperKeyboardInputHandler(KeyboardInput input) {
        super(input);
    }

    public void add(int keyCode, Enterable enterable) {
        enterables.put(keyCode, enterable);
    }

    public void remove(KeyEvent keyEvent) {
        enterables.remove(keyEvent);
    }

    @Override
    public void processInput() {
        for (Map.Entry<Integer, Enterable> entry : enterables.entrySet()) {
            if (getInput().isKeyDownOnce(entry.getKey())) {
                entry.getValue().onEnter();
            }
        }
    }
}
