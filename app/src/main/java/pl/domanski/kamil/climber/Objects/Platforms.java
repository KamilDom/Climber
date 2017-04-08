package pl.domanski.kamil.climber.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
    public int howFar = 400;


    BitmapFactory bf = new BitmapFactory();

    Bitmap platform1 = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
            R.drawable.grass);
    Bitmap platform2 = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
            R.drawable.plat2);

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


        if (player.getRectangle().bottom > rectangle.top - 50
                && player.getRectangle().bottom < rectangle.top + rectangle.height() / 2
                && player.getRectangle().left < rectangle.right
                && player.getRectangle().right > rectangle.left)
            return true;

        else return false;

    }

    @Override
    public void draw(Canvas canvas) {


        paint.setColor(Color.RED);
        if (platformType == 0) {
          //  canvas.drawBitmap(platform2, null, rectangle, paint);
            canvas.drawBitmap(resizedPlatform1,rectangle.left, rectangle.top ,paint);
        } else if (platformType == 2) {
            //canvas.drawBitmap(platform3, null, rectangle, paint);
            canvas.drawBitmap(resizedPlatform2,rectangle.left, rectangle.top ,paint);
        } else {
          //  canvas.drawBitmap(platform2, null, rectangle, paint);
            canvas.drawBitmap(resizedPlatform1,rectangle.left, rectangle.top ,paint);
            if (!SceneManager.PAUSE && !SceneManager.GAMEOVER)
                movingPlatform();
        }
    }

    private void movingPlatform() {

        if (direction == 0) {
            rectangle.right += 6;
            rectangle.left += 6;
            howFar -= 6;
            if (howFar < 0)
                direction = 1;
        } else {
            rectangle.right -= 6;
            rectangle.left -= 6;
            howFar += 6;
            if (howFar > 400)
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

    }

}
