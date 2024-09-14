package ru.quickstartschool;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {

    Texture backGround;
    Texture flyTexture;

    SpriteBatch batch;
    OrthographicCamera camera;

    @Override
    public void create() {

        backGround = new Texture(Gdx.files.internal("background.png"));
        flyTexture = new Texture(Gdx.files.internal("fly.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.CLEAR);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(backGround, 0, 0, 800, 480);
        batch.draw(flyTexture, 800 / 2, 480 / 2, 64, 64);

        batch.end();
    }

    @Override
    public void dispose() {
        backGround.dispose();
        flyTexture.dispose();
        batch.dispose();
    }
}
