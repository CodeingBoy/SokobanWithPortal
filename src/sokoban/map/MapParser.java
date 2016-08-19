package sokoban.map;

import sokoban.map.objects.CheckPoint;
import sokoban.map.objects.Floor;
import sokoban.map.objects.MapObject;
import sokoban.map.objects.Wall;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class MapParser {

    /**
     * 解析地图文件
     *
     * @param mapFile 欲解析的地图文件对象
     * @return 地图对象
     * @throws FileNotFoundException 找不到文件
     */
    public static GameMap parseMapFile(File mapFile) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(mapFile)));

        StringBuffer stringBuffer = new StringBuffer();
        try {
            while (bufferedReader.ready())
                stringBuffer.append(bufferedReader.readLine() + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String part[] = stringBuffer.toString().trim().split("\r\n\r\n");

        // 读入地图基本信息
        String basicInf[] = part[0].split("\r\n");
        Map<String, String> inf = new HashMap<>();
        for (String s : basicInf) {
            String[] line = s.split(":");
            inf.put(line[0], line[1]);
        }

        int mapWidth = Integer.parseInt(inf.get("MapWidth"));
        int mapHeight = Integer.parseInt(inf.get("MapHeight"));

        if (inf.get("Offset") != null) {
            int XOffset = Integer.parseInt(inf.get("Offset").split(",")[0]);
            int YOffset = -Integer.parseInt(inf.get("Offset").split(",")[1]);
            MapObject.setOffset(new Point(XOffset, YOffset));
        }

        MapObject[][] mapObjects = new MapObject[mapHeight][mapWidth];

        // 读入地图数据
        String mapRow[] = part[1].split("\r\n");
        for (int row = 0; row < mapRow.length; row++) {
            String[] mapCol = mapRow[row].split(",");
            for (int col = 0; col < mapCol.length; col++) {
                // Point pos = new Point(XOffset + col, YOffset + row); // 不需转换到世界坐标 直接传入原始坐标
                Point pos = new Point(col, row);
                switch (Integer.parseInt(mapCol[col])) {
                    case 1: // floor
                        mapObjects[row][col] = new Floor(pos);
                        break;
                    case 2:
                         mapObjects[row][col] = new CheckPoint(pos);
                         break;
                    // case 3:
                    //     mapObjects[row][col] = new Floor();
                    //     break;
                    case 0:
                        mapObjects[row][col] = new Wall(pos);
                        break;
                    default:
                        mapObjects[row][col] = null;
                        break;
                }
            }
        }

        return new GameMap(mapObjects, mapWidth, mapHeight, inf.get("MapName"), inf);

    }

}
