package kingdoms.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import kingdoms.biome.Biome;
import kingdoms.race.HumanBuilding;
import kingdoms.tile.TileEnum;

public class BiomeScreen implements Screen {
    final Biome biome;
    final GameName game;
    TiledMap map = new TiledMap();
    TiledMapRenderer renderer;
    OrthographicCamera camera = new OrthographicCamera();
    Stage stage;

    boolean showMenu = false;

    public BiomeScreen(final Biome biome, final GameName game) {
        this.biome = biome;
        this.game = game;

        stage = new Stage(new ScreenViewport());

        camera.setToOrtho(false, 20,20);
        camera.update();

        TileEnum[][] tiles = biome.getTiles();

        MapLayers layers = map.getLayers();

        TiledMapTileLayer layer = new TiledMapTileLayer(tiles[0].length, tiles.length, 16, 16);
        layer.setName("biome");

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                Cell cell = new Cell();
                cell.setTile(game.biomeTileSet.getTile((tiles[y][x]).getTileID())); //need to change casting
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

        ScreenUtils.clear(0,0,0,1);
        camera.update();
        renderer.setView(camera);
        renderer.render();

        if (Gdx.input.justTouched()) {
            game.returnToMapScreen();
            dispose();
        }

        //stage.act(delta);
        stage.draw();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        highlightArea();
        highlightTile(game.biomeTileSet.getTile(HumanBuilding.HOUSE.getTileID()).getTextureRegion());
        //highlightTile();
        game.batch.end();

        game.hudBatch.begin();
        String resources = game.player.getKingdom(biome).printResources();
        game.font.draw(game.hudBatch, resources, 10, Gdx.graphics.getHeight() - 20);
        game.hudBatch.end();
    }

    @Override
    public void resize(int width, int height) {
    /* keeps the camera view from being skewed, fixed aspect ratio
        camera.viewportWidth = width / 16f;
        camera.viewportHeight = height / 16f;
        camera.update();
    */
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
        stage.dispose();
    }

    private void highlightTile() {
        Vector3 position = new Vector3();
        position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(position);
        position.x = (int)position.x;
        position.y = (int)position.y;
        game.batch.draw(game.biomeTileSet.getTile(3).getTextureRegion(), position.x, position.y, 1, 1);
    }

    private void highlightTile(TextureRegion texture) {
        Vector3 position = new Vector3();
        position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(position);
        position.x = (int)position.x;
        position.y = (int)position.y;
        game.batch.setColor(1,1,1,.7f);
        game.batch.draw(texture, position.x, position.y, 1, 1);
        game.batch.setColor(1,1,1,1);
    }

    private void highlightArea() {
        Vector3 position = new Vector3();
        position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(position);
        position.x = (int)position.x;
        position.y = (int)position.y;
        TextureRegion area = game.mapTileSet.getTile(3).getTextureRegion();
        game.batch.setColor(1,0,0,.5f);
        game.batch.draw(area, position.x - 1, position.y - 1, 3, 3);
        game.batch.setColor(1,1,1,1);
    }
}
