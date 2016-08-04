package sokoban.game.engine.graphics.component;

import sokoban.game.engine.graphics.Vector2f;
import sokoban.game.engine.graphics.shapes.Drawable;
import sokoban.game.engine.graphics.shapes.Rect;

import java.awt.*;
import java.awt.event.MouseListener;

/**
 * Created by CodeingBoy on 2016-8-3-0003.
 */
public abstract class Button extends Rect implements MouseListener, Drawable {
    private String text;
    private Image normalImg, hoverImg, pressImg;

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

    @Override
    public void draw(Graphics g, double delta) {
        g.drawImage(normalImg, (int) originVectors[0].x, (int) originVectors[0].y, null);
    }

    @Override
    public void fill(Graphics g, double delta) {

    }

}
