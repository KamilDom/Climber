package pl.domanski.kamil.climber.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import pl.domanski.kamil.climber.Animations.Animation;
import pl.domanski.kamil.climber.Animations.AnimationManager;
import pl.domanski.kamil.climber.Engine.Constans;
import pl.domanski.kamil.climber.Engine.OrientationData;
import pl.domanski.kamil.climber.GameObject;
import pl.domanski.kamil.climber.R;

/**
 * Created by Kamil on 16.04.2017.
 */

public class Player implements GameObject {

    public int xPos;
    public int yPos;
    public int playerWidth;
    public int playerHeight;

    private int sensivity;

   /* private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;*/
   private Animation upRight;
    private Animation upLeft;
    private Animation downRight;
    private Animation downLeft;
    private AnimationManager animManager;
    int state;

    private PlatformsManager platformsManager;

    private OrientationData orientationData;

    private float jumpVector;

    public int jumpState = 0;

    public Player(int xPos, int yPos, int playerWidth, int playerHeight, int sensivity, PlatformsManager platformsManager) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.playerWidth = playerWidth;
        this.playerHeight = playerHeight;
        this.platformsManager = platformsManager;
        this.sensivity = sensivity;

        orientationData = new OrientationData();
        orientationData.register();

        BitmapFactory bf = new BitmapFactory();
  /*      Bitmap idleImg = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
                R.drawable.alienbluey);
        Bitmap walk1 = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
                R.drawable.alienblue_walk1y);
        Bitmap walk2 = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
                R.drawable.alienblue_walk2y);

        idleImg = getResizedBitmap(idleImg, playerWidth, playerHeight);
        walk1 = getResizedBitmap(walk1, playerWidth, playerHeight);
        walk2 = getResizedBitmap(walk2, playerWidth, playerHeight);

        idle = new Animation(new Bitmap[]{idleImg}, 2);
        walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.4f);


        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m, false);

        walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 0.4f);


        animManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});*/
        Bitmap upImg = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
                R.drawable.boy_rise);
        Bitmap downImg = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
                R.drawable.boy_fall);

        upImg = getResizedBitmap(upImg, playerWidth, playerHeight);
        downImg = getResizedBitmap(downImg, playerWidth, playerHeight);

        upRight = new Animation(new Bitmap[]{upImg}, 2);
        downRight = new Animation(new Bitmap[]{downImg}, 2);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        upImg = Bitmap.createBitmap(upImg, 0, 0, upImg.getWidth(), upImg.getHeight(), m, false);
        downImg = Bitmap.createBitmap(downImg, 0, 0, downImg.getWidth(), downImg.getHeight(), m, false);

        upLeft = new Animation(new Bitmap[]{upImg}, 2);
        downLeft = new Animation(new Bitmap[]{downImg}, 2);

        animManager = new AnimationManager(new Animation[]{upRight,downRight, upLeft,downLeft});
    }

    @Override
    public void draw(Canvas canvas) {

        animManager.draw(canvas, xPos, yPos);
    }

    @Override
    public void update() {
        float oldX = xPos;

        // TODO zoptymalizowac poruszanie dla różnych rozdzielczości

        xPos -= Math.abs(orientationData.getOrientation() * 10) * sensivity / (Constans.SCREEN_WIDTH / 120) > (Constans.SCREEN_WIDTH / 270) ?
                orientationData.getOrientation() * sensivity / (Constans.SCREEN_WIDTH / 120) * 10 : 0;

        if (xPos < -playerWidth / 2)
            xPos = Constans.SCREEN_WIDTH - playerWidth / 2;
        else if (xPos > Constans.SCREEN_WIDTH - playerWidth / 2)
            xPos = -playerWidth / 2;

        if (jumpState == 1) {
            yPos -= jumpVector();
        } else {
            if (yPos > Constans.SCREEN_HEIGHT * 7 / 24) {
                yPos -= jumpVector();
            } else {
                platformsManager.incrementY(jumpVector());
            }
        }

    /*    int state = 0;
        if (xPos - oldX > 3)
            state = 1;
        else if (xPos - oldX < -3)
            state = 2;*/

        if (xPos - oldX > 3 && jumpState == 0){
            state = 0;
        }

        else if (xPos - oldX > 3 && jumpState == 1){
            state = 1;
        }

        else if (xPos - oldX <-3 && jumpState == 0){
            state = 2;
        }

        else if (xPos - oldX <-3 && jumpState == 1){
            state = 3;
        }

        else if(state==0&&jumpState==1){
            state=1;
        }
        else if(state==1&&jumpState==0){
            state=0;
        }

        else if(state==2&&jumpState==1){
            state=3;
        }
        else if(state==3&&jumpState==0){
            state=2;
        }


        animManager.playAnim(state);
        animManager.update();
    }

    public void jump(int extraJump) {
        jumpState = 0;
        jumpVector = extraJump;

    }

    public float jumpVector() {


        if (jumpState == 0) {
            jumpVector -= ((float) Constans.SCREEN_HEIGHT / 1920);

            if (jumpVector < 0) {
                jumpState = 1;
            }

            return jumpVector;

        } else if (jumpState == 1) {
            jumpVector += ((float) Constans.SCREEN_HEIGHT / 1920);
            if (jumpVector > 200) return jumpVector;
            return -jumpVector;
        }

        return jumpVector;
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


    public float getJumpVector() {

        return jumpVector;
    }

    public Rect getPlayerRect(){
        return new Rect(xPos , yPos, xPos+playerWidth, yPos+playerHeight);
    }
}
