package kingdoms.biome;

import kingdoms.tile.*;
import java.util.Random;


public abstract class Biome {
    protected Tile[][] tiles; //maybe just int?
    private int[] tileOffset;
    private final String mapTile;
    private final int[] mapLocation;
    // count of each tile type?
    private static final String ANSI_BLACK_BACKGROUND = "\u001B[49m";
    protected Random rnd = new Random();

    Biome(String mapTile, int row, int col) {
        this.mapTile = mapTile;
        mapLocation = new int[]{row, col};
        generateBiomeShape();
    }
    
    // add a function that returns specific kingdom object(s) from occupied players

    private void generateBiomeShape() {
        tiles = new Tile[20][20];
    }

    // Biome functions

    /**
     * Replaces a tile at row,col coordinates without error checking or array out of bounds checking.
     * @param newTile
     * @param row
     * @param col
     */
    // could include the logic of validBuildingLocation if we passed an override boolean (overloaded)
    public void replaceTile(Tile newTile, int row, int col) {
        tiles[row][col] = newTile;
    }

    /**
     * Checks that the row, col coordinates are not out of array bounds
     * and that tile at that spot is not a BuildingTile or a ROCK.
     * @param row
     * @param col
     * @return true if valid
     */
    public boolean validBuildingLocation(int row, int col) {
        if (row > 0 && row < tiles.length) {
            if (col > 0 && col < tiles[row].length) {
                if (tiles[row][col] instanceof BuildingTile || tiles[row][col] == BiomeTile.ROCK) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getMapTile() {
        return mapTile;
    }

    public int[] getMapLocation() {
        return mapLocation;
    }

    /**
     * Puts the biome tiles into a String[] by their row to be used in a printing method.
     * Calls printTile() which contains an ANSI background coloring.
     * ANSI_BLACK_BACKGROUND is appended at the end to "reset" back to default background.
     * @return
     */
    public String[] toStringArray() {
        String[] biomeString = new String[tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < tiles[i].length; j++) {
                line.append(tiles[i][j].printTile() + " " + ANSI_BLACK_BACKGROUND);
            }
            biomeString[i] = line.toString();
        }
        return biomeString;
    }

    /**
     * Used by Biome subclasses to generate the tiles in their biome.
     * This current implementation uses a recursive method for generation.
     * @param tile the tile we want to grow
     * @param iterations the more iterations leads to larger growth
     */
    protected void tileGrow(final Tile tile, int iterations) {
        int row = rnd.nextInt(tiles.length);
        int col = rnd.nextInt(tiles[row].length);
        tiles[row][col] = tile;
        recursiveTileGrow(tile, row, col, iterations);
    }

    /**
     * Recursive method for generating tiles in a biome. Used by tileGrow.
     * @param tile we want to grow
     * @param row
     * @param col
     * @param count (max) iterations to run
     */
    private void recursiveTileGrow(final Tile tile, int row, int col, int count) {
        if (count < 0) {
            return;
        }
        // randomize which direction we are moving way from row,col
        int colOffset = rnd.nextInt(4);
        if (colOffset == 3) colOffset = 1; // 50% for 0. 50% for -1,1
        else if (colOffset == 2) colOffset = -1;
        else colOffset = 0;

        int rowOffset = 0;
        if (colOffset == 0) {
            // dont let rowOffset also be 0
            rowOffset = rnd.nextInt(2);
            if (rowOffset == 0) rowOffset = -1;
        }
        else {
            rowOffset = rnd.nextInt(4);
            if (rowOffset == 3) rowOffset = 1; // 50% for 0. 50% for -1,1
            else if (rowOffset == 2) rowOffset = -1;
            else rowOffset = 0;
        }

        /* scattering
        int colMultiplier = 1;
        if (rnd.nextDouble() > .8) {
            colMultiplier = 2;
        }

        int rowMultiplier = 1;
        if (rnd.nextDouble() > .8) {
            rowMultiplier = 2;
        }
        */

        col += colOffset;
        row += rowOffset;

        if (row >= 0 && col >= 0 && row < tiles.length && col < tiles[row].length) {
            if (tiles[row][col] == tile) {
                // if there is a tree already, go back and try again
                recursiveTileGrow(tile,row - rowOffset, col - colOffset, count - 2);
            }
            else {
                tiles[row][col] = tile;
                recursiveTileGrow(tile,row - rowOffset, col - colOffset, count - 2);
                recursiveTileGrow(tile, row, col, count - 1);
            }
        }
        else {
            recursiveTileGrow(tile, row - rowOffset, col - colOffset, count - 1);
        }

    }

    /**
     * Must be called by Biome subclasses to specify values for tileGrow for each
     * relevant tile in that biome.
     */
    protected abstract void generateBiomeTiles();

    public Tile[][] getTiles() {
        return tiles;
    }
}
