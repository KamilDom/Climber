package pl.domanski.kamil.climber.Scenes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.widget.Toast;


import java.util.ArrayList;

import pl.domanski.kamil.climber.Engine.Constans;
import pl.domanski.kamil.climber.Objects.Button;



// Klasa odpowiedzialna za menu

class MenuScene implements Scene {

    private Paint paint;
    private Rect r;
    private int textButtonColor = Color.rgb(255, 255, 255);
    private int backgroundButtonColor = Color.rgb(0, 120, 0);

    private SceneManager sceneManager;
    private ArrayList<Button> buttons;
    private ConfirmScene confirmScene;

    public boolean showConfirmScene = false;


    public MenuScene(SceneManager sceneManager) {
        buttons = new ArrayList<Button>();
        buttons.add(new Button(Constans.SCREEN_WIDTH / 5, Constans.SCREEN_HEIGHT * 5 / 20, Constans.SCREEN_WIDTH * 3 / 5, Constans.SCREEN_HEIGHT / 10, "New game", Constans.SCREEN_WIDTH / 10, textButtonColor, backgroundButtonColor));
        buttons.add(new Button(Constans.SCREEN_WIDTH / 5, Constans.SCREEN_HEIGHT * 8 / 20, Constans.SCREEN_WIDTH * 3 / 5, Constans.SCREEN_HEIGHT / 10, "Highscore", Constans.SCREEN_WIDTH / 10, textButtonColor, backgroundButtonColor));
        buttons.add(new Button(Constans.SCREEN_WIDTH / 5, Constans.SCREEN_HEIGHT * 11 / 20, Constans.SCREEN_WIDTH * 3 / 5, Constans.SCREEN_HEIGHT / 10, "Settings", Constans.SCREEN_WIDTH / 10, textButtonColor, backgroundButtonColor));
        buttons.add(new Button(Constans.SCREEN_WIDTH / 5, Constans.SCREEN_HEIGHT * 14 / 20, Constans.SCREEN_WIDTH * 3 / 5, Constans.SCREEN_HEIGHT / 10, "Exit", Constans.SCREEN_WIDTH / 10, textButtonColor, backgroundButtonColor));

        this.sceneManager = sceneManager;
        paint = new Paint();
        r = new Rect();

        confirmScene = new ConfirmScene("Are you sure ", "you want to exit?");

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawColor(Color.rgb(0, 100 , 200));
        paint.setColor(Color.WHITE);
        paint.setTextSize(Constans.SCREEN_WIDTH / 5);
        drawCenterText(canvas, paint, "Climber", Constans.SCREEN_HEIGHT / 5);


        paint.setColor(Color.DKGRAY);


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
                    sceneManager.resetGame();
                    sceneManager.setScene(1);
                } else if (buttons.get(1).onClick(event)) {
                    sceneManager.setScene(2);
                } else if (buttons.get(2).onClick(event)) {
                    sceneManager.setScene(3);
                } else if (buttons.get(3).onClick(event)) {
                    showConfirmScene=true;
                }
            }
            else if(confirmScene.buttons.get(0).onClick(event) ){
                showConfirmScene=false;
            }

            else if(confirmScene.buttons.get(1).onClick(event) ){
                System.exit(0);
            }



        }


    }

    public void drawCenterText(Canvas canvas, Paint paint, String text, int y) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        canvas.drawText(text, x, y, paint);
    }


}
