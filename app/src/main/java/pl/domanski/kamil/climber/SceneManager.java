package pl.domanski.kamil.climber;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Kamil on 19.03.2017.
 */

public class SceneManager {

    public static final int MENUSTATE =0;
    public static final int GAMESTATE =1;
    private  int ACTIVE_SCENE =1;

    private ArrayList<Scene> scenes = new ArrayList<>();



    public SceneManager() {
        ACTIVE_SCENE = MENUSTATE;
        scenes.add(new MenuScene(this));
        scenes.add(new GameplayScene(this));

    }

    public void setScene(int scene){
        ACTIVE_SCENE = scene;
    }

    public void recieveTouch(MotionEvent event){

        scenes.get(ACTIVE_SCENE).recieveTouch(event);
    }

    public void update(){
       // ACTIVE_SCENE = scenes.get(ACTIVE_SCENE).changeScene(this);
        scenes.get(ACTIVE_SCENE).update();
    }

    public void draw(Canvas canvas){

        scenes.get(ACTIVE_SCENE).draw(canvas);
    }
}
