package sokoban.game.engine.graphics;

/**
 * Created by CodeingBoy on 2016-7-16-0016.
 */
public class Vector2f {
    public float x, y, w;

    public Vector2f(float x, float y, float w) {
        this.x = x;
        this.y = y;
        this.w = w;
    }

    public void move(float tx,float ty){
        x += tx;
        y += ty;
    }

    public void scale(float sx,float sy){
        x *= sx;
        y *= sx;
    }

    public void rotate(float rad){
        float tmp = (float) (x * Math.cos(rad) - y * Math.sin(rad));
        y = (float) (x * Math.sin(rad) - y * Math.cos(rad));
        x = tmp;
    }

    public void shear(float sx, float sy){
        float tmp = x + sx * y;
        y += sy * x;
        x = tmp;
    }
}
