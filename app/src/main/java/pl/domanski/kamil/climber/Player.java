package pl.domanski.kamil.climber;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Kamil on 19.03.2017.
 */

public class Player implements GameObject {

    private Rect rectangle;
    private int color;

    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;
    private AnimationManager animManager;

    private int jumpVector;
    private int jumpVectorLenght;
    private long lastJumpVector;
    private float VectorTime;
    public int jumpState = 0; // 1 fall

    public int p = 0;

    public Rect getRectangle() {
        return rectangle;
    }

    public Player(Rect rectangle, int color, int JumpVectorLenght) {
        this.rectangle = rectangle;
        this.color = color;
        this.jumpVectorLenght = JumpVectorLenght;
        jumpVector = jumpVectorLenght;


        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
                R.drawable.alienbluey);
        Bitmap walk1 = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
                R.drawable.alienblue_walk1y);
        Bitmap walk2 = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
                R.drawable.alienblue_walk2y);


        idle = new Animation(new Bitmap[]{idleImg}, 2);
        walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.4f);


        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk1.getHeight(), m, false);

        walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 0.4f);


        animManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});

    }

    @Override
    public void draw(Canvas canvas) {


        animManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {

        animManager.update();
    }

    public void update(Point point) {
        float oldLeft = rectangle.left;

        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);

        int state = 0;
        if (rectangle.left - oldLeft > 3)
            state = 1;
        else if (rectangle.left - oldLeft < -3)
            state = 2;

        animManager.playAnim(state);
        animManager.update();

    }

    public void jump() {
        jumpState = 0;
        jumpVector = jumpVectorLenght;

    }

    public void jump(int extraJump) {
        jumpState = 0;
        jumpVector = extraJump;
    }


    public int jumpVector() {


        if (jumpState == 0) {
            jumpVector--;

            if (jumpVector == 0) {
                jumpState = 1;
            }

            return jumpVector;

        }

        else if (jumpState == 1) {
            jumpVector++;
            if (jumpVector > 200) return jumpVector;
            return -jumpVector;
        }

        return jumpVector;
    }


}

