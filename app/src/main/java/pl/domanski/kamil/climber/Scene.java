package pl.domanski.kamil.climber;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Kamil on 19.03.2017.
 */

public interface Scene {



    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void recieveTouch(MotionEvent event);


}
