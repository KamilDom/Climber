package pl.domanski.kamil.climber;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Kamil on 19.03.2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread;
    private static SceneManager manager;
    Paint paint = new Paint();

    public GamePanel(Context context){
        super(context);
        getHolder().addCallback(this);
        Constans.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(), this);
        manager = new SceneManager();

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(),this);
        Constans.INIT_TIME = System.currentTimeMillis();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry)
        {
            try
            {
                thread.setRunning(false);
                thread.join();
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
            retry = false;

        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        manager.recieveTouch(event);
        return true;
    }

    public void update() {
        manager.update();
    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);
        manager.draw(canvas);
        paint.setTextSize(50);
        paint.setColor(Color.YELLOW);
        canvas.drawText("FPS: " + String.valueOf(thread.averageFPS),Constans.SCREEN_WIDTH-300,50,paint);
    }

    public static void OnBackPressed() {
        manager.OnBackPressed();
    }


}
