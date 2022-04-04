package kingdoms.tile;

import kingdoms.race.Resource;
import java.util.EnumMap;

public interface BuildingTile extends TileEnum {
    public abstract EnumMap<Resource, Double> getResourceCost();
}
