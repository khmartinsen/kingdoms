package kingdoms.biome;

import kingdoms.tile.Tile;

public enum BiomeTile implements Tile {
    GRASS(" "),
    TREE("\u001B[42m" + " "),
    WATER("\u001B[44m" + " "),
    ROCK("\u001B[100m"+ " ");

    private String tileString;

    //implement in all Tile interfaces, print which defines the colors for each one
    // return String: tile.COLOR + tile character + RESET

    BiomeTile(String tileString) {
        this.tileString = tileString;
    }

    public String printTile () { return tileString; }

}

