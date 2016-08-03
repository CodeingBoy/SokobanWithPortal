package sokoban.game.engine.graphics.shapes;

import sokoban.game.engine.graphics.Vector2f;

/**
 * Created by CodeingBoy on 2016-8-2-0002.
 */
public class Rect extends Polygon {

    public Rect(Vector2f v1, Vector2f v2, Vector2f v3, Vector2f v4) {
        super(v1, v2, v3, v4);
    }

    // public Rect(Point p1, Point p2, Point p3, Point p4) {
    //     super(Arrays.asList(p1, p2, p3, p4));
    // }

    public Rect(Vector2f start, int width, int height) {
        super(start, new Vector2f(start.x + width, start.y),
                new Vector2f(start.x + width, start.y + height), new Vector2f(start.x, start.y + height));
    }

    // public Rect(Point start, int width, int height) {
    //     super();
    //     super(Arrays.asList(start, new Point(start.x + width, start.y),
    //             new Point(start.x + width, start.y + height), new Point(start.x, start.y + height)));
    // }
}
