package kingdoms.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import kingdoms.biome.Biome;
import kingdoms.biome.BiomeMap;
import kingdoms.biome.Forest;
import kingdoms.biome.Plain;
import kingdoms.race.Humans;

public class MapScreen implements Screen {
    final GameName game;
    OrthographicCamera camera = new OrthographicCamera();
    TiledMapRenderer renderer;
    TiledMap map = new TiledMap();
    BiomeMap biomeMap;


    public MapScreen(final GameName game) { //take Race input for each player
        this.game = game;
        camera.setToOrtho(false, 5,5);
        camera.update();

        // Create the map of biomes
        biomeMap = new BiomeMap(5, 5);

        game.player = new Humans("Kevin", biomeMap.getBiome(0,0));
        // Create starting player buildings
        //game.player.setup();

        MapLayers layers = map.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(5, 5, 16, 16);
        layer.setName("map");

        // fill with fog of war tiles
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Cell cell = new Cell();
                cell.setTile(game.mapTileSet.getTile(2));
                layer.setCell(x,y, cell);
            }
        }

        // only show visible biomes
        for (Biome visibleBiome: game.player.getVisibleBiomes()) {
            int[] coords = visibleBiome.getMapLocation();
            System.out.println("This thing: " + coords[0]);
            Cell cell = new Cell();
            cell.setTile(game.mapTileSet.getTile(visibleBiome.getMapTile().ordinal()));
            layer.setCell(coords[0], coords[1], cell);
        }
        layers.add(layer);

        renderer = new OrthogonalTiledMapRenderer(map, 1/16f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            // after unprojecting the correct position is given for the input to choose a tile in the tile map
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            Biome clickedBiome = biomeMap.getBiome((int)touchPos.y, (int)touchPos.x);

            if (game.player.getVisibleBiomes().contains(clickedBiome)) {
                // player can only see visible biomes
                game.setScreen(new BiomeScreen(clickedBiome, game));
            }
        }

        ScreenUtils.clear(0,0,0,1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        renderer.setView(camera);
        renderer.render();

        game.batch.begin();
        highlightTile();
        game.batch.end();

        game.hudBatch.begin();
        //game.font.draw(game.hudBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 10);
        highlightText();
        game.hudBatch.end();
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

    private void highlightTile() {
        Vector3 position = new Vector3();
        position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(position);
        position.x = (int)position.x;
        position.y = (int)position.y;
        game.batch.draw(game.mapTileSet.getTile(3).getTextureRegion(), position.x, position.y, 1, 1);
    }
    private void highlightText() {
        Vector3 position = new Vector3();
        position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(position);
        position.x = (int)position.x * (Gdx.graphics.getWidth() / 5);
        position.y = (int)position.y * (Gdx.graphics.getHeight() / 5);
        game.font.draw(game.hudBatch, "HERE", position.x, position.y);
    }
}
