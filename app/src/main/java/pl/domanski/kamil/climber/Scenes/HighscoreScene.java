package pl.domanski.kamil.climber.Scenes;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.view.MotionEvent;

import java.util.ArrayList;

import pl.domanski.kamil.climber.Engine.Constans;
import pl.domanski.kamil.climber.Engine.OrientationData;
import pl.domanski.kamil.climber.Objects.Button;

/**
 * Created by Kamil on 30.03.2017.
 */

public class HighscoreScene implements Scene {

    private final SceneManager sceneManager;
    private Rect r;
    private Paint paint;
    private Rect back;
    private ArrayList<Button> buttons;
    public int[] highscore;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public HighscoreScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        r = new Rect();
        paint = new Paint();
        back = new Rect(Constans.SCREEN_WIDTH * 3 / 5, Constans.SCREEN_HEIGHT / 4 + Constans.SCREEN_HEIGHT * 3 / 10,
                Constans.SCREEN_WIDTH * 9 / 10, Constans.SCREEN_HEIGHT / 4 + Constans.SCREEN_HEIGHT * 4 / 10);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Constans.CURRENT_CONTEXT);
        editor = sharedPreferences.edit();
        highscore = new int[5];
        readHighscore();


    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawColor(Color.rgb(0, 100, 200));
        paint.setColor(Color.WHITE);
        paint.setTextSize(Constans.SCREEN_WIDTH / 5);
        drawCenterText(canvas, paint, "Highscore", Constans.SCREEN_HEIGHT / 5);

        paint.setTextSize(Constans.SCREEN_HEIGHT / 15);
        for (int i = 1; i < highscore.length + 1; i++) {
            canvas.drawText(i + ". " + highscore[i - 1], Constans.SCREEN_WIDTH / 8, Constans.SCREEN_HEIGHT * 1 / 4 + Constans.SCREEN_HEIGHT * i / 10, paint);
        }
    }

    @Override
    public void terminate() {

    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (back.contains((int) event.getX(), (int) event.getY())) {
                    sceneManager.setScene(0);
                }

                break;


        }

    }

    public void drawCenterText(Canvas canvas, Paint paint, String text, int y) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);

        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        //  float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);

    }

    public void readHighscore() {
        for (int i = 0; i < highscore.length; i++) {
            highscore[i] = sharedPreferences.getInt("Highscore" + i, -2);
        }
    }

    public void setHighscore() {
        for (int i = 0; i < highscore.length; i++) {
            editor.putInt("Highscore" + i, sceneManager.highscoreScene.highscore[i]);
        }
        editor.apply();
    }
}
