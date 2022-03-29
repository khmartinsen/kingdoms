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
    OrthographicCamera camera;
    TiledMapRenderer renderer;
    TiledMap map;
    Texture mapTiles;
    BiomeMap biomeMap;


    public MapScreen(final GameName game) { //take Race input for each player
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 5,5);

        // Create the map of biomes
        biomeMap = new BiomeMap(5, 5);

        game.player = new Humans("Kevin", biomeMap.getBiome(0,0));
        // Create starting player buildings
        //game.player.setup();

        mapTiles = new Texture(Gdx.files.internal("Garden-TileSet.png"));
        TextureRegion[][] splitTiles = TextureRegion.split(mapTiles, 16,16);
        map = new TiledMap();
        MapLayers layers = map.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(5, 5, 16, 16);

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Biome currentBiome = biomeMap.getBiome(y,x);
                TextureRegion specificTexture;
                if (currentBiome instanceof Forest) {
                    specificTexture = splitTiles[7][6];
                }
                else if (currentBiome instanceof Plain) {
                    specificTexture = splitTiles[1][2];
                }
                else {
                    specificTexture = splitTiles[0][0];
                }
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(specificTexture));
                layer.setCell(x,y, cell);
            }
        }

        layers.add(layer);
        renderer = new OrthogonalTiledMapRenderer(map, 1/16f);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        camera.update();
        //game.batch.setProjectionMatrix(camera.combined);
        renderer.setView(camera);
        renderer.render();

        game.batch.begin();
        game.batch.end();


        if (Gdx.input.justTouched()) {
            // after unprojecting the correct position is given for the input to choose a tile in the tile map
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            Biome clickedBiome = biomeMap.getBiome((int)touchPos.y, (int)touchPos.x);

            game.setScreen(new BiomeScreen(clickedBiome, game));
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
        mapTiles.dispose();
        map.dispose();
    }
}
