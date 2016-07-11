package sokoban.game.engine.input.handler;

import sokoban.game.engine.input.MouseInput;

/**
 * Created by CodeingBoy on 2016-7-11-0011.
 */
public abstract class MouseInputHandler implements InputHandler {
    protected MouseInput input;

    public MouseInputHandler(MouseInput input) {
        this.input = input;
    }

    @Override
    public final void poll() {
        input.poll();
    }
}
