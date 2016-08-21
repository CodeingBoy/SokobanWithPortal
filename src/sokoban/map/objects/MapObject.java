package sokoban.map.objects;

import sokoban.game.engine.graphics.Vector2f;
import sokoban.game.engine.graphics.shapes.Drawable;
import sokoban.game.engine.graphics.shapes.Square;
import sokoban.map.GameObjectsMappingTool;
import sokoban.utils.Settings;

import java.awt.*;


public abstract class MapObject extends Square implements Drawable {
    public final static int PICWIDTH = 1;
    protected static int XOffset, YOffset; // 供转换到世界坐标
    protected static GameObjectsMappingTool gameObjectsMappingTool;
    protected Point curPos;
    private Image drawingPic;

    public MapObject(Point pos, Image pic) {
        super(new Vector2f(convertToWorld(pos)), PICWIDTH); // 转换到世界坐标
        curPos = pos;
        this.drawingPic = pic;
    }

    public static void setGameObjectsMappingTool(GameObjectsMappingTool gameObjectsMappingTool) {
        MapObject.gameObjectsMappingTool = gameObjectsMappingTool;
    }

    public static void setXOffset(int XOffset) {
        MapObject.XOffset = XOffset;
    }

    public static void setYOffset(int YOffset) {
        MapObject.YOffset = YOffset;
    }

    public static void setOffset(Point offset) {
        XOffset = offset.x;
        YOffset = offset.y;
    }

    public static Point convertToWorld(Point p) {
        return new Point(p.x + XOffset, p.y + YOffset);
    }

    public Point getPos() {
        return curPos;
    }

    public void setPos(Point newPos) {
        super.setOriginVectors(new Vector2f(convertToWorld(newPos)), PICWIDTH);
        curPos = newPos;
        updateW2SVectors();
    }

    public void updateW2SVectors() {
        if (gameObjectsMappingTool == null)
            w2sVectors = originVectors;
        else
            w2sVectors = gameObjectsMappingTool.getW2sMatrix().mul(originVectors);
    }

    @Override
    public final void fill(Graphics g, double delta) {
        draw(g, delta);
    }

    @Override
    public void draw(Graphics g, double delta) {
        if (w2sVectors == null) updateW2SVectors();

        update(delta);

        if (drawingPic == null)
            throw new IllegalArgumentException();

        g.drawImage(drawingPic, (int) currentVectors[0].x, (int) currentVectors[0].y,
                (int) (currentVectors[2].x - currentVectors[0].x), (int) (currentVectors[2].y - currentVectors[0].y), null);

        if (Settings.isDebugMode()) {
            // 注意！！！显示的坐标是从0开始的
            g.setColor(new Color(255, 255, 255, 200));
            g.drawString(curPos.x + "," + curPos.y, (int) currentVectors[0].x, (int) currentVectors[0].y + 10);
            g.drawString(originVectors[0].x + "," + originVectors[0].y, (int) currentVectors[0].x, (int) currentVectors[0].y + 30);
            g.drawString(currentVectors[0].x + "," + currentVectors[0].y, (int) currentVectors[0].x, (int) currentVectors[0].y + 50);
        }
    }
}
