package kingdoms.biome;

import kingdoms.tile.TileEnum;

import java.util.Arrays;

import static kingdoms.biome.BiomeTile.*;

public class Forest extends Biome {
    // int forestGrowths here?

    public Forest(int row, int col) {
        super(MapTile.FOREST, row, col);
        generateBiomeTiles();
    }

    @Override
    protected void generateBiomeTiles() {
        // base it on number of total tiles
        int forestGrowths = rnd.nextInt(5) + 10;
        int rockGrowths = 7;
        int waterGrowths = 2;

        // create a TREE filled biome first
        for (TileEnum[] tilesRow : tiles) {
            Arrays.fill(tilesRow, GRASS);
        }

        // grow GRASS numTreeGrowths times
        for (int i = 0; i < forestGrowths; i++) {
            this.tileGrow(TREE, 20);
        }

        // rock generation
        for (int i = 0; i < rockGrowths; i++) {
            this.tileGrow(ROCK, 5);
        }

        // water generation
        for (int i = 0; i < waterGrowths; i++) {
            this.tileGrow(WATER, 5);
        }
    }
}

