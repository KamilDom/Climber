package pl.domanski.kamil.climber.Animations;

import android.graphics.Canvas;
import android.graphics.Rect;

import pl.domanski.kamil.climber.Animations.Animation;

/**
 * Created by Kamil on 19.03.2017.
 */

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
