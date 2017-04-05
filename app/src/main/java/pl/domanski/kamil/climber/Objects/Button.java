package pl.domanski.kamil.climber.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import pl.domanski.kamil.climber.Engine.Constans;
import pl.domanski.kamil.climber.GameObject;
import pl.domanski.kamil.climber.R;


public class Button implements GameObject{

    private int startX;
    private int startY;
    private int buttonWidth;
    private int buttonHeight;
    private String buttonText;
    private int textSize;
    Paint paint;
    private Rect buttonRect;
    private int textColor;
    private int buttonColor;
    BitmapFactory bf = new BitmapFactory();

    Bitmap button = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
            R.drawable.menubutton);


    public Button(int startX, int startY, int buttonWidth, int buttonHeight, String buttonText, int textSize, int textColor, int buttonColor){
        this.startX=startX;
        this.startY=startY;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.buttonText = buttonText;
        this.textSize = textSize;
        this.buttonColor=buttonColor;
        this.textColor = textColor;
        buttonRect = new Rect(startX, startY, startX+buttonWidth, startY+buttonHeight);

        paint = new Paint();


    }

    @Override
    public void draw(Canvas canvas) {

        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(buttonColor);
      //  canvas.drawRect(startX, startY , startX+buttonWidth , startY+buttonHeight, paint);
        canvas.drawBitmap(button, null, new Rect(startX, startY , startX+buttonWidth , startY+buttonHeight), paint);
       // canvas.drawBitmap(button, startX,startY, paint);
        paint.setColor(textColor);
        canvas.drawText(buttonText,startX+(buttonWidth-paint.measureText(buttonText))/2,startY+buttonHeight/2+textSize/4, paint);
    }

    @Override
    public void update() {

    }

    public boolean onClick(MotionEvent event){
        if (buttonRect.contains((int) event.getX(), (int) event.getY())) {



            return true;
        }

        else return false;
    }


}
