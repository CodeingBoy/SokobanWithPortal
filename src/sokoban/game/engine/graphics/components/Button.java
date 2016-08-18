package sokoban.game.engine.graphics.components;

import sokoban.game.engine.graphics.Vector2f;
import sokoban.game.engine.graphics.shapes.Drawable;
import sokoban.game.engine.graphics.shapes.Rect;
import sokoban.game.engine.input.handler.Clickable;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-3-0003.
 */
public abstract class Button extends Rect implements Clickable, Drawable {
    private String text;
    private Image normalImg, hoverImg, pressImg;
    private boolean isClicking = false, isHovering = false;

    public Button(Vector2f start, int width, int height, String text, Image normalImg, Image hoverImg, Image pressImg) {
        super(start, width, height);
        this.text = text;
        this.normalImg = normalImg;
        this.hoverImg = hoverImg;
        this.pressImg = pressImg;
    }

    public Button(Vector2f start, String text, Image normalImg, Image hoverImg, Image pressImg) {
        super(start, normalImg.getWidth(null), normalImg.getHeight(null));
        this.text = text;
        this.normalImg = normalImg;
        this.hoverImg = hoverImg;
        this.pressImg = pressImg;
    }

    public Button(Point start, String text, Image normalImg, Image hoverImg, Image pressImg) {
        super(new Vector2f(start.x, start.y), normalImg.getWidth(null), normalImg.getHeight(null));
        this.text = text;
        this.normalImg = normalImg;
        this.hoverImg = hoverImg;
        this.pressImg = pressImg;
    }

    @Override
    public void draw(Graphics g, double delta) {
        update(delta);
        if (isClicking)
            g.drawImage(pressImg, (int) currentVectors[0].x, (int) currentVectors[0].y, null);
        else if (isHovering)
            g.drawImage(hoverImg, (int) currentVectors[0].x, (int) currentVectors[0].y, null);
        else
            g.drawImage(normalImg, (int) currentVectors[0].x, (int) currentVectors[0].y, null);

        isClicking = false;
        isHovering = false;
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
}
