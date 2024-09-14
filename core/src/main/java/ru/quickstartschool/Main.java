package ru.quickstartschool;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {

    Texture backGround;

    Texture flyTexture;
    Rectangle flyHitBox;
    boolean isAlive;

    SpriteBatch batch;
    OrthographicCamera camera;

    Vector3 touchPos;

    BitmapFont font;

    Music flyMusic;
    Sound flySound;

    @Override
    public void create() {

        backGround = new Texture(Gdx.files.internal("background.png"));
        font = new BitmapFont();
        font.getData().setScale(2);
        font.setColor(Color.BLACK);
        flyMusic = Gdx.audio.newMusic(Gdx.files.internal("zzzzz.mp3"));
        flyMusic.setLooping(true);
        flyMusic.play();

        flyTexture = new Texture(Gdx.files.internal("fly.png"));
        flyHitBox = new Rectangle(800 / 2, 480 / 2, 64, 64);
        isAlive = true;
        flySound = Gdx.audio.newSound(Gdx.files.internal("touch.mp3"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        touchPos = new Vector3();
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.CLEAR);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backGround, 0, 0, 800, 480);
        if (isAlive) {
            batch.draw(flyTexture, 800 / 2, 480 / 2, 64, 64);
        } else {
            font.draw(batch, "WIN!", 800 / 2, 480 / 2);
        }
        batch.end();

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (flyHitBox.contains(touchPos.x, touchPos.y)) {
                isAlive = false;
                flySound.play();
                flyMusic.stop();
            }
        }

    }

    @Override
    public void dispose() {
        backGround.dispose();
        flyTexture.dispose();
        batch.dispose();
        font.dispose();
        flySound.dispose();
        flyMusic.dispose();
    }
}
