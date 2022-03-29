package kingdoms.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import kingdoms.biome.Biome;
import kingdoms.biome.BiomeTile;
import kingdoms.tile.Tile;

public class BiomeScreen implements Screen {
    final Biome biome;
    final GameName game;
    Texture biomeTiles;
    TiledMap map;
    TiledMapRenderer renderer;
    OrthographicCamera camera;

    public BiomeScreen(final Biome biome, final GameName game) {
        this.biome = biome;
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 20,20);
        camera.update();

        Tile[][] tiles = biome.getTiles();

        biomeTiles = new Texture(Gdx.files.internal("Garden-TileSet.png"));
        TextureRegion[][] splitTiles = TextureRegion.split(biomeTiles, 16, 16);
        map = new TiledMap();
        MapLayers layers = map.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(tiles[0].length, tiles.length, 16, 16);


        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                TextureRegion specificTile = switch ((BiomeTile) tiles[y][x]) { //need to change Tile from interface to Enum
                    case GRASS -> splitTiles[1][2];
                    case TREE -> splitTiles[7][6];
                    case WATER -> splitTiles[0][9];
                    case ROCK -> splitTiles[8][12];
                    default -> splitTiles[0][0];
                };

                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(specificTile));
                layer.setCell(x, y, cell);
            }
        }


        layers.add(layer);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f); // scaling is 1/16px
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        camera.update();
        renderer.setView(camera);
        renderer.render();

        if (Gdx.input.justTouched()) {
            game.returnToMapScreen();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        biomeTiles.dispose();
        map.dispose();
    }
}