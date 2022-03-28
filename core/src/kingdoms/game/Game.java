package kingdoms.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import kingdoms.biome.BiomeMap;
import kingdoms.biome.BiomeTile;
import kingdoms.tile.Tile;

import static kingdoms.biome.BiomeTile.*;

public class Game extends ApplicationAdapter {
	Texture tiles;
	TiledMap map;
	TiledMapRenderer renderer;
	OrthographicCamera camera;
	SpriteBatch batch;
	BitmapFont font;
	
	@Override
	public void create () {
		font = new BitmapFont();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		int tilesWidth = 3;
		int tilesHeight = 3;
		camera.setToOrtho(false, tilesWidth * 20, tilesHeight * 20); // x by y tiles
		camera.update();

		// my map/biomes
		BiomeMap biomeMap = new BiomeMap(tilesWidth,tilesHeight);

		tiles = new Texture(Gdx.files.internal("Garden-TileSet.png"));
		TextureRegion[][] splitTiles = TextureRegion.split(tiles, 16,16);
		map = new TiledMap();
		MapLayers layers = map.getLayers();
		TiledMapTileLayer layer = new TiledMapTileLayer(tilesWidth * 20, tilesHeight * 20, 16, 16);

		int xOffset = 0;
		int yOffset = 0;
		//iterate over biome map

		for (int y = 0; y < tilesHeight; y++) {
			for (int x = 0; x < tilesWidth; x++) {
				xOffset = x * 20;
				yOffset = y * 20;
				Tile[][] biomeTiles = biomeMap.getBiome(x,y).getTiles();
				for (int i = 0; i < biomeTiles.length; i++) {
					for (int j = 0; j < biomeTiles[i].length; j++) {
						TextureRegion specificTile;
						switch((BiomeTile)biomeTiles[i][j]) { //need to change Tile from interface to Enum
							case GRASS:
								specificTile = splitTiles[1][2];
								break;
							case TREE:
								specificTile = splitTiles[7][6];
								break;
							case WATER:
								specificTile = splitTiles[0][9];
								break;
							case ROCK:
								specificTile = splitTiles[8][12];
								break;
							default:
								specificTile = splitTiles[0][0];
						}
						Cell cell = new Cell();
						cell.setTile(new StaticTiledMapTile(specificTile));
						layer.setCell(i + xOffset,j + yOffset, cell);
					}
				}
			}
		}
		layers.add(layer);

		renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f); // scaling is 1/16px
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		renderer.setView(camera);
		renderer.render();
		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 20);
		batch.end();
	}
	
	@Override
	public void dispose () {
		tiles.dispose();
		map.dispose();
		batch.dispose();
		font.dispose();
	}
}
