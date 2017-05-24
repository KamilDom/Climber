package pl.domanski.kamil.climber.Scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

import pl.domanski.kamil.climber.Engine.Constans;
import pl.domanski.kamil.climber.Objects.Button;
import pl.domanski.kamil.climber.Objects.Player;

/**
 * Created by Kamil on 18.04.2017.
 */

public class GameOverScene implements Scene {


    public float yStart;
    private float windowSpeed;
    private Paint paint;
    public boolean gameover;
    public Player player;
    private float playerYSpeed;
    private float gameoverYPlayerSpeed;
    private Rect r;
    public int score;
    private SceneManager sceneManager;

    private int textButtonColor = Color.rgb(255, 255, 255);
    private int backgroundButtonColor = Color.rgb(0, 120, 0);
    private ArrayList<Button> buttons;



    public void setWindowSpeed(float windowSpeed) {
        this.windowSpeed = windowSpeed;
    }

    public void setGameover(boolean gameover) {
        this.gameover = gameover;
    }

    public GameOverScene(Player player, SceneManager sceneManager) {
        yStart = Constans.SCREEN_HEIGHT*11/10;
        windowSpeed=0;
        paint = new Paint();
        r=new Rect();
        gameover = false;
        this.player = player;
        playerYSpeed=0;
        gameoverYPlayerSpeed=0;
        this.sceneManager=sceneManager;

        buttons = new ArrayList<Button>();
        buttons.add(new Button((Constans.SCREEN_WIDTH - Constans.SCREEN_WIDTH * 24 / 45)/2, Constans.SCREEN_HEIGHT * 41 / 90, Constans.SCREEN_WIDTH * 24 / 45, Constans.SCREEN_HEIGHT *8/ 90, "Retry", Constans.SCREEN_WIDTH *8/ 90, textButtonColor, backgroundButtonColor));
        buttons.add(new Button((Constans.SCREEN_WIDTH - Constans.SCREEN_WIDTH * 24 / 45)/2, Constans.SCREEN_HEIGHT * 109 / 180, Constans.SCREEN_WIDTH * 24 / 45, Constans.SCREEN_HEIGHT *8/ 90, "Menu", Constans.SCREEN_WIDTH *8/ 90, textButtonColor, backgroundButtonColor));

    }

    @Override
    public void update() {



        if (gameover&&yStart>Constans.SCREEN_HEIGHT*3/18-windowSpeed){
            yStart+=windowSpeed;
            playerYSpeed=windowSpeed;
        }

        buttons.get(0).setStartY((int) yStart+Constans.SCREEN_HEIGHT*25/80);
        buttons.get(1).setStartY((int) yStart+Constans.SCREEN_HEIGHT*17/40);
    }


    @Override
    public void draw(Canvas canvas) {

       if(yStart<Constans.SCREEN_HEIGHT+10){
          //  paint.setColor(Color.rgb( 0, 100 , 200));
          paint.setColor(Color.argb( 200,0, 100 , 200));
            canvas.drawRect(Constans.SCREEN_WIDTH/7, yStart, Constans.SCREEN_WIDTH*6/7, yStart+Constans.SCREEN_HEIGHT*12/18, paint);
            paint.setStrokeWidth(5);
            paint.setColor(Color.rgb(0,0,0));
            canvas.drawLine(Constans.SCREEN_WIDTH/7, yStart, Constans.SCREEN_WIDTH*6/7, yStart,paint);
            canvas.drawLine(Constans.SCREEN_WIDTH*6/7, yStart, Constans.SCREEN_WIDTH*6/7, yStart+Constans.SCREEN_HEIGHT*12/18,paint);
            canvas.drawLine(Constans.SCREEN_WIDTH/7, yStart+Constans.SCREEN_HEIGHT*12/18, Constans.SCREEN_WIDTH*6/7, yStart+Constans.SCREEN_HEIGHT*12/18,paint);
            canvas.drawLine(Constans.SCREEN_WIDTH/7, yStart, Constans.SCREEN_WIDTH/7, yStart+Constans.SCREEN_HEIGHT*12/18,paint);

           paint.setTextSize(Constans.SCREEN_HEIGHT/17);
           paint.setTypeface(Constans.font);
           paint.setColor(Color.WHITE);
           canvas.drawText("Game over",(getCenterCorinate(canvas, "Game over")[0]), (float) (yStart+Constans.SCREEN_HEIGHT*3/40),paint);

           paint.setTextSize(Constans.SCREEN_HEIGHT/20);
           canvas.drawText("Your score:",(getCenterCorinate(canvas, "Your score:")[0]), (float) (yStart+Constans.SCREEN_HEIGHT*8/40),paint);
           canvas.drawText(""+score,(getCenterCorinate(canvas, ""+score)[0]), (float) (yStart+Constans.SCREEN_HEIGHT*11/40),paint);

           for (Button bu : buttons) {
               bu.draw(canvas);
           }

        }


    }

    @Override
    public void terminate() {

    }

    @Override
    public void recieveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (buttons.get(0).onClick(event)) {
                sceneManager.resetGame();
                sceneManager.gameplayScene.platformsManager.reset();
                sceneManager.setScene(1);
            }

            else if (buttons.get(1).onClick(event)) {
                sceneManager.setScene(0);
            }

        }



    }

    public void reset(){
        yStart = Constans.SCREEN_HEIGHT*11/10;
        windowSpeed=0;
        gameover = false;
        playerYSpeed=0;
        gameoverYPlayerSpeed=0;
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
