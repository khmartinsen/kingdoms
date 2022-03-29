package kingdoms.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import kingdoms.race.Humans;
import kingdoms.race.Race;

import java.util.Map;

public class GameName extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    private MapScreen mapScreen;
    public Race player;

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
