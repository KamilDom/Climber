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

import pl.domanski.kamil.climber.Engine.Constans;
import pl.domanski.kamil.climber.Engine.GamePanel;
import pl.domanski.kamil.climber.Engine.MainActivity;

/**
 * Created by Kamil on 03.04.2017.
 */
public class SettingsScene implements Scene {


    private static int sensivity;
    private final SceneManager sceneManager;
    private Rect r;
    private Rect sensAdd;
    private Rect sensSub;
    private Paint paint;
    private Rect back;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SettingsScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        sensivity = 4;
        r = new Rect();
        paint = new Paint();
        sensAdd = new Rect(Constans.SCREEN_WIDTH * 19 / 24, Constans.SCREEN_HEIGHT / 6 , Constans.SCREEN_WIDTH * 23 / 24, Constans.SCREEN_HEIGHT / 6 + Constans.SCREEN_HEIGHT*3/40);
        sensSub = new Rect(Constans.SCREEN_WIDTH * 14 / 24, Constans.SCREEN_HEIGHT / 6 , Constans.SCREEN_WIDTH * 18 / 24, Constans.SCREEN_HEIGHT / 6 + Constans.SCREEN_HEIGHT*3/40);
        back = new Rect(Constans.SCREEN_WIDTH * 3 / 5, Constans.SCREEN_HEIGHT / 4 + Constans.SCREEN_HEIGHT * 3 / 10,
                       Constans.SCREEN_WIDTH * 9 / 10, Constans.SCREEN_HEIGHT / 4 + Constans.SCREEN_HEIGHT * 4 / 10);

        sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(Constans.CURRENT_CONTEXT);

        editor = sharedPreferences.edit();
        sensivity = sharedPreferences.getInt("Sensivity", 4);

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);

        paint.setColor(Color.DKGRAY);
        paint.setTextSize(Constans.SCREEN_WIDTH / 7);
        drawCenterText(canvas, paint, "Settings:", Constans.SCREEN_HEIGHT / 5);

        paint.setColor(Color.WHITE);
        paint.setTextSize(Constans.SCREEN_WIDTH / 10);
        //drawCenterText(canvas, paint, "ff", Constans.SCREEN_HEIGHT/4 + Constans.SCREEN_HEIGHT/10);
        canvas.drawText("Sensivity:    " + sensivity, Constans.SCREEN_WIDTH / 10, Constans.SCREEN_HEIGHT * 3 / 10, paint);

        paint.setColor(Color.DKGRAY);
        canvas.drawRect(back, paint);
        canvas.drawRect(sensAdd, paint);
        canvas.drawRect(sensSub, paint);
    }

    @Override
    public void terminate() {

    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (back.contains((int) event.getX(), (int) event.getY())) {
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

    public static int getSensivity() {
        return sensivity;
    }
}
