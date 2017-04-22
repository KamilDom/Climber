package pl.domanski.kamil.climber.Scenes;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import pl.domanski.kamil.climber.Engine.Constans;
import pl.domanski.kamil.climber.Engine.GamePanel;
import pl.domanski.kamil.climber.Engine.MainActivity;
import pl.domanski.kamil.climber.Objects.Button;

/**
 * Created by Kamil on 03.04.2017.
 */
public class SettingsScene implements Scene {


    private static int sensivity;
    private final SceneManager sceneManager;
    private Rect r;
    private Paint paint;
    private ArrayList<Button> buttons;
    private int textButtonColor = Color.rgb(255, 255, 255);
    private int backgroundButtonColor = Color.rgb(0, 120, 0);


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SettingsScene(SceneManager sceneManager) {
        buttons = new ArrayList<Button>();
        buttons.add(new Button(Constans.SCREEN_WIDTH * 29 / 50, Constans.SCREEN_HEIGHT * 11 / 40, Constans.SCREEN_WIDTH/6, Constans.SCREEN_WIDTH/6, "+", Constans.SCREEN_WIDTH / 10, textButtonColor, backgroundButtonColor));
        buttons.add(new Button(Constans.SCREEN_WIDTH * 40 / 50, Constans.SCREEN_HEIGHT * 11 / 40,  Constans.SCREEN_WIDTH/6, Constans.SCREEN_WIDTH/6, "-", Constans.SCREEN_WIDTH / 10, textButtonColor, backgroundButtonColor));

        this.sceneManager = sceneManager;
        sensivity = 4;
        r = new Rect();
        paint = new Paint();

        sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(Constans.CURRENT_CONTEXT);

        editor = sharedPreferences.edit();
        sensivity = sharedPreferences.getInt("Sensivity", 4);

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {


        canvas.drawColor(Color.rgb(0, 100, 200));
        paint.setColor(Color.WHITE);
        paint.setTextSize(Constans.SCREEN_WIDTH / 5);
        drawCenterText(canvas, paint, "Settings", Constans.SCREEN_HEIGHT / 5);

        paint.setColor(Color.WHITE);
        paint.setTextSize(Constans.SCREEN_WIDTH / 11);
        canvas.drawText("Sensivity: " + sensivity, Constans.SCREEN_WIDTH / 18, Constans.SCREEN_HEIGHT * 61 / 180, paint);

        for (Button bu : buttons) {
            bu.draw(canvas);
        }


    }

    @Override
    public void terminate() {

    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (buttons.get(0).onClick(event)) {
                    if(sensivity<10){
                        sensivity++;
                        editor.putInt("Sensivity", sensivity);
                        editor.apply();
                    }
                }

                if (buttons.get(1).onClick(event)) {
                    if(sensivity>1){
                        sensivity--;
                        editor.putInt("Sensivity", sensivity);
                        editor.apply();
                    }
                }
      /*          if (back.contains((int) event.getX(), (int) event.getY())) {
                    if(sceneManager.getLastScene()==1)
                        sceneManager.setScene(1);
                    else if(sceneManager.getLastScene()==0)
                    sceneManager.setScene(0);

                }

               else if (sensAdd.contains((int) event.getX(), (int) event.getY()) && sensivity<10) {
                    sensivity++;
                    editor.putInt("Sensivity", sensivity);
                    editor.apply();

                }

                else if (sensSub.contains((int) event.getX(), (int) event.getY()) && sensivity > 1) {
                    sensivity--;
                    editor.putInt("Sensivity", sensivity);
                    editor.apply();

                }*/
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


}
