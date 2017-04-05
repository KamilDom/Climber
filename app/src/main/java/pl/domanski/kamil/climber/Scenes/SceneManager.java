package pl.domanski.kamil.climber.Scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import pl.domanski.kamil.climber.Scenes.AboutScene;
import pl.domanski.kamil.climber.Scenes.GameplayScene;
import pl.domanski.kamil.climber.Scenes.MenuScene;
import pl.domanski.kamil.climber.Scenes.Scene;

/**
 * Created by Kamil on 19.03.2017.
 */

public class SceneManager {

    public static final int MENUSTATE = 0;
    public static final int GAMESTATE = 1;
    public static final int SETTINGSTATE = 2;
    public static final int ABOUSTATE = 3;

    public static boolean PAUSE = false;
    public static boolean GAMEOVER = false;

    private int ACTIVE_SCENE = MENUSTATE;
    private int lastScene;

    private ArrayList<Scene> scenes = new ArrayList<>();


    public SceneManager() {

        scenes.add(new MenuScene(this));
        scenes.add(new GameplayScene(this));
        scenes.add(new SettingsScene(this));
        scenes.add(new AboutScene(this));


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
        if(ACTIVE_SCENE==ABOUSTATE){
            ACTIVE_SCENE = MENUSTATE;
        }

        else if(ACTIVE_SCENE==GAMESTATE){
            PAUSE = !PAUSE;
        }
    }

    public int getLastScene(){
        return lastScene;
    }

    public int getSensivity(){
        return SettingsScene.getSensivity();
    }

    public boolean confirmDialog(){


     return true;
    }
}
