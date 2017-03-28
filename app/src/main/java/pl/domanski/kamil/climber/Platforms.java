package pl.domanski.kamil.climber;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

// Klasa w której zdefiniowane są parametry platform. Jeden obiekt tej klasy - jedna platforma

public class Platforms implements GameObject {

    public Rect rectangle;

    public int platformType;


    public int direction;

    public int howFar = 400;


    BitmapFactory bf = new BitmapFactory();
    Bitmap platform1 = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
            R.drawable.platform1);
    Bitmap platform2 = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
            R.drawable.grass);
    Bitmap platform3 = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
            R.drawable.spring);


    public Rect getRectangle() {

        return rectangle;
    }

    public void incrementY(float y) {
        rectangle.top += y;
        rectangle.bottom += y;

    }


    public Platforms(int platformHeight, int platformWidth, int startX, int startY, int platType) {


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
        //Rect.intersects(rectangle, player.getRectangle());
    }

    @Override
    public void draw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        if (platformType == 0) {
            paint.setColor(Color.BLUE);
            //  canvas.drawRect(rectangle, paint);
            canvas.drawBitmap(platform2, null, rectangle, paint);
        } else if (platformType == 2) {
            paint.setColor(Color.BLACK);
            canvas.drawBitmap(platform3, null, rectangle, paint);
            //  canvas.drawRect(rectangle, paint);

        } else {
            paint.setColor(Color.GREEN);
            canvas.drawBitmap(platform2, null, rectangle, paint);
            //canvas.drawRect(rectangle, paint);
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


    @Override
    public void update() {

    }

}
