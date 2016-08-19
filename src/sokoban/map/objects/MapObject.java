package sokoban.map.objects;

import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.Vector2f;
import sokoban.game.engine.graphics.shapes.Drawable;
import sokoban.game.engine.graphics.shapes.Square;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public abstract class MapObject extends Square implements Drawable {
    public final static int PICWIDTH = 1;
    protected ScreenMappingTool screenMappingTool;
    //protected Point p;
    private Image drawingPic;

    public MapObject(Vector2f start, Image pic) {
        super(start, PICWIDTH);
        this.drawingPic = pic;
    }

    public MapObject(Point start) {
        super(new Vector2f(start.x, start.y), PICWIDTH);
    }

    public Point getPos() {
        return new Point((int)originVectors[0].x, (int)originVectors[0].y);
    }

    public void setPos(Vector2f start) {
        super.setOriginVectors(start, PICWIDTH);
    }

    public void setPos(Point start) {
        super.setOriginVectors(new Vector2f(start.x, start.y), PICWIDTH);
    }

    @Override
    public final void fill(Graphics g, double delta) {
        draw(g, delta);
    }

    @Override
    public final void draw(Graphics g, double delta) {
        update(delta);

        if (drawingPic==null)
            throw new IllegalArgumentException();

        g.drawImage(drawingPic, (int) currentVectors[0].x, (int) currentVectors[0].y,
                (int) (currentVectors[2].x - currentVectors[0].x), (int) (currentVectors[2].y - currentVectors[0].y), null);
    }
}
