package sokoban.game.engine.input.handler;

import sokoban.game.engine.input.KeyboardInput;

public abstract class KeyboardInputHandler implements InputHandler {
    protected KeyboardInput input;

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
}
