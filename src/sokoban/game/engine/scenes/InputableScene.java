package sokoban.game.engine.scenes;

import sokoban.game.engine.input.handler.KeyboardInputHandler;
import sokoban.game.engine.input.handler.MouseInputHandler;

/**
 * Created by CodeingBoy on 2016-7-28-0028.
 */
public class InputableScene extends SuperSceneDecorator {
    private KeyboardInputHandler keyboardInputHandler;
    private MouseInputHandler mouseInputHandler;
    private SuperScene scene;

    public InputableScene(SuperScene scene, KeyboardInputHandler keyboardInputHandler, MouseInputHandler mouseInputHandler) {
        super(scene);
        this.scene = scene;
        this.keyboardInputHandler = keyboardInputHandler;
        this.mouseInputHandler = mouseInputHandler;
    }

    @Override
    public void onInitialize() {
        scene.onInitialize();
        canvas.addKeyListener(keyboardInputHandler.getInput());
        canvas.addMouseListener(mouseInputHandler.getInput());
        canvas.addMouseMotionListener(mouseInputHandler.getInput());
        canvas.addMouseWheelListener(mouseInputHandler.getInput());
    }

    @Override
    public void onRendering() {
        keyboardInputHandler.poll();
        keyboardInputHandler.processInput();
        mouseInputHandler.poll();
        mouseInputHandler.processInput();
        scene.onRendering();
    }
}
