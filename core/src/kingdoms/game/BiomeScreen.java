package kingdoms.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
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
    TiledMap map = new TiledMap();
    TiledMapRenderer renderer;
    OrthographicCamera camera = new OrthographicCamera();

    public BiomeScreen(final Biome biome, final GameName game) {
        this.biome = biome;
        this.game = game;

        camera.setToOrtho(false, 20,20);
        camera.update();

        Tile[][] tiles = biome.getTiles();

        map = new TiledMap();
        MapLayers layers = map.getLayers();

        TiledMapTileLayer layer = new TiledMapTileLayer(tiles[0].length, tiles.length, 16, 16);
        layer.setName("biome");

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                Cell cell = new Cell();
                cell.setTile(game.biomeTileSet.getTile(((BiomeTile)tiles[y][x]).ordinal())); //need to change casting
                layer.setCell(x, y, cell);
            }
        }

        layers.add(layer);
        layers.add(new TiledMapTileLayer(tiles[0].length, tiles.length, 16, 16)); // empty layer for glow
        renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f); // scaling is 1/16px
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Vector3 cursorPos = new Vector3();
        cursorPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(cursorPos);

        // tile glow at cursor type thing. Should be a function in biome screen and map screen
        // overload with a custom texture (pass building tiles and stuff that we should be using to show
        // what could be built
        /*
        MapLayers layers = map.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(20,20,16,16);
        layer.setCell((int)cursorPos.x, (int)cursorPos.y, new Cell().setTile(new StaticTiledMapTile(testGlow)));
        map.getLayers().remove(1);
        layers.add(layer);
        */


        ScreenUtils.clear(0,0,0,1);
        camera.update();
        renderer.setView(camera);
        renderer.render();

        if (Gdx.input.justTouched()) {
            game.returnToMapScreen();
            dispose();
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
        map.dispose();
    }
}
