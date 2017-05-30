package pl.domanski.kamil.climber.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;

import pl.domanski.kamil.climber.Engine.Constans;
import pl.domanski.kamil.climber.Objects.GameObject;
import pl.domanski.kamil.climber.R;


// klasa do przyciskow - klasa skaluje grafiki potrzebne do narysownia przycisku o zadanych wymiarach,
// oblicza wspolrzedene potrzebne do narysowania tekstu.
// Kazdy obiekt tej klasy ma tez swoja funkcje onClick

public class Button implements GameObject{

    private int startX;
    private int startY;
    private int buttonWidth;
    private int buttonHeight;
    private String buttonText;
    private int textSize;
    Paint paint;
    public Rect buttonRect;
    private int textColor;
    private int buttonColor;

    BitmapFactory bf = new BitmapFactory();



    Bitmap buttonEdge = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
            R.drawable.button_edge);

    Bitmap buttonMiddle = bf.decodeResource(Constans.CURRENT_CONTEXT.getResources(),
            R.drawable.button_middle);

    private Bitmap[] buttonBitmaps;

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

        buttonBitmaps = buttonBitmaps(buttonEdge, buttonMiddle,  buttonHeight);


    }

    public Bitmap[] buttonBitmaps(Bitmap edgeLeftSource, Bitmap middleSource,  int destinationHeight){
        float xRatio =  edgeLeftSource.getHeight()/destinationHeight;
        Bitmap leftEdge = getResizedBitmap(edgeLeftSource , (int) (edgeLeftSource.getWidth()/xRatio),destinationHeight);

        Bitmap middle = getResizedBitmap(middleSource,buttonWidth-2*leftEdge.getWidth(),buttonHeight);

        Matrix m = new Matrix();
        m.preScale(-1, 1);

        Bitmap rightEdge = Bitmap.createBitmap(leftEdge, 0, 0, leftEdge.getWidth(), leftEdge.getHeight(), m, false);

        Bitmap[] temp = {leftEdge,middle,rightEdge};

        return  temp;
    }

    @Override
    public void draw(Canvas canvas) {

        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(buttonColor);
        canvas.drawBitmap(buttonBitmaps[0], startX,startY, paint);
        canvas.drawBitmap(buttonBitmaps[1], startX+buttonBitmaps[0].getWidth(),startY, paint);
        canvas.drawBitmap(buttonBitmaps[2], startX+buttonBitmaps[0].getWidth()+buttonBitmaps[1].getWidth(),startY, paint);
        paint.setTypeface(Constans.font);
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

    public void updateText(String text){
        buttonText = text;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }
}
