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

    //public static final String[] actions = {"(B)uild"};

    public Humans(String playerName, Biome startBiome) {
        super("Humans", playerName, HumanBuilding.values(), new Kingdom(startBiome, defaultResources));
    }

    public HumanBuilding[] getBuildings() { return HumanBuilding.values(); }

    /**
     * Returns a string array containing the index and the corresponding HumanBuilding
     * @return arrayIndex) BuildingTileName
     */
    // should this be in Kingdom instead?
    public String[] buildOptions() {
        String[] optionsArray = new String[HumanBuilding.values().length];
        //optionsArray[0] = " - - - BUILD OPTIONS - - - ";
        for (int i = 0; i < optionsArray.length; i++) {
            StringBuilder line = new StringBuilder();
            HumanBuilding building = HumanBuilding.values()[i];
            line.append((i) + ") " + building.name() + " | ");
            for (Map.Entry<Resource, Double> mapEntry : building.getResourceCost().entrySet()) {
                line.append(mapEntry.getKey().name() + ": " + mapEntry.getValue().intValue() + " ");
            }
            optionsArray[i] = line.toString();
        }
        return optionsArray;
    }

    @Override
    public void addKingdom(Biome newBiome) {
        kingdoms.add(new Kingdom(newBiome, defaultResources));
    }

    @Override
    public void setup() {
        // game start setup, NEEDS TO BE REWORKED to be smart
        getBiomes().get(0).replaceTile(HumanBuilding.CASTLE,10,10);
        //inform mapScreen so we can draw a castle on the map screen?
    }

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
