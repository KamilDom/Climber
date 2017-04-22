package pl.domanski.kamil.climber.Scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Kamil on 18.04.2017.
 */

public class GameOverScene implements Scene {

    private int xStart;
    private int yStart;
    private int windowWidth;
    private int windowHeight;
    private int windowSpeed;


    public GameOverScene(int xStart, int yStart, int windowWidth, int windowHeight, int windowSpeed) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowSpeed = windowSpeed;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void terminate() {

    }

    @Override
    public void recieveTouch(MotionEvent event) {

    }
}
