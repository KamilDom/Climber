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

import pl.domanski.kamil.climber.Engine.Constans;
import pl.domanski.kamil.climber.Engine.OrientationData;
import pl.domanski.kamil.climber.Objects.PlatformsManager;
import pl.domanski.kamil.climber.Objects.Player;
import pl.domanski.kamil.climber.R;

//Główna klasa gameplayu

public class GameplayScene implements Scene {

    private Rect r = new Rect();
    private Rect background;
    private Rect settings;
    private Rect resume;
    private Rect menu;

    protected SceneManager sceneManager;

    BitmapFactory bf = new BitmapFactory();
    Bitmap sky = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
            R.drawable.sky2);




    Bitmap scaled = Bitmap.createScaledBitmap(sky, Constans.SCREEN_WIDTH, Constans.SCREEN_HEIGHT, true);

    Paint paint;

    private Player player;
    private Point playerPoint;
    private Point playerPoint2;

    private PlatformsManager platformsManager;


    private long gameOverTime;

    private OrientationData orientationData;
    private long frameTime;


    public GameplayScene(SceneManager sceneManager) {

        sceneManager.PAUSE = false;
        this.sceneManager = sceneManager;
        paint = new Paint();
        settings = new Rect(Constans.SCREEN_WIDTH/2, Constans.SCREEN_HEIGHT*2/7, Constans.SCREEN_WIDTH*2/3, Constans.SCREEN_HEIGHT*3/7);
        menu = new Rect(Constans.SCREEN_WIDTH/2, Constans.SCREEN_HEIGHT*4/7, Constans.SCREEN_WIDTH*2/3, Constans.SCREEN_HEIGHT*5/7);
        resume = new Rect(Constans.SCREEN_WIDTH/2, Constans.SCREEN_HEIGHT*6/7, Constans.SCREEN_WIDTH*2/3, Constans.SCREEN_HEIGHT*7/8);
        player = new Player(new Rect(100, 100, 250, 250), Color.rgb(200, 50, 50), 40);
        playerPoint = new Point(Constans.SCREEN_WIDTH / 2, Constans.SCREEN_HEIGHT * 4 / 5);
        background = new Rect(0, 0, Constans.SCREEN_WIDTH, Constans.SCREEN_HEIGHT);
        player.update(playerPoint);

        Log.d("new", "GampleyScene");
        platformsManager = new PlatformsManager((int) (Constans.SCREEN_WIDTH * 0.23), 80);

        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();


    }

    public void reset() {
        platformsManager.reset();
        playerPoint.x = Constans.SCREEN_WIDTH / 2;
        playerPoint.y = 3 * Constans.SCREEN_HEIGHT / 4;
        player.update(playerPoint);



    }

    @Override
    public void terminate() {

        SceneManager.GAMEOVER = true;

    }


    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (SceneManager.GAMEOVER && System.currentTimeMillis() - gameOverTime >= 500) {
                    sceneManager.setScene(0);
                    reset();
                    SceneManager.GAMEOVER = false;
                }

                else if (settings.contains((int) event.getX(), (int) event.getY()) && SceneManager.PAUSE ) {
                    sceneManager.setScene(2);
                }

                else if (menu.contains((int) event.getX(), (int) event.getY()) && SceneManager.PAUSE ) {
                    sceneManager.setScene(0);
                }

                else if (resume.contains((int) event.getX(), (int) event.getY()) && SceneManager.PAUSE ) {
                SceneManager.PAUSE = false;
                }



                    break;

        }
    }



    @Override
    public void draw(Canvas canvas) {
       canvas.drawColor(Color.rgb(91, 173, 235));
       // canvas.drawBitmap(scaled,0,0,paint);


        platformsManager.draw(canvas);
        player.draw(canvas);
        paint.setColor(Color.YELLOW);
        paint.setTextSize(50);
        canvas.drawText("Score: " + String.valueOf(platformsManager.score), 50, 50, paint);

        if (SceneManager.GAMEOVER) {
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            getCenterCorinate(canvas, "Game Over");

            canvas.drawText("Game Over",(getCenterCorinate(canvas, "Game Over")[0]),(getCenterCorinate(canvas, "Game Over")[1]) , paint);


        }

        if (sceneManager.PAUSE && !SceneManager.GAMEOVER){
            drawPauseMenu(canvas);

        }


    }

    private void drawPauseMenu(Canvas canvas) {

        paint.setColor(Color.rgb( 0, 100 , 200));
        canvas.drawRect(Constans.SCREEN_WIDTH/9, Constans.SCREEN_HEIGHT/9, Constans.SCREEN_WIDTH*8/9, Constans.SCREEN_HEIGHT*8/9, paint);
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        canvas.drawText("Pause",(getCenterCorinate(canvas, "Pause")[0]), (float) (Constans.SCREEN_HEIGHT*1.8/9),paint);
        canvas.drawRect(settings, paint);
        canvas.drawRect(menu, paint);
        canvas.drawRect(resume,paint);

    }

    @Override
    public void update() {


        if (!SceneManager.GAMEOVER && !sceneManager.PAUSE) {

            if (frameTime < Constans.INIT_TIME)
                frameTime = Constans.INIT_TIME;
            int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();

            float xSpeed;
            playerPoint.x -= Math.abs(orientationData.getOrientation() * elapsedTime)*sceneManager.getSensivity()/9 > 4 ? orientationData.getOrientation()*sceneManager.getSensivity()/9 * elapsedTime : 0;


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




                if (playerPoint.x < -player.getRectangle().width() / 4)
                    playerPoint.x = Constans.SCREEN_WIDTH + player.getRectangle().width() / 4;
                else if (playerPoint.x > Constans.SCREEN_WIDTH + player.getRectangle().width() / 4)
                    playerPoint.x = -player.getRectangle().width() / 4;


                platformsManager.playerCollide(player);

                if (playerPoint.y < 0) playerPoint.y = 0;




            player.update(playerPoint);
            platformsManager.update();
            if (playerPoint.y > Constans.SCREEN_HEIGHT) {
                SceneManager.GAMEOVER = true;
                gameOverTime = System.currentTimeMillis();

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
