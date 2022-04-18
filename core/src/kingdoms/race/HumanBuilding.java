package kingdoms.race;

import kingdoms.biome.BiomeTile;
import kingdoms.tile.BuildingTile;

import static kingdoms.race.Resource.*;

import java.util.EnumMap;

public enum HumanBuilding implements BuildingTile {
    SETTLEMENT(new EnumMap<Resource, Double>(Resource.class) {{
        put(WOOD, 50.0);
        put(STONE, 50.0);
        put(GOLD, 50.0);
    }} ),
    CASTLE( new EnumMap<Resource, Double>(Resource.class) {{
        put(WOOD, 100.0);
        put(STONE, 100.0);
        put(GOLD, 100.0);
    }} ),
    HOUSE(new EnumMap<Resource, Double>(Resource.class) {{
        put(WOOD, 50.0);
        put(STONE, 10.0);
        put(GOLD, 10.0);
    }} ),
    FARM(new EnumMap<Resource, Double>(Resource.class) {{
        put(WOOD, 10.0);
        put(STONE, 10.0);
    }} );

    // Order: BiomeTile, HumanBuilding,
    private final int ordinalOffset = BiomeTile.values().length;

    private final EnumMap<Resource, Double> resourceCost;

    HumanBuilding(EnumMap<Resource, Double> resourceCost) {
        this.resourceCost = resourceCost;
    }

    public EnumMap<Resource, Double> getResourceCost() {
        return resourceCost;
    }

    @Override
    public int getTileID() {
        return this.ordinal() + ordinalOffset;
    }
}
