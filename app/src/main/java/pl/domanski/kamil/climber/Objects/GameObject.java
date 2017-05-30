package pl.domanski.kamil.climber.Objects;

import android.graphics.Canvas;

// interface ktory implementuja wszystkie obiekty gry

public interface GameObject
{
    public void draw(Canvas canvas);
    public void update();
}
