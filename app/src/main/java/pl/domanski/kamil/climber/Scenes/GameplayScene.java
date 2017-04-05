package pl.domanski.kamil.climber.Scenes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import pl.domanski.kamil.climber.Constans;
import pl.domanski.kamil.climber.OrientationData;
import pl.domanski.kamil.climber.PlatformsManager;
import pl.domanski.kamil.climber.Player;
import pl.domanski.kamil.climber.R;
import pl.domanski.kamil.climber.SceneManager;
import pl.domanski.kamil.climber.Scenes.Scene;

//Główna klasa gameplayu

public class GameplayScene implements Scene {

    private Rect r = new Rect();
    private Rect background;

    protected SceneManager sceneManager;

    BitmapFactory bf = new BitmapFactory();
    Bitmap sky = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
            R.drawable.sky2);


    Paint paint;

    private Player player;
    private Point playerPoint;
    private Point playerPoint2;

    private PlatformsManager platformsManager;


    private boolean gameOver = false;
    private boolean pause = false;
    private long gameOverTime;

    private OrientationData orientationData;
    private long frameTime;


    public GameplayScene(SceneManager sceneManager) {

        this.sceneManager = sceneManager;
        paint = new Paint();
        player = new Player(new Rect(100, 100, 250, 250), Color.rgb(200, 50, 50), 40);
        playerPoint = new Point(Constans.SCREEN_WIDTH / 2, Constans.SCREEN_HEIGHT * 4 / 5);
        background = new Rect(0, 0, Constans.SCREEN_WIDTH, Constans.SCREEN_HEIGHT);
        player.update(playerPoint);

        Log.d("new", "GampleyScene");
        platformsManager = new PlatformsManager((int) (Constans.SCREEN_WIDTH * 0.23), 80);

        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
        orientationData.newGame();

    }

    public void reset() {
        platformsManager.reset();
        playerPoint.x = Constans.SCREEN_WIDTH / 2;
        playerPoint.y = 3 * Constans.SCREEN_HEIGHT / 4;
        player.update(playerPoint);
        orientationData.newGame();


    }

    @Override
    public void terminate() {

        gameOver = true;

    }


    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (gameOver && System.currentTimeMillis() - gameOverTime >= 500) {
                    sceneManager.setScene(0);
                    reset();
                    gameOver = false;
                    orientationData.newGame();

                }


                break;

        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.rgb(91, 173, 235));



        platformsManager.draw(canvas);
        player.draw(canvas);
        paint.setColor(Color.YELLOW);
        paint.setTextSize(50);
        canvas.drawText("Score: " + String.valueOf(platformsManager.score), 50, 50, paint);

        if (gameOver) {

            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas, paint, "Game Over");

        }

        if (sceneManager.PAUSESTATE && !gameOver){
            paint.setColor(Color.argb(200, 0, 198 , 50));
            canvas.drawRect(Constans.SCREEN_WIDTH/9, Constans.SCREEN_HEIGHT/9, Constans.SCREEN_WIDTH*8/9, Constans.SCREEN_HEIGHT*8/9, paint);
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas, paint, "Pause");
        }


    }

    @Override
    public void update() {

        if(pause!=sceneManager.PAUSESTATE && pause==false){
            pause=sceneManager.PAUSESTATE;
            playerPoint2.x=playerPoint.x;
            playerPoint2.y=playerPoint.y;

        }

        else if(pause!=sceneManager.PAUSESTATE && pause==true){
            pause=sceneManager.PAUSESTATE;
            playerPoint.x=playerPoint2.x;
            playerPoint.y=playerPoint2.y;
        }
        if (!gameOver && !sceneManager.PAUSESTATE) {

            if (frameTime < Constans.INIT_TIME)
                frameTime = Constans.INIT_TIME;
            int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();

            if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
                float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];
                float xSpeed = 2 * roll * Constans.SCREEN_WIDTH / 550f;


                if (platformsManager.playerCollide(player) && player.jumpState == 1) {
                    playerPoint.y = platformsManager.getPlatformColideTop() - player.getRectangle().height() / 4;
                    if (platformsManager.getPlatformType(platformsManager.indexColide) == 2)
                        player.jump(120);
                    else
                        player.jump();
                }

                if (player.jumpState == 1)
                    playerPoint.y -= player.jumpVector();
                else {
                    if (playerPoint.y > Constans.SCREEN_HEIGHT / 3) {
                        playerPoint.y -= player.jumpVector();

                    } else {
                        platformsManager.incrementY(player.jumpVector());

                    }
                }

                playerPoint.x += Math.abs(xSpeed * elapsedTime) > 4 ? xSpeed * elapsedTime : 0;


                if (playerPoint.x < -player.getRectangle().width() / 4)
                    playerPoint.x = Constans.SCREEN_WIDTH + player.getRectangle().width() / 4;
                else if (playerPoint.x > Constans.SCREEN_WIDTH + player.getRectangle().width() / 4)
                    playerPoint.x = -player.getRectangle().width() / 4;


                platformsManager.playerCollide(player);

                if (playerPoint.y < 0) playerPoint.y = 0;

            }


            player.update(playerPoint);
            platformsManager.update();
            if (playerPoint.y > Constans.SCREEN_HEIGHT) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();

            }
        }
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);

    }

}