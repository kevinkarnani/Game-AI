import java.util.Arrays;
import java.util.Random;

public class GenerateMap {
    public static void main(String[] args) {
        int height = 8, width = 8;
        int startX = -1, startY = -1;
        int newHeight = 2 * height + 1, newWidth = 2 * width + 1;
        int[][] map = new int[newWidth][newHeight];

        // big trees along the edges, small trees on even tiles, land on odd tiles
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                if (i == 0 || j == 0 || i == newWidth - 1 || j == newHeight - 1) {
                    map[i][j] = Objects.BIG_TREE_TILE.getValue();
                } else if (i % 2 == 1 && j % 2 == 1) {
                    map[i][j] = Objects.LAND_TILE.getValue();
                } else {
                    map[i][j] = Objects.SMALL_TREE_TILE.getValue();
                }
            }
        }

        Graph g = new Graph(width, height);

        for (Edge edge : g.kruskal()) {
            int row1 = 2 * (edge.getStart() / width) + 1;
            int col1 = 2 * (edge.getStart() % width) + 1;

            // first land tile is the start position (edges have random weight, so random start)
            if (startX == -1) {
                startX = col1;
                startY = row1;
            }

            // connecting the path from start to end
            int row2 = 2 * (edge.getEnd() / width) + 1;
            int col2 = 2 * (edge.getEnd() % width) + 1;

            // clear the path
            if (col2 > col1) {
                map[row1][col1 + 1] = Objects.LAND_TILE.getValue();
            } else if (row2 > row1) {
                map[row1 + 1][col1] = Objects.LAND_TILE.getValue();
            }
        }

        // 1...w - 1 and 1... h - 1 ascertains that big trees arent removed
        for (int i = 1; i < newWidth - 1; i++) {
            for (int j = 1; j < newHeight - 1; j++) {
                // randomize the map without blocking any open paths
                if (map[i][j] != Objects.LAND_TILE.getValue()) {
                    int r1 = new Random().nextInt(100) + 1;

                    if (r1 <= 5) {
                        map[i][j] = Objects.LAND_TILE.getValue();
                    } else if (r1 <= 15) {
                        map[i][j] = Objects.HOUSE.getValue();
                    } else if (r1 < 25) {
                        map[i][j] = Objects.ROCK.getValue();
                    }
                }
            }
        }

        startX *= 32;
        startY *= 32;
        new FileHandler().writeToFile(startX, startY, map);
    }
}
