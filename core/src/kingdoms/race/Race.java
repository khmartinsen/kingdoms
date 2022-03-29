package kingdoms.race;

import kingdoms.biome.Biome;
import kingdoms.tile.BuildingTile;

import java.util.ArrayList;

// each player is a specific race
// rename Race to Player?
public abstract class Race {
    private final String raceName;
    private final String playerName;
    private final BuildingTile[] raceBuildings;
    protected ArrayList<Kingdom> kingdoms = new ArrayList<Kingdom>();
    protected ArrayList<Biome> visibleBiomes = new ArrayList<Biome>(); // maybe should be a matrix the same size as map with boolean values
    // or maybe the Map class has the visible biomes for each player? (easier interactions?)
    // should default resources defined here?


    Race(String raceName, String playerName, BuildingTile[] raceBuildings, Kingdom startLocation) {
        this.raceName = raceName;
        this.playerName = playerName;
        this.raceBuildings = raceBuildings;
        kingdoms.add(startLocation);
    }

    public String getRaceName() {
        return raceName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public BuildingTile[] getRaceBuildings() {
        return raceBuildings;
    }

    public ArrayList<Kingdom> getKingdoms() {return kingdoms;}

    public Kingdom getKingdom(Biome biome) {
        for (Kingdom k: kingdoms) {
            if (k.getBiome() == biome) return k;
        }
        return null;
    }

    public ArrayList<Biome> getBiomes() { // can we use a map here?
        ArrayList<Biome> playerBiomes = new ArrayList<Biome>();
        for (Kingdom kingdom: kingdoms) {
            playerBiomes.add(kingdom.getBiome());
        }
        return playerBiomes;
    }

    /**
     * Updates the assets for each kingdom the player controls.
     */
    public void update() {
        for (Kingdom kingdom: kingdoms) {
            kingdom.updateResources();
        }
    }

    public abstract void addKingdom(Biome newBiome);

    public abstract boolean checkWin();

    public abstract void setup();
}
