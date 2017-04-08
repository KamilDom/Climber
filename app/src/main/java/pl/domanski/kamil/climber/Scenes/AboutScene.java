package pl.domanski.kamil.climber.Scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import pl.domanski.kamil.climber.Engine.Constans;
import pl.domanski.kamil.climber.Engine.OrientationData;

/**
 * Created by Kamil on 30.03.2017.
 */

public class AboutScene implements Scene {

    private final SceneManager sceneManager;
    private Rect r;
    private Paint paint;
    private Rect back;





    public AboutScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        r = new Rect();
        paint = new Paint();
        back = new Rect(Constans.SCREEN_WIDTH*3/5, Constans.SCREEN_HEIGHT/4 + Constans.SCREEN_HEIGHT*3/10,
                    Constans.SCREEN_WIDTH*9/10, Constans.SCREEN_HEIGHT/4 + Constans.SCREEN_HEIGHT*4/10  );


    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);

        paint.setColor(Color.DKGRAY);
        paint.setTextSize(Constans.SCREEN_WIDTH/7);
        drawCenterText(canvas, paint, "Authors:", Constans.SCREEN_HEIGHT/4);

        paint.setColor(Color.WHITE);
        paint.setTextSize(Constans.SCREEN_WIDTH/10);
        drawCenterText(canvas, paint, "Kamil Domański", Constans.SCREEN_HEIGHT/4 + Constans.SCREEN_HEIGHT/10);
        drawCenterText(canvas, paint, "Katarzyna Jaskólska", Constans.SCREEN_HEIGHT/4 + Constans.SCREEN_HEIGHT*2/10);

        paint.setColor(Color.DKGRAY);
        canvas.drawRect(back,paint);
    }

    @Override
    public void terminate() {

    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if(back.contains((int)event.getX(), (int)event.getY()) ){
                    sceneManager.setScene(0);
                }

                break;


        }

    }

    public void drawCenterText(Canvas canvas, Paint paint, String text, int y) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);

        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        //  float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);





    }

}
