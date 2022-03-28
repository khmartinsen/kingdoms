package kingdoms.tile;

import kingdoms.race.Resource;

import java.util.EnumMap;

public interface BuildingTile extends Tile {
    public abstract EnumMap<Resource, Double> getResourceCost();
}
