package kingdoms.biome;

import kingdoms.tile.TileEnum;

import java.util.Arrays;

import static kingdoms.biome.BiomeTile.*;

public class Plain extends Biome {
    public Plain(int row, int col) {
        super(MapTile.PLAIN, row, col);
    }

    @Override
    protected void generateBiomeTiles() {
        // base it on number of total tiles
        int forestGrowths = 2;
        int rockGrowths = 2;
        int waterGrowths = 3;

        // create a Grass filled biome first
        for (TileEnum[] tilesRow : tiles) {
            Arrays.fill(tilesRow, GRASS);
        }

        // grow TREE numTreeGrowths times
        for (int i = 0; i < forestGrowths; i++) {
            this.tileGrow(TREE, 5);
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
