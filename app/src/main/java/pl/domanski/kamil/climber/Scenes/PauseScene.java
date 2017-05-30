package pl.domanski.kamil.climber.Scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

import pl.domanski.kamil.climber.Engine.Constans;
import pl.domanski.kamil.climber.Objects.Button;

// Klasa do wyswietlania i zarzadzania menu pauzy

public class PauseScene implements Scene {

    private SceneManager sceneManager;
    private Paint paint;
    private Rect r;
    private ArrayList<Button> buttons;

    private int textButtonColor = Color.rgb(255, 255, 255);
    private int backgroundButtonColor = Color.rgb(0, 120, 0);
    private ConfirmScene confirmScene;
    public boolean showConfirmScene = false;

    public PauseScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        paint = new Paint();
        r=new Rect();

        buttons = new ArrayList<Button>();
        buttons.add(new Button((Constans.SCREEN_WIDTH - Constans.SCREEN_WIDTH * 24 / 45)/2, Constans.SCREEN_HEIGHT * 11 / 36, Constans.SCREEN_WIDTH * 24 / 45, Constans.SCREEN_HEIGHT *8/ 90, "Resume", Constans.SCREEN_WIDTH *8/ 90, textButtonColor, backgroundButtonColor));
        buttons.add(new Button((Constans.SCREEN_WIDTH - Constans.SCREEN_WIDTH * 24 / 45)/2, Constans.SCREEN_HEIGHT * 41 / 90, Constans.SCREEN_WIDTH * 24 / 45, Constans.SCREEN_HEIGHT *8/ 90, "Settings", Constans.SCREEN_WIDTH *8/ 90, textButtonColor, backgroundButtonColor));
        buttons.add(new Button((Constans.SCREEN_WIDTH - Constans.SCREEN_WIDTH * 24 / 45)/2, Constans.SCREEN_HEIGHT * 109 / 180, Constans.SCREEN_WIDTH * 24 / 45, Constans.SCREEN_HEIGHT *8/ 90, "Menu", Constans.SCREEN_WIDTH *8/ 90, textButtonColor, backgroundButtonColor));

        confirmScene = new ConfirmScene("Are you sure ", "you want to exit?");


    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

     //   paint.setColor(Color.rgb( 0, 100 , 200));
        paint.setColor(Color.argb( 200,0, 100 , 200));
        canvas.drawRect(Constans.SCREEN_WIDTH/7, Constans.SCREEN_HEIGHT*3/18, Constans.SCREEN_WIDTH*6/7, Constans.SCREEN_HEIGHT*15/18, paint);
        paint.setStrokeWidth(5);
        paint.setColor(Color.rgb(0,0,0));
        canvas.drawLine(Constans.SCREEN_WIDTH/7, Constans.SCREEN_HEIGHT*3/18, Constans.SCREEN_WIDTH*6/7,Constans.SCREEN_HEIGHT*3/18,paint);
        canvas.drawLine(Constans.SCREEN_WIDTH*6/7, Constans.SCREEN_HEIGHT*3/18, Constans.SCREEN_WIDTH*6/7,Constans.SCREEN_HEIGHT*15/18,paint);
        canvas.drawLine(Constans.SCREEN_WIDTH/7, Constans.SCREEN_HEIGHT*15/18, Constans.SCREEN_WIDTH*6/7,Constans.SCREEN_HEIGHT*15/18,paint);
        canvas.drawLine(Constans.SCREEN_WIDTH/7, Constans.SCREEN_HEIGHT*3/18, Constans.SCREEN_WIDTH/7,Constans.SCREEN_HEIGHT*15/18,paint);
        paint.setTextSize(Constans.SCREEN_HEIGHT/17);
        paint.setTypeface(Constans.font);
        paint.setColor(Color.WHITE);
        canvas.drawText("Pause",(getCenterCorinate(canvas, "Pause")[0]), (float) (Constans.SCREEN_HEIGHT*29/120),paint);

        for (Button bu : buttons) {
            bu.draw(canvas);
        }
        if(showConfirmScene){
            confirmScene.draw(canvas);
        }

    }

    @Override
    public void terminate() {

    }

    @Override
    public void recieveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(!showConfirmScene){
                if (buttons.get(0).onClick(event)) {
                    SceneManager.PAUSE = false;
                } else if (buttons.get(1).onClick(event)) {
                    sceneManager.setScene(3);
                } else if (buttons.get(2).onClick(event)){
                    showConfirmScene = true;
                }
            } else if(confirmScene.buttons.get(0).onClick(event)){
                showConfirmScene=false;
            } else if(confirmScene.buttons.get(1).onClick(event)){
                sceneManager.setScene(0);
            }

        }

    }

    private float[] getCenterCorinate(Canvas canvas, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;

        float[] cordinate ={x,y};
        return cordinate;

    }
}
