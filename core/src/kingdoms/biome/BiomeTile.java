package kingdoms.biome;

import kingdoms.tile.TileEnum;

// Order: BiomeTile, HumanBuilding,

// tile id starts at 0, first in texture image
public enum BiomeTile implements TileEnum {
    GRASS,
    TREE,
    WATER,
    ROCK;

    @Override
    public int getTileID() {
        return this.ordinal();
    }
}

