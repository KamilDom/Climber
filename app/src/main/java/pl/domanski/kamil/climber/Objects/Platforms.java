package pl.domanski.kamil.climber.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import pl.domanski.kamil.climber.Engine.Constans;
import pl.domanski.kamil.climber.R;
import pl.domanski.kamil.climber.Scenes.SceneManager;

// Klasa w której zdefiniowane są parametry platform. Jeden obiekt tej klasy - jedna platforma

public class Platforms implements pl.domanski.kamil.climber.GameObject {

    public Rect rectangle;

    public int platformType;
    private Paint paint;
    public int direction;
    public int howFar = (int)(Constans.SCREEN_WIDTH/2.7);
    private int movPlatinc = (int)(Constans.SCREEN_WIDTH/180);


    BitmapFactory bf = new BitmapFactory();

    Bitmap platform1 = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
            R.drawable.platform);
    Bitmap platform2 = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
            R.drawable.platform_super);

    Bitmap resizedPlatform1;
    Bitmap resizedPlatform2;


    public Rect getRectangle() {

        return rectangle;
    }

    public void incrementY(float y) {
        rectangle.top += y;
        rectangle.bottom += y;

    }


    public Platforms(int platformHeight, int platformWidth, int startX, int startY, int platType) {

        paint = new Paint();
        resizedPlatform1 = getResizedBitmap(platform1 ,platformWidth,platformHeight);
        resizedPlatform2 = getResizedBitmap(platform2 ,platformWidth,platformHeight);
        rectangle = new Rect(startX, startY, startX + platformWidth, startY + platformHeight);
        platformType = platType;


    }


    public boolean playerColide(Player player) {


        if (player.yPos+player.playerHeight > rectangle.top - 30
                && player.yPos+player.playerHeight < rectangle.top + rectangle.height() / 2
                && player.xPos < rectangle.right
                && player.xPos+player.playerWidth > rectangle.left)
            return true;

        else return false;

    }

    @Override
    public void draw(Canvas canvas) {



        if (platformType == 0) {
            canvas.drawBitmap(resizedPlatform1,rectangle.left, rectangle.top ,paint);
        } else if (platformType == 2) {
            canvas.drawBitmap(resizedPlatform2,rectangle.left, rectangle.top ,paint);
        } else {
            canvas.drawBitmap(resizedPlatform1,rectangle.left, rectangle.top ,paint);

        }
    }

    private void movingPlatform() {

        if (direction == 0) {
            rectangle.right += movPlatinc;
            rectangle.left += movPlatinc;
            howFar -= movPlatinc;
            if (howFar < 0)
                direction = 1;
        } else {
            rectangle.right -= movPlatinc;
            rectangle.left -= movPlatinc;
            howFar += movPlatinc;
            if (howFar > (int)(Constans.SCREEN_WIDTH/2.7))
                direction = 0;
        }


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

    @Override
    public void update() {
        if (!SceneManager.PAUSE && !SceneManager.GAMEOVER && platformType == 1)
            movingPlatform();
    }

}
