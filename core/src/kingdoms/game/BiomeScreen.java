package kingdoms.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import kingdoms.biome.Biome;
import kingdoms.race.HumanBuilding;
import kingdoms.tile.TileEnum;

public class BiomeScreen implements Screen {
    final Biome biome;
    final GameName game;
    TiledMapRenderer renderer;
    OrthographicCamera gameCamera = new OrthographicCamera();
    OrthographicCamera mapCamera = new OrthographicCamera();
    OrthographicCamera hudCamera = new OrthographicCamera();
    Viewport gameView;
    Viewport menuView;
    Viewport mapView;
    final int mapSizePixels = 2 * 5 * 16;

    Stage menuStage;
    Label resources;



    TiledMap map = new TiledMap();

    public BiomeScreen(final Biome biome, final GameName game) {
        this.biome = biome;
        this.game = game;

        // for viewport layouts

         // 160

        // setup camera, viewport, and menu stage

        gameCamera.setToOrtho(false, 20,20); //change 20 to biome tiles variable
        gameView = new FitViewport(20,20, gameCamera);

        mapCamera.setToOrtho(false, 5,5);
        mapView = new FitViewport(5,5,mapCamera);

        menuView = new FitViewport(mapSizePixels, Gdx.graphics.getHeight() - mapSizePixels);
        menuStage = new Stage(menuView);
        // menu stuff
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        resources = new Label("temp", skin);
        menuStage.addActor(resources);


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
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        Vector3 cursorPos = new Vector3();
        cursorPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        mapView.unproject(cursorPos);

        ScreenUtils.clear(0,0,0,1);

        gameView.setScreenBounds(0,0, w - mapSizePixels,h);
        gameView.apply();
        gameCamera.update();
        renderer.setView(gameCamera);
        renderer.render();

        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        highlightArea();
        highlightTile(game.biomeTileSet.getTile(HumanBuilding.HOUSE.getTileID()).getTextureRegion());
        //highlightTile();
        game.batch.end();


        // map view
        mapView.setScreenBounds(w - mapSizePixels, h - mapSizePixels, mapSizePixels, mapSizePixels);
        mapView.apply();
        game.getMapRenderer().setView(mapCamera);
        game.getMapRenderer().render();


        menuView.setScreenBounds(w - mapSizePixels, 0, mapSizePixels, h - mapSizePixels);
        menuView.apply();
        //stage.act(delta);
        String resourceString = game.player.getKingdom(biome).printResources();
        resources.setText(resourceString);
        resources.setY(h/2 - mapSizePixels);
        menuStage.draw();


        if (Gdx.input.justTouched()) {
            game.returnToMapScreen();
            dispose();
        }

        /*
        game.hudBatch.begin();
        String resources = game.player.getKingdom(biome).printResources();
        game.font.draw(game.hudBatch, resources, 10, Gdx.graphics.getHeight() - 20);
        game.hudBatch.end();
         */
    }

    @Override
    public void resize(int width, int height) {
        gameView.update(width, height);
        mapView.update(width,height);
        menuView.update(width, height);
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
        menuStage.dispose();
    }

    private void highlightTile() {
        Vector3 position = new Vector3();
        position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        gameView.unproject(position);
        game.batch.draw(game.biomeTileSet.getTile(3).getTextureRegion(), (int)position.x, (int)position.y, 1, 1);
    }

    private void highlightTile(TextureRegion texture) {
        Vector3 position = new Vector3();
        position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        gameView.unproject(position);
        game.batch.setColor(1,1,1,.7f);
        game.batch.draw(texture, (int)position.x, (int)position.y, 1, 1);
        game.batch.setColor(1,1,1,1);
    }

    private void highlightArea() {
        Vector3 position = new Vector3();
        position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        gameView.unproject(position);
        position.x = (int)position.x;
        position.y = (int)position.y;
        TextureRegion area = game.mapTileSet.getTile(3).getTextureRegion();
        game.batch.setColor(1,0,0,.5f);
        game.batch.draw(area, position.x - 1, position.y - 1, 3, 3);
        game.batch.setColor(1,1,1,1);
    }
}
