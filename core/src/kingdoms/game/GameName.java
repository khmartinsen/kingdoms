package kingdoms.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Map;

public class GameName extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    private MapScreen mapScreen; // put a setter/getter so we can set the screen to the map
    // then a method that is void returnToMap() {game.setScreen(mapScreen)}

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public void returnToMapScreen(){
        this.setScreen(mapScreen);
    }

    public void setMapScreen(MapScreen mapScreen) {
        this.mapScreen = mapScreen;
    }
}
