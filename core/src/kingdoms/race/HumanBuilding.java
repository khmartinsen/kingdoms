package kingdoms.race;

import kingdoms.tile.BuildingTile;

import static kingdoms.race.Resource.*;

import java.util.EnumMap;

// Should this be put in Humans class file and be named enum Buildings?
// then each race just has the form of RACE.Buildings."..."???
// can they still use implements?

// Maybe just in a human folder that would give the same effect?

public enum HumanBuilding implements BuildingTile {
    SETTLEMENT("S", new EnumMap<Resource, Double>(Resource.class) {{
        put(WOOD, 50.0);
        put(STONE, 50.0);
        put(GOLD, 50.0);
    }} ), // change to unit settlers?
    CASTLE("C", new EnumMap<Resource, Double>(Resource.class) {{
        put(WOOD, 100.0);
        put(STONE, 100.0);
        put(GOLD, 100.0);
    }} ),
    HOUSE("H", new EnumMap<Resource, Double>(Resource.class) {{
        put(WOOD, 50.0);
        put(STONE, 10.0);
        put(GOLD, 10.0);
    }} ),
    FARM("F", new EnumMap<Resource, Double>(Resource.class) {{
        put(WOOD, 10.0);
        put(STONE, 10.0);
    }} );

    // do a map of <resource, cost> then a function related to it for when building cost

    private final EnumMap<Resource, Double> resourceCost;

    private final String tileString;
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[41m";


    HumanBuilding(String tileString, EnumMap<Resource, Double> resourceCost) {
        this.tileString = tileString;
        this.resourceCost = resourceCost;
    }

    public EnumMap<Resource, Double> getResourceCost() {
        return resourceCost;
    }

    @Override
    public String printTile() {
        return ANSI_YELLOW_BACKGROUND + tileString;
    }
}
