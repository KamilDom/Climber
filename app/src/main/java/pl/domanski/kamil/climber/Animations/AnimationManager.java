package pl.domanski.kamil.climber.Animations;

import android.graphics.Canvas;


// Klasa zarządzająca animacjami, np ptak ma dwie animacje - lotu w lewo i w prawo
// Przy pomocy tej klasy wybiera się która z animacji ma być odtwarzana w danym momencie

public class AnimationManager {

    private Animation[] animations;
    private int animationIndex = 0;

    public AnimationManager(Animation[] animations) {
        this.animations = animations;
    }

    public void playAnim(int index) {
        for (int i = 0; i < animations.length; i++) {
            if (i == index) {
                if (!animations[index].isPlaying())
                    animations[i].play();
            } else
                animations[i].stop();
        }
        animationIndex = index;
    }

    public void draw(Canvas canvas, int xStart, int yStart) {
        if (animations[animationIndex].isPlaying()) ;
        animations[animationIndex].draw(canvas, xStart, yStart);
    }

    public void update() {
        if (animations[animationIndex].isPlaying()) ;
        animations[animationIndex].update();
    }


}
