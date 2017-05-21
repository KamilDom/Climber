package pl.domanski.kamil.climber.Scenes;

import android.graphics.Canvas;
import android.graphics.Typeface;
import android.view.MotionEvent;

import java.util.ArrayList;

import pl.domanski.kamil.climber.Engine.Constans;

/**
 * Created by Kamil on 19.03.2017.
 */

public class SceneManager {

    public static final int MENUSTATE = 0;
    public static final int GAMESTATE = 1;
    public static final int HIGHSCORESTATE = 2;
    public static final int SETTINGSTATE = 3;

    public static boolean PAUSE = false;
    public static boolean GAMEOVER = false;


    private int ACTIVE_SCENE = MENUSTATE;
    private int lastScene;

    private ArrayList<Scene> scenes = new ArrayList<>();
    private MenuScene menuScene = new MenuScene(this);
    public GameplayScene gameplayScene = new GameplayScene(this);
    public HighscoreScene highscoreScene = new HighscoreScene(this);
    private SettingsScene settingsScene = new SettingsScene(this);

    public SceneManager() {
        scenes.add(menuScene);
        scenes.add(gameplayScene);
        scenes.add(highscoreScene);
        scenes.add(settingsScene);


    }

    public void setScene(int scene) {
        lastScene = ACTIVE_SCENE;
        ACTIVE_SCENE = scene;
    }

    public void recieveTouch(MotionEvent event) {

        scenes.get(ACTIVE_SCENE).recieveTouch(event);
    }

    public void update() {

        scenes.get(ACTIVE_SCENE).update();
    }

    public void draw(Canvas canvas) {

        scenes.get(ACTIVE_SCENE).draw(canvas);
    }

    public void OnBackPressed() {
        if (ACTIVE_SCENE == HIGHSCORESTATE) {
            setScene(lastScene);
        } else if (ACTIVE_SCENE == GAMESTATE) {
            if (gameplayScene.pauseScene.showConfirmScene == true)
                gameplayScene.pauseScene.showConfirmScene = false;
            else if (GAMEOVER) {
                gameplayScene.reset();
                setScene(0);
            } else
                PAUSE = !PAUSE;
        } else if (ACTIVE_SCENE == SETTINGSTATE) {
            setScene(lastScene);
        } else if (ACTIVE_SCENE == MENUSTATE) {
            if (menuScene.showConfirmScene == true)
                menuScene.showConfirmScene = false;
            else menuScene.showConfirmScene = true;
        }


    }

    public int getLastScene() {

        return lastScene;
    }


    public void resetGame() {

        gameplayScene.reset();
    }


    public void onPause() {
        PAUSE = true;
    }
}
