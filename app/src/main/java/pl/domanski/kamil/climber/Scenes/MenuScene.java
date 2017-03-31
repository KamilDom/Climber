package pl.domanski.kamil.climber;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import pl.domanski.kamil.climber.Scenes.Scene;

// Klasa odpowiedzialna za menu

class MenuScene implements Scene {

    private Paint paint;
    private Rect r;
    private Rect NewGameRect;
    private Rect Exit;
    private Rect About;

    protected SceneManager sceneManager;

    public int ActiveScene=0;

    public MenuScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        paint = new Paint();
        r = new Rect();
        NewGameRect = new Rect();
        Exit = new Rect();
        About = new Rect();
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {



        canvas.drawColor(Color.GRAY);

        paint.setColor(Color.DKGRAY);
        paint.setTextSize(Constans.SCREEN_WIDTH/5);
        drawCenterText(canvas, paint, "Climber", Constans.SCREEN_HEIGHT/5);



        paint.setColor(Color.DKGRAY);

        NewGameRect = getTextRect(canvas, "New Game", Constans.SCREEN_HEIGHT/3, Constans.SCREEN_WIDTH/8);
        canvas.drawRect(getTextRect(canvas, "New Game", Constans.SCREEN_HEIGHT/3, Constans.SCREEN_WIDTH/8), paint);

        About = getTextRect(canvas, "About", Constans.SCREEN_HEIGHT/2, Constans.SCREEN_WIDTH/8);
        canvas.drawRect(getTextRect(canvas, "About", Constans.SCREEN_HEIGHT/2, Constans.SCREEN_WIDTH/8), paint);

        Exit = getTextRect(canvas, "Exit", Constans.SCREEN_HEIGHT*2/3, Constans.SCREEN_WIDTH/8);
        canvas.drawRect(getTextRect(canvas, "Exit", Constans.SCREEN_HEIGHT*2/3, Constans.SCREEN_WIDTH/8), paint);

        paint.setColor(Color.WHITE);

        drawCenterText(canvas, paint, "New Game", Constans.SCREEN_HEIGHT/3);
        drawCenterText(canvas, paint, "About", Constans.SCREEN_HEIGHT/2);
        drawCenterText(canvas, paint, "Exit", Constans.SCREEN_HEIGHT*2/3);





    }

    @Override
    public void terminate() {

    }


    @Override
    public void recieveTouch(MotionEvent event) {
        if(NewGameRect.contains((int)event.getX(), (int)event.getY()) ){
            sceneManager.setScene(1);
        }

        else if(About.contains((int)event.getX(), (int)event.getY()) ){
            sceneManager.setScene(2);
        }

        else if(Exit.contains((int)event.getX(), (int)event.getY()) ){

            System.exit(0);
        }
        //else ActiveScene =0;

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


    private Rect getTextRect(Canvas canvas, String text, int y, int textSize){
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        Rect a = new Rect ( (int) x, y-r.height()-r.height()/4, (int) (Constans.SCREEN_WIDTH-x) ,y+r.height()/4);

        return a;
    }


}
