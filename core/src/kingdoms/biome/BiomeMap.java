package kingdoms.biome;
import java.util.Random;

public class BiomeMap {
    private Biome[][] map;

    private BiomeMap() {}

    public BiomeMap(int width, int height) {
        map = new Biome[width][height];
        this.generate();
    }

    public Biome getBiome(int row, int column) {
        return map[row][column];
    }


    /**
     * Generates random biomes for the 2D map.
     */
    // could use biome generation here
    private void generate() {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                int x = new Random().nextInt(2); // gives 0 or 1 for forest or plain
                if (x == 0) {
                    map[row][col] = new Forest(row, col);
                }
                else {
                    map[row][col] = new Plain(row, col);
                }
            }
        }
    }
}
