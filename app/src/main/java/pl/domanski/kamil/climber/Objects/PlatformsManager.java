package pl.domanski.kamil.climber.Objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

import pl.domanski.kamil.climber.Engine.Constans;

// Zarządzanie platformami - generacja

public class PlatformsManager {

    private ArrayList<Platforms> platforms;
    private int platformWidth;
    private int platformHeight;

    public int indexColide;
    private int lastX = 0;


    private int xStart;
    public int score = 0;

    private Random rand;


    public PlatformsManager(int platformWidth, int platformHeight) {
        this.platformHeight = platformHeight;
        this.platformWidth = platformWidth;

        rand = new Random();




        platforms = new ArrayList<>();

        generatePlatforms();

    }

    public int getPlatformType(int platIndex) {

        return platforms.get(platIndex).platformType;
    }

    public void reset() {
        score = 0;
        for (int i = platforms.size() - 1; i > -1; i--) {
            platforms.remove(i);
        }
        generatePlatforms();
    }

    public boolean playerCollide(Player player) {
        indexColide = -1;

        for (int i = 0; i < platforms.size() + 1; i++) {
            indexColide++;
            if (indexColide == platforms.size()) {
                indexColide = -1;
                return false;
            }

            if (platforms.get(i).playerColide(player))
                return true;
        }
        return false;
    }


    public int getPlatformColideTop() {
        if (indexColide == -1) return -100;
        return platforms.get(indexColide).getRectangle().top;
    }

    private void generatePlatforms() {
/*
      TODO  Tu musze poprawic startową platforme                !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1111


        platforms.add(new Platforms(platformHeight, Constans.SCREEN_WIDTH, 0, Constans.SCREEN_HEIGHT - platformHeight, 0));
        platforms.get(0).platformType = 0;*/

        platforms.add(new Platforms(platformHeight, platformWidth, 0, Constans.SCREEN_HEIGHT - platformHeight, 0));
        platforms.get(0).platformType = 0;
        int currY = (int) (-Constans.SCREEN_HEIGHT * 3 / 4);


        while (currY < 0) {
            xStart = rand.nextInt(Constans.SCREEN_WIDTH - platformWidth);

            while (Math.abs(lastX - xStart) < Constans.SCREEN_WIDTH / 5){
                xStart = (int) (Math.random() * (Constans.SCREEN_WIDTH - platformWidth));
            }


            lastX = xStart;
            int y = rand.nextInt(Constans.SCREEN_HEIGHT/19);
            platforms.add(0, new Platforms(platformHeight, platformWidth, xStart, ((int)(-Constans.SCREEN_HEIGHT/6.4) + platforms.get(0).getRectangle().top - y), randPlatformType()));
            if (platforms.get(0).platformType == 1) {

                GenerateMovingPlatformPostition();

            }
            currY += platformHeight + y;
        }

    }

    public void update() {
        if (platforms.get(platforms.size() - 1).getRectangle().top >= Constans.SCREEN_HEIGHT*20/19) {

            newPlatform();
            score += 10;
        }

        for (Platforms pl : platforms) {

            pl.update();
        }
    }


    private void newPlatform() {

        for (int i = platforms.size() - 2; i >= 0; i--) {


            platforms.get(i + 1).getRectangle().top = platforms.get(i).getRectangle().top;
            platforms.get(i + 1).getRectangle().bottom = platforms.get(i).getRectangle().bottom;
            platforms.get(i + 1).getRectangle().left = platforms.get(i).getRectangle().left;
            platforms.get(i + 1).getRectangle().right = platforms.get(i).getRectangle().right;

            platforms.get(i + 1).platformType = platforms.get(i).platformType;
            platforms.get(i + 1).howFar = platforms.get(i).howFar;
            platforms.get(i + 1).direction = platforms.get(i).direction;

        }


        xStart = (int) (Math.random() * (Constans.SCREEN_WIDTH - platformWidth));

        while (Math.abs(lastX - xStart) < Constans.SCREEN_WIDTH / 5)

        {
            xStart = (int) (Math.random() * (Constans.SCREEN_WIDTH - platformWidth));
        }
        lastX = xStart;

        int y = rand.nextInt(Constans.SCREEN_HEIGHT/19);
        platforms.get(0).getRectangle().left = xStart;
        platforms.get(0).getRectangle().right = xStart + platformWidth;
        platforms.get(0).getRectangle().top = ((int)(-Constans.SCREEN_HEIGHT/6.4) + platforms.get(0).getRectangle().top - y);
        platforms.get(0).getRectangle().bottom = platforms.get(0).getRectangle().top + platformHeight;


        platforms.get(0).platformType = randPlatformType();
        if (platforms.get(0).platformType == 1) {

            GenerateMovingPlatformPostition();

        }

        else if(platforms.get(0).platformType == 2){
            platforms.get(0).getRectangle().bottom = platforms.get(0).getRectangle().top + platformHeight;
        }


    }

    private void GenerateMovingPlatformPostition() {

        platforms.get(0).howFar = rand.nextInt((int)(Constans.SCREEN_WIDTH/2.7) - 7);
        platforms.get(0).direction = 1;

        xStart = (int) (Math.random() * (Constans.SCREEN_WIDTH/3.86 - platformWidth) + Constans.SCREEN_WIDTH/2.7);


        platforms.get(0).getRectangle().left = xStart;
        platforms.get(0).getRectangle().right = xStart + platformWidth;

    }

    public void incrementY(float inY) {

        for (Platforms pl : platforms) {
            pl.incrementY(inY);
        }
    }

    public int randPlatformType() {

        int type = (int) (Math.random() * 50);
        if (type > 10) {
            return 0;
        } else if (type > 2 && type <= 10) {
            return 1;

        } else return 2;

    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.WHITE);

        for (Platforms pl : platforms) {

            pl.draw(canvas);
        }





    }
}
