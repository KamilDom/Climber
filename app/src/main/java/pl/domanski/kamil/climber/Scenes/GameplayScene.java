package pl.domanski.kamil.climber.Scenes;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;

import pl.domanski.kamil.climber.Engine.Constans;
import pl.domanski.kamil.climber.Objects.Bird;
import pl.domanski.kamil.climber.Objects.PlatformsManager;
import pl.domanski.kamil.climber.Objects.Player;
import pl.domanski.kamil.climber.R;


//Główna klasa gameplayu - sterowanie cala rozgrywka


public class GameplayScene implements Scene {

    private Rect r = new Rect();


    private SceneManager sceneManager;
    protected PauseScene pauseScene;
    private GameOverScene gameOverScene;
    private float gameoverYSpeed;
    private float gameoverYPlayerSpeed;
    private Paint paint;
    private float yScore;

    public Player player;
    private Bird bird;


    public PlatformsManager platformsManager;
    BitmapFactory bf = new BitmapFactory();
    BitmapFactory.Options op = new BitmapFactory.Options();
    Bitmap sky;

    public boolean vibration;
    Vibrator v = (Vibrator) Constans.CURRENT_CONTEXT.getSystemService(Context.VIBRATOR_SERVICE);

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public GameplayScene(SceneManager sceneManager) {
        gameoverYSpeed = 0;
        gameoverYPlayerSpeed = 0;
        yScore = 0;
        op.inPreferredConfig = Bitmap.Config.ARGB_8888;

        sky = getResizedBitmap(bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(), R.drawable.sky3), Constans.SCREEN_WIDTH, Constans.SCREEN_HEIGHT);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Constans.CURRENT_CONTEXT);
        editor = sharedPreferences.edit();
        vibration = sharedPreferences.getBoolean("Vibration", false);

        sceneManager.PAUSE = false;
        this.sceneManager = sceneManager;
        paint = new Paint();
        pauseScene = new PauseScene(sceneManager);
        platformsManager = new PlatformsManager((int) (Constans.SCREEN_WIDTH * 0.2), (int) (Constans.SCREEN_HEIGHT / 24));

        player = new Player(Constans.SCREEN_WIDTH / 2, Constans.SCREEN_HEIGHT * 4 / 5, Constans.SCREEN_WIDTH / 6,
                Constans.SCREEN_HEIGHT / 8, sharedPreferences.getInt("Sensivity", 4), platformsManager);


        bird = new Bird((int) (Math.random() * Constans.SCREEN_WIDTH), 0, Constans.SCREEN_WIDTH / 145, 1, Constans.SCREEN_WIDTH * 5 / 24, Constans.SCREEN_HEIGHT / 13);
        gameOverScene = new GameOverScene(player, sceneManager);

    }

    public void reset() {


        player.xPos = Constans.SCREEN_WIDTH / 2;
        player.yPos = Constans.SCREEN_HEIGHT * 4 / 5;
        SceneManager.PAUSE = false;
        pauseScene.showConfirmScene = false;
        player.jump((Constans.SCREEN_HEIGHT / 48));
        bird.exist = false;
        gameoverYSpeed = 0;
        gameoverYPlayerSpeed = 0;
        yScore = 0;
        SceneManager.GAMEOVER = false;
        gameOverScene.reset();
        player.gameover = false;
        platformsManager.reset();

    }

    @Override
    public void terminate() {

        SceneManager.GAMEOVER = true;

    }


    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:


                if (gameOverScene.gameover) {
                    gameOverScene.recieveTouch(event);
                }

                if (SceneManager.PAUSE)
                    pauseScene.recieveTouch(event);
                break;


        }
    }


    @Override
    public void draw(Canvas canvas) {

        // canvas.drawColor(Color.rgb(91, 173, 235));
        canvas.drawBitmap(sky, 0, 0, paint);

        platformsManager.draw(canvas);
        bird.draw(canvas);
        player.draw(canvas);
        paint.setColor(Color.YELLOW);
        paint.setTextSize(Constans.SCREEN_WIDTH / 17);
        paint.setTypeface(Constans.font);
        canvas.drawText("Score: " + String.valueOf(platformsManager.score), (float) (Constans.SCREEN_WIDTH / 21.6), (float) (-yScore + Constans.SCREEN_HEIGHT / 30), paint);


        if (sceneManager.PAUSE && !SceneManager.GAMEOVER) {
            pauseScene.draw(canvas);

        }

        if (gameOverScene.gameover) {
            gameOverScene.draw(canvas);
        }


    }


    @Override
    public void update() {

        if (!SceneManager.GAMEOVER && !sceneManager.PAUSE) {


            if (platformsManager.playerCollide(player) && player.jumpState == 1) {
                player.yPos = platformsManager.getPlatformColideTop() - player.playerHeight * 3 / 4;

                if (platformsManager.getPlatformType(platformsManager.indexColide) == 2) {
                    player.jump(Constans.SCREEN_HEIGHT / 25);
                    platformsManager.platforms.get(platformsManager.indexColide).playAnim();
                    if (vibration)
                        v.vibrate(1);
                } else {
                    player.jump(Constans.SCREEN_HEIGHT / 48);
                    if (vibration)
                        v.vibrate(1);
                }

            }

            if (player.jumpState == 0 && player.yPos <= Constans.SCREEN_HEIGHT * 7 / 24)
                bird.incrementY(player.getJumpVector());


            platformsManager.playerCollide(player);
            player.update();
            platformsManager.update();

            bird.update();

            if (bird.exist) {
                if (player.getPlayerRect().intersect(bird.getBirdRect().left + bird.getBirdRect().width() / 5, bird.getBirdRect().top + bird.getBirdRect().height() / 5,
                        bird.getBirdRect().right - bird.getBirdRect().width() / 5, bird.getBirdRect().bottom - bird.getBirdRect().height() / 5)) {
                    if (player.getPlayerRect().bottom < bird.getBirdRect().bottom) {
                        player.jump(Constans.SCREEN_HEIGHT / 48);
                        if (vibration)
                            v.vibrate(1);
                        platformsManager.score += 200;
                        bird.dead = true;
                        bird.playDeadScore();
                    } else {
                        SceneManager.GAMEOVER = true;
                        gameOverScene.gameover = true;
                        player.gameover = true;
                        gameOverScene.score = platformsManager.score;
                        setHighscore();
                    }

                }
            } else if (!bird.exist && platformsManager.score > 100 && (int) (Math.random() * 700) == 0) {
                bird.reset();

            }

            if (player.yPos > Constans.SCREEN_HEIGHT - player.playerHeight / 2) {
                SceneManager.GAMEOVER = true;
                gameOverScene.setGameover(true);
                player.gameover = true;
                gameoverYSpeed = (int) player.jumpVector();
                gameOverScene.score = platformsManager.score;
                setHighscore();
            }
        } else if (gameOverScene.gameover) {
            player.update();
            if (platformsManager.platforms.get(platformsManager.platforms.size() - 1).rectangle.bottom > -Constans.SCREEN_HEIGHT / 2) {
                platformsManager.update();
                bird.update();

                if (platformsManager.platforms.get(platformsManager.platforms.size() - 1).getRectangle().bottom > -Constans.SCREEN_HEIGHT / 2) {
                    platformsManager.incrementY(gameoverYSpeed);
                }


                if (bird.getBirdRect().bottom > 0) {
                    bird.incrementY(gameoverYSpeed);
                }

                gameoverYSpeed -= 1;
                gameOverScene.setWindowSpeed(gameoverYSpeed);


                if (player.yPos > Constans.SCREEN_HEIGHT * 1 / 10) {
                    player.yPos -= gameoverYPlayerSpeed;
                    gameoverYPlayerSpeed += 0.5;
                } else
                    gameoverYPlayerSpeed = 0;
            } else {

                if (player.yPos < Constans.SCREEN_HEIGHT) {
                    player.yPos += gameoverYPlayerSpeed;
                    gameoverYPlayerSpeed += 3;
                }
                gameOverScene.update();
            }

            if (gameOverScene.yStart < Constans.SCREEN_HEIGHT + 10) {
                yScore -= gameoverYSpeed / (Constans.SCREEN_HEIGHT / 100);
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

        float[] cordinate = {x, y};
        return cordinate;

    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public void setHighscore() {
        for (int i = 0; i < sceneManager.highscoreScene.highscore.length; i++) {
            if (platformsManager.score > sceneManager.highscoreScene.highscore[i]) {
                for (int k = sceneManager.highscoreScene.highscore.length - 2; k >= i; k--) {
                    sceneManager.highscoreScene.highscore[k + 1] = sceneManager.highscoreScene.highscore[k];
                }
                sceneManager.highscoreScene.highscore[i] = platformsManager.score;
                i = 20;
            }
        }
        sceneManager.highscoreScene.setHighscore();


    }

}