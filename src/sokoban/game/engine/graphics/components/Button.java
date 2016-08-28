package sokoban.game.engine.graphics.components;

import sokoban.game.engine.graphics.Vector2f;
import sokoban.game.engine.graphics.shapes.Drawable;
import sokoban.game.engine.graphics.shapes.Rect;
import sokoban.game.engine.input.handler.Clickable;
import sokoban.game.utils.TextDrawer;
import sokoban.utils.Settings;

import java.awt.*;

/**
 * 按钮类，定义了基于图片的按钮
 */
public abstract class Button extends Rect implements Clickable, Drawable {
    private Image normalImg, hoverImg, pressImg;
    private boolean isClicking = false, isHovering = false;

    @Deprecated
    public Button(Vector2f start, int width, int height, String text, Image normalImg, Image hoverImg, Image pressImg) {
        super(start, width, height);
        this.normalImg = normalImg;
        this.hoverImg = hoverImg;
        this.pressImg = pressImg;
    }

    @Deprecated
    public Button(Vector2f start, String text, Image normalImg, Image hoverImg, Image pressImg) {
        super(start, normalImg.getWidth(null), normalImg.getHeight(null));
        this.normalImg = normalImg;
        this.hoverImg = hoverImg;
        this.pressImg = pressImg;
    }

    @Deprecated
    public Button(Point start, String text, Image normalImg, Image hoverImg, Image pressImg) {
        super(new Vector2f(start.x, start.y), normalImg.getWidth(null), normalImg.getHeight(null));
        this.normalImg = normalImg;
        this.hoverImg = hoverImg;
        this.pressImg = pressImg;
    }

    public Button(Point start, Image normalImg, Image hoverImg, Image pressImg) {
        super(new Vector2f(start.x, start.y), normalImg.getWidth(null), normalImg.getHeight(null));
        this.normalImg = normalImg;
        this.hoverImg = hoverImg;
        this.pressImg = pressImg;
    }

    protected Button(Point start, int width, int height, Image normalImg, Image hoverImg, Image pressImg) {
        super(new Vector2f(start.x, start.y), width, height);
        this.normalImg = normalImg;
        this.hoverImg = hoverImg;
        this.pressImg = pressImg;
    }

    @Override
    public void draw(Graphics g, double delta) {
        update(delta);
        if (isClicking && pressImg != null)
            g.drawImage(pressImg, (int) currentVectors[0].x, (int) currentVectors[0].y, null);
        else if (isHovering && hoverImg != null)
            g.drawImage(hoverImg, (int) currentVectors[0].x, (int) currentVectors[0].y, null);
        else if (normalImg != null)
            g.drawImage(normalImg, (int) currentVectors[0].x, (int) currentVectors[0].y, null);

        isClicking = false;
        isHovering = false;

        if (Settings.isDebugMode()) {
            drawDebugInf(g);
        }
    }

    @Override
    protected void update(double delta) {
        super.update(delta);
    }

    @Override
    public void fill(Graphics g, double delta) {
        draw(g, delta);
    }

    @Override
    public void onClick(Point p) {
        isClicking = true;
    }

    @Override
    public void onHover(Point p) {
        isHovering = true;
    }

    protected boolean isClicking() {
        return isClicking;
    }

    protected boolean isHovering() {
        return isHovering;
    }

    @Override
    protected void drawDebugInf(Graphics g) {
        TextDrawer.drawString(g, (int) currentVectors[0].x, (int) currentVectors[0].y, TextDrawer.FONT_DEBUGINF, TextDrawer.COLOR_DEBUGINF, new String[]{
                originVectors[0].x + "," + originVectors[0].y,
                (int) currentVectors[0].x + "," + (int) currentVectors[0].y,
        });

        g.drawRect((int) currentVectors[0].x, (int) currentVectors[0].y,
                (int) (currentVectors[2].x - currentVectors[0].x), (int) (currentVectors[2].y - currentVectors[0].y));
    }
}
