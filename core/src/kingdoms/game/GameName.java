package kingdoms.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import kingdoms.race.Humans;
import kingdoms.race.Race;

import java.util.Map;

public class GameName extends Game {
    public SpriteBatch batch;
    public SpriteBatch hudBatch;
    public BitmapFont font;
    private MapScreen mapScreen;
    public Race player;
    public TiledMapTileSet mapTileSet = new TiledMapTileSet(); // does nto have to be in Game, only one intance of mapscreen is created
    public TiledMapTileSet biomeTileSet = new TiledMapTileSet();

    public void create() {
        batch = new SpriteBatch();
        hudBatch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));

        // load in tileSet assets
        //map tileSet
        TextureRegion[][] splitTiles = TextureRegion.split(new Texture(Gdx.files.internal("Garden-TileSet.png")), 16,16);

        int tid = 0; // starting tile id
        for (int j = 0; j < splitTiles.length; j++) {
            for (int i = 0; i < splitTiles[j].length; i++) {
                final StaticTiledMapTile tile = new StaticTiledMapTile(splitTiles[j][i]);
                tile.setId(tid);
                mapTileSet.putTile(tid,tile);
                tid++;
            }
        }

        //biome tileSet - each biome object should just create a map so its loaded in memory and just accessed
        splitTiles = TextureRegion.split(new Texture(Gdx.files.internal("biometiles.png")), 16,16);

        tid = 0; // starting tile id
        for (int j = 0; j < splitTiles.length; j++) {
            for (int i = 0; i < splitTiles[j].length; i++) {
                final StaticTiledMapTile tile = new StaticTiledMapTile(splitTiles[j][i]);
                tile.setId(tid);
                biomeTileSet.putTile(tid,tile);
                tid++;
            }
        }
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        hudBatch.dispose();
        font.dispose();
    }

    public void returnToMapScreen(){
        this.setScreen(mapScreen);
    }

    public void setMapScreen(MapScreen mapScreen) {
        this.mapScreen = mapScreen;
    }

}
