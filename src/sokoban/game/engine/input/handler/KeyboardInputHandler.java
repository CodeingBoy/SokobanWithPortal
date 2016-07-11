package sokoban.game.engine.input.handler;

import sokoban.game.engine.input.KeyboardInput;

/**
 * Created by CodeingBoy on 2016-7-11-0011.
 */
public abstract class KeyboardInputHandler implements InputHandler {
    protected KeyboardInput input;

    public KeyboardInputHandler(KeyboardInput input) {
        this.input = input;
    }

    @Override
    public final void poll() {
        input.poll();
    }
}
