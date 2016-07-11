package sokoban.dialog;

import sokoban.game.engine.input.KeyboardInput;
import sokoban.game.engine.input.MouseInput;
import sokoban.game.engine.input.handler.SimpleKeyboardInputHandler;
import sokoban.game.engine.input.handler.SimpleMouseInputHandler;
import sokoban.game.utils.FrameRateCalculator;
import sokoban.utils.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

/**
 * Created by CodeingBoy on 2016-7-10-0010.
 */
public class GameDialog extends JFrame implements Runnable {
    private FrameRateCalculator frameRate = null;
    private BufferStrategy bufferStrategy;
    private Thread renderThread;
    private boolean rendering = false;
    private SimpleKeyboardInputHandler keyboardInputHandler;
    private SimpleMouseInputHandler mouseInputHandler;

    public GameDialog() throws HeadlessException {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setTitle("GameDialog");
        setIgnoreRepaint(true); // active rendering, need not passive repaint 主动渲染，无须被动渲染
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                onWindowClosing();
            }
        });
    }

    public static void main(String[] args) {
        GameDialog dialog = new GameDialog();
        FrameRateCalculator frameRate = new FrameRateCalculator();
        frameRate.setShouldLog(true);
        dialog.setFrameRate(frameRate);

        LogDialog logDialog = LogDialog.getInstance();
        logDialog.setVisible(true);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dialog.createAndShowGUI();
                dialog.setVisible(true);
            }
        });
    }

    private void onWindowClosing() {
        Log.d("stopping rendering...");
        rendering = false;
        try {
            renderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("render thread stopped!");

        Log.d("Exiting...");
        // TODO: Wait for Log dialog
        // try {
        //     LogDialog.getInstance().wait();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        System.exit(0);
    }

    public void createAndShowGUI() {
        Canvas canvas = new Canvas();
        canvas.setBackground(Color.black);
        add(canvas);
        setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();

        KeyboardInput keyboardInput = new KeyboardInput();
        MouseInput mouseInput = new MouseInput();
        canvas.addKeyListener(keyboardInput);
        canvas.addMouseListener(mouseInput);
        canvas.addMouseMotionListener(mouseInput);
        canvas.addMouseWheelListener(mouseInput);
        keyboardInputHandler = new SimpleKeyboardInputHandler(keyboardInput);
        mouseInputHandler = new SimpleMouseInputHandler(mouseInput);

        renderThread = new Thread(this);
        renderThread.start();
    }

    public FrameRateCalculator getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(FrameRateCalculator frameRate) {
        if (frameRate != null && !frameRate.isInitalized())
            frameRate.initialize();
        this.frameRate = frameRate;
    }

    @Override
    public void run() {
        rendering = true;
        frameRate.initialize();
        while (rendering) {
            keyboardInputHandler.poll();
            keyboardInputHandler.processInput();
            mouseInputHandler.poll();
            mouseInputHandler.processInput();
            renderLoop();
        }
    }

    private void renderLoop() {
        do {
            do {
                Graphics g = bufferStrategy.getDrawGraphics();
                g.clearRect(0, 0, getWidth(), getHeight());
                render(g);

                if (g != null) {
                    g.dispose();
                }
            } while (bufferStrategy.contentsRestored());
            bufferStrategy.show();
        } while (bufferStrategy.contentsLost());
    }

    private void render(Graphics g) {
        if (frameRate != null) {
            frameRate.calculate();
            g.setColor(Color.white);
            g.drawString(frameRate.getLatestFrameRateString(), 50, 50);
        }
    }


    // private class GamePanel extends JPanel {
    //
    //     @Override
    //     public void paint(Graphics g) {
    //         super.paint(g);
    //         onPaint(g);
    //         repaint();
    //     }
    //
    //     private void onPaint(Graphics g) {
    //         if (frameRate != null) {
    //             frameRate.calculate();
    //             g.setColor(Color.white);
    //             g.drawString(frameRate.getLatestFrameRateString(), 50, 50);
    //         }
    //     }
    // }
}
