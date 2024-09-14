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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Main extends ApplicationAdapter {

    Texture backGround;

    Texture flyTexture;
    Array<Rectangle> fliesHitBox;
    boolean isAlive;

    SpriteBatch batch;
    OrthographicCamera camera;

    Vector3 touchPos;

    BitmapFont font;

    Music flyMusic;
    Sound flySound;

    long timeStart, timeFinish;

    int delta;
    boolean invert;

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
        isAlive = true;
        flySound = Gdx.audio.newSound(Gdx.files.internal("touch.mp3"));
        fliesHitBox = new Array<>();
        int countFlies = MathUtils.random(10, 15);
        for (int i = 0; i < countFlies; i++) {
            Rectangle newFly = new Rectangle();

            newFly.x = MathUtils.random(0, 800 - 64);
            newFly.y = MathUtils.random(0, 480 - 64);
            newFly.height = 64;
            newFly.width = 64;

            fliesHitBox.add(newFly);
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        touchPos = new Vector3();

        timeStart = TimeUtils.millis();
        delta = 1;
        invert = true;
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.CLEAR);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backGround, 0, 0, 800, 480);
        if (fliesHitBox.size != 0) {
            for (int i = 0; i < fliesHitBox.size; i++) {
                Rectangle currentFly = fliesHitBox.get(i);
                batch.draw(
                    flyTexture,
                    currentFly.x,
                    currentFly.y,
                    currentFly.width,
                    currentFly.height
                );
            }
        } else {
            font.draw(
                batch,
                "WIN! Time: " + ((timeFinish - timeStart) / 1000),
                800 / 2 - 75,
                480 / 2
            );
        }
        batch.end();

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            for (int i = 0; i < fliesHitBox.size; i++) {
                Rectangle currentFly = fliesHitBox.get(i);
                if (currentFly.contains(touchPos.x, touchPos.y)) {
                    flySound.play();
                    fliesHitBox.removeIndex(i);
                }
            }

            if (fliesHitBox.size == 0) {
                flyMusic.stop();
                timeFinish = TimeUtils.millis();
            }
        }

        for (int i = 0; i < fliesHitBox.size; i++) {
            Rectangle currentFly = fliesHitBox.get(i);
            currentFly.x += delta;
            currentFly.y += delta;
        }
        if (invert) {
            delta = -delta;
        }
        invert = !invert;
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
