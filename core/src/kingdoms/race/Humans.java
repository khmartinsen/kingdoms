package kingdoms.race;

import kingdoms.biome.Biome;
import java.util.EnumMap;
import java.util.Map;

import static kingdoms.race.Resource.*;

// Has buildings, magic / technology, religion?, trading, exploring
public class Humans extends Race {
    // default kingdom resources and population (no max population? maybe just logarithm it
    private static final Asset[] defaultResources = {
            new Asset(POPULATION, 10.0, 0.05),
            new Asset(FOOD, 100.0, 1.0),
            new Asset(WOOD, 100.0, 1.0),
            new Asset(STONE, 100.0, 1.0),
            new Asset(GOLD, 0.0, 1.0)
        };

    public Humans(String playerName, Biome startBiome) {
        super("Humans", playerName, HumanBuilding.values(), startBiome);
    }

    public HumanBuilding[] getBuildings() { return HumanBuilding.values(); }

    @Override
    public void addKingdom(Biome newBiome) {
        kingdoms.add(new Kingdom(newBiome, defaultResources));
        addVisibleBiome(newBiome);
    }

    @Override
    public void setup() {
        // game start setup, NEEDS TO BE REWORKED to be smart
        Biome startBiome = getBiomes().get(0);
        startBiome.replaceTile(HumanBuilding.CASTLE,10,10);
        startBiome.replaceTile(HumanBuilding.HOUSE, 12,12);
        startBiome.replaceTile(HumanBuilding.HOUSE, 9,9);
        startBiome.replaceTile(HumanBuilding.HOUSE, 11,9);
    }

    /*
    private boolean buildCastle(Biome biome, int row, int col) {
        // row and col is the bottom right of the castle location

        //replace biome tiles
        biome.replaceTile(HumanBuilding.CASTLE, int row);

        for (int id = 15; id >= 8; id--) {
            biome.replaceTile();
        }
    }
    */

    /**
     * (NOT IMPLEMENTED YET)
     * Checks the conditions for race Humans to win.
     * This would look for number of Kingdoms, total resources, and other conditions.
     * @return
     */
    @Override
    public boolean checkWin() {
        return false;
    }
}
