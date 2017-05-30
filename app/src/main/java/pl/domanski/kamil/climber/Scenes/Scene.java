package pl.domanski.kamil.climber.Scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

// interface ktory implementuja wszystkie sceny w grze

public interface Scene {



    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void recieveTouch(MotionEvent event);


}
