package kingdoms.biome;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import kingdoms.tile.*;
import java.util.Random;

public abstract class Biome {
    protected TileEnum[][] tiles;
    private int[] tileOffset;
    private final MapTile mapTile;
    private final int[] mapLocation;
    protected Random rnd = new Random();

    final static TiledMapTileSet biomeTileSet = loadTileSet();
    private TiledMap map = new TiledMap();
    private OrthogonalTiledMapRenderer renderer;

    Biome(MapTile mapTile, int row, int col) {
        this.mapTile = mapTile;
        mapLocation = new int[]{row, col};
        generateBiomeShape();
        generateBiomeTiles();

        // Create the tiledmap and renderer for the biome
        MapLayers layers = map.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(tiles[0].length, tiles.length, 16, 16);
        layer.setName("biome");

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(biomeTileSet.getTile((tiles[y][x]).getTileID()));
                layer.setCell(x, y, cell);
            }
        }
        layers.add(layer);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
    }
    
    // add a function that returns specific kingdom object(s) from occupied players
    private void generateBiomeShape() {
        tiles = new TileEnum[20][20];
    }

    // Biome functions

    /**
     * Replaces a tile at row,col coordinates without error checking or array out of bounds checking.
     */
    // could include the logic of validBuildingLocation if we passed an override boolean (overloaded)
    public void replaceTile(TileEnum newTile, int row, int col) {
        tiles[row][col] = newTile;
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("biome");
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(biomeTileSet.getTile(tiles[row][col].getTileID()));
        layer.setCell(row,col, cell);
    }

    /**
     * Checks that the row, col coordinates are not out of array bounds
     * and that tile at that spot is not a BuildingTile or a ROCK.
     * @return true if valid
     */
    public boolean validBuildingLocation(int row, int col) {
        if (row >= 0 && row < tiles.length) {
            if (col >= 0 && col < tiles[row].length) {
                TileEnum tile = tiles[row][col];
                if (tile instanceof BuildingTile || tile == BiomeTile.ROCK || tile == BiomeTile.WATER) {
                    return false;
                }
            }
        }
        return true;
    }

    public MapTile getMapTile() {
        return mapTile;
    }

    public int[] getMapLocation() {
        return mapLocation;
    }


    /**
     * Used by Biome subclasses to generate the tiles in their biome.
     * This current implementation uses a recursive method for generation.
     */
    protected void tileGrow(final TileEnum tile, int iterations) {
        int row = rnd.nextInt(tiles.length);
        int col = rnd.nextInt(tiles[row].length);
        tiles[row][col] = tile;
        recursiveTileGrow(tile, row, col, iterations);
    }

    /**
     * Recursive method for generating tiles in a biome. Used by tileGrow.
     */
    private void recursiveTileGrow(final TileEnum tile, int row, int col, int count) {
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

    public TileEnum[][] getTiles() {
        return tiles;
    }

    public OrthogonalTiledMapRenderer getRenderer() { return renderer;}

    private static TiledMapTileSet loadTileSet() {
        TextureRegion[][] splitTiles = TextureRegion.split(new Texture(Gdx.files.internal("biometiles.png")), 16,16);
        TiledMapTileSet tileSet = new TiledMapTileSet();
        int tid = 0; // starting tile id
        for (int j = 0; j < splitTiles.length; j++) {
            for (int i = 0; i < splitTiles[j].length; i++) {
                final StaticTiledMapTile tile = new StaticTiledMapTile(splitTiles[j][i]);
                tile.setId(tid);
                tileSet.putTile(tid,tile);
                tid++;
            }
        }
        return tileSet;
    }

    public static TiledMapTileSet getTileSet() {
        return biomeTileSet;
    }

    /**
     * Must be called by Biome subclasses to specify values for tileGrow for each
     * relevant tile in that biome.
     */
    protected abstract void generateBiomeTiles();


}
