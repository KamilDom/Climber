package pl.domanski.kamil.climber.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import pl.domanski.kamil.climber.Animations.Animation;
import pl.domanski.kamil.climber.Animations.AnimationManager;
import pl.domanski.kamil.climber.Engine.Constans;
import pl.domanski.kamil.climber.Engine.MainActivity;
import pl.domanski.kamil.climber.GameObject;
import pl.domanski.kamil.climber.R;
import pl.domanski.kamil.climber.Scenes.SceneManager;


public class Bird implements GameObject {

    private int xPos;
    public int yPos;
    private int speed;
    private int direction;
    private int birdWidth;
    private int birdHeight;
    public boolean dead;
    private int deadSpeed;
    public boolean playDeadScore = false;
    private int xPosDeadScore;
    private float yPosDeadScore;
    private long startDeadScore;
    private long deadScoreLength = 1000;
    private float deadScoreTextSize = 30;

    private Animation birdLeft;
    private Animation birdRight;
    private Animation deadBirdLeft;
    private Animation deadBirdRight;
    private Animation warningAnim;
    private AnimationManager animManager;
    private AnimationManager warning;

    private Paint paint;

    BitmapFactory bf = new BitmapFactory();
    private Bitmap bird1 = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(), R.drawable.bird1);
    private Bitmap bird2 = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(), R.drawable.bird2);
    private Bitmap bird3 = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(), R.drawable.dead_bird);
    private Bitmap warningSign = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(), R.drawable.warning);
    private Bitmap blank = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(), R.drawable.blank);

    public boolean exist = false;

    public Bird(int xPos, int yPos, int speed, int direction, int birdWidth, int birdHeight) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;
        this.direction = direction;
        this.birdWidth = birdWidth;
        this.birdHeight = birdHeight;
        paint = new Paint();
        paint.setTypeface(Constans.font);
        paint.setColor(Color.YELLOW);
        bird1 = getResizedBitmap(bird1, birdWidth, birdHeight);
        bird2 = getResizedBitmap(bird2, birdWidth, birdHeight);
        bird3 = getResizedBitmap(bird3, birdWidth, birdHeight);

        warningSign = getResizedBitmap(warningSign, Constans.SCREEN_WIDTH / 20, Constans.SCREEN_HEIGHT / 15);

        birdRight = new Animation(new Bitmap[]{bird1, bird2}, 0.3f);
        deadBirdRight = new Animation(new Bitmap[]{bird3}, 1f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        bird1 = Bitmap.createBitmap(bird1, 0, 0, bird1.getWidth(), bird1.getHeight(), m, false);
        bird2 = Bitmap.createBitmap(bird2, 0, 0, bird2.getWidth(), bird2.getHeight(), m, false);
        bird3 = Bitmap.createBitmap(bird3, 0, 0, bird3.getWidth(), bird3.getHeight(), m, false);

        birdLeft = new Animation(new Bitmap[]{bird1, bird2}, 0.3f);
        deadBirdLeft = new Animation(new Bitmap[]{bird3}, 1f);

        warningAnim = new Animation(new Bitmap[]{warningSign, blank}, 0.9f);

        animManager = new AnimationManager(new Animation[]{birdLeft, birdRight, deadBirdLeft, deadBirdRight});
        warning = new AnimationManager(new Animation[]{warningAnim});
    }

    @Override
    public void draw(Canvas canvas) {


        if (exist) {
            if (yPos < 0){
                if(!SceneManager.GAMEOVER)
                warning.draw(canvas, Constans.SCREEN_WIDTH / 10, Constans.SCREEN_HEIGHT / 19);
            }


            animManager.draw(canvas, xPos, yPos);
        }

        if (playDeadScore) {
            paint.setTextSize(deadScoreTextSize);
            deadScoreTextSize+=Constans.SCREEN_HEIGHT/800;
            canvas.drawText("+200", xPosDeadScore, yPosDeadScore, paint);
            yPosDeadScore-=Constans.SCREEN_HEIGHT/400;
        }




    }

    @Override
    public void update() {

        if (playDeadScore &&  System.currentTimeMillis() - deadScoreLength > startDeadScore) {
            playDeadScore = false;
        }

        if (exist) {
            if (direction == 0) {
                xPos += speed;
                if (!dead) {
                    if (xPos + birdWidth >= Constans.SCREEN_WIDTH) {
                        direction = 1;
                    }
                }
            } else if (direction == 1) {
                xPos -= speed;
                if (!dead) {
                    if (xPos <= 0) {
                        direction = 0;
                    }
                }
            }

            warning.playAnim(0);
            warning.update();
        }

        if (dead) {
            animManager.playAnim(direction + 2);
            yPos += deadSpeed;
            deadSpeed++;
        }
        else{
            animManager.playAnim(direction);
        }

        animManager.update();


        if (yPos > Constans.SCREEN_HEIGHT + 10) {
            exist = false;
            dead = true;
            yPos = 0;
        }


    }

    public void incrementY(float y) {
        yPos += y;
    }

    public void reset() {
        yPos = -Constans.SCREEN_HEIGHT / 2;
        xPos = (int) (Math.random() * (Constans.SCREEN_WIDTH - birdWidth));
        direction = (int) (Math.random() * 3 - 1);
        exist = true;
        dead = false;
        deadSpeed = 0;
    }


    public Rect getBirdRect() {
        return new Rect(xPos, yPos, xPos + birdWidth, yPos + birdHeight);
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


    public void playDeadScore() {
        xPosDeadScore = xPos;
        yPosDeadScore = yPos;
        deadScoreTextSize=30;
        startDeadScore = System.currentTimeMillis();
        playDeadScore = true;
    }
}
