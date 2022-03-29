package kingdoms.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final GameName game;

    OrthographicCamera camera;

    public MainMenuScreen(final GameName game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 600);
    }

    @Override
    public void show() {

    }

    public void render(float delta) {
        ScreenUtils.clear(0,0, .2f, 1);
        camera.update();
        game.batch.begin();
        game.font.draw(game.batch, "KINGDOMS GAME", 100, 250);
        game.font.draw(game.batch, "Tap anywhere to start", 100, 150);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            MapScreen mapScreen = new MapScreen(game);
            game.setMapScreen(mapScreen);
            game.setScreen(mapScreen);
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

    }
}
