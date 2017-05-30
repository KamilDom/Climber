package pl.domanski.kamil.climber.Engine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import pl.domanski.kamil.climber.Scenes.SceneManager;


// Glowna klasa - w niej tworzony jest wątek gry oraz nadpisywana jest metoda rysowania

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread;
    private static SceneManager manager;


    public GamePanel(Context context){
        super(context);
        getHolder().addCallback(this);
        Constans.CURRENT_CONTEXT = context;
        Constans.font = Typeface.createFromAsset(Constans.CURRENT_CONTEXT.getAssets(), "Marker.ttf" );

        thread = new MainThread(getHolder(), this);
        manager = new SceneManager();
        getHolder().setFormat(PixelFormat.RGBX_8888);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(),this);
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

    }

    public static void OnBackPressed() {

        manager.OnBackPressed();
    }


    public void onPause() {
        manager.onPause();
    }
}