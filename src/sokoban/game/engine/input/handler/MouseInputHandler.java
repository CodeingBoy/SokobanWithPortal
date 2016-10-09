package sokoban.game.engine.input.handler;

import sokoban.game.engine.input.MouseInput;

/**
 * 鼠标输入处理器抽象类
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

    public MouseInput getInput() {
        return input;
    }

}

