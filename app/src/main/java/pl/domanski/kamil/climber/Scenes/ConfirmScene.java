package pl.domanski.kamil.climber.Scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

import pl.domanski.kamil.climber.Engine.Constans;
import pl.domanski.kamil.climber.Objects.Button;

// // Klasa do wyswietlanie i zarzadzania menu potwierdzenia

public class ConfirmScene implements Scene {

    public ArrayList<Button> buttons;
    Paint paint;
    private int textButtonColor = Color.rgb(255, 255, 255);
    private int backgroundButtonColor = Color.rgb(0, 120, 0);
    private String text, text2;


    public ConfirmScene(String text, String text2) {
        buttons = new ArrayList<Button>();
        paint = new Paint();
        this.text = text;
        this.text2 = text2;

        buttons.add(new Button(Constans.SCREEN_WIDTH *238/1485, Constans.SCREEN_HEIGHT * 33 / 64, Constans.SCREEN_WIDTH * 14 / 45, Constans.SCREEN_HEIGHT *8/ 90, "No", Constans.SCREEN_WIDTH *8/ 90, textButtonColor, backgroundButtonColor));
        buttons.add(new Button(Constans.SCREEN_WIDTH*791/1485, Constans.SCREEN_HEIGHT * 33 / 64, Constans.SCREEN_WIDTH * 14 / 45, Constans.SCREEN_HEIGHT *8/ 90, "Yes", Constans.SCREEN_WIDTH *8/ 90, textButtonColor, backgroundButtonColor));

    }




    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(  Color.rgb(0, 100 , 200));
        canvas.drawRect(Constans.SCREEN_WIDTH/11, Constans.SCREEN_HEIGHT*11/32, Constans.SCREEN_WIDTH*10/11, Constans.SCREEN_HEIGHT*21/32,paint);
        paint.setColor(textButtonColor);
        paint.setTypeface(Constans.font);
        paint.setTextSize(Constans.SCREEN_WIDTH *7/ 90);
        canvas.drawText(text, Constans.SCREEN_WIDTH *238/1485,  Constans.SCREEN_HEIGHT*13/32, paint);
        canvas.drawText(text2, Constans.SCREEN_WIDTH *238/1485,  Constans.SCREEN_HEIGHT*13/32  + Constans.SCREEN_WIDTH *8/ 90, paint);
        paint.setColor(Color.rgb(0,0,0));
        paint.setStrokeWidth(Constans.SCREEN_WIDTH/216);
        canvas.drawLine(Constans.SCREEN_WIDTH/11, Constans.SCREEN_HEIGHT*11/32, Constans.SCREEN_WIDTH*10/11, Constans.SCREEN_HEIGHT*11/32, paint);
        canvas.drawLine(Constans.SCREEN_WIDTH*10/11, Constans.SCREEN_HEIGHT*11/32, Constans.SCREEN_WIDTH*10/11, Constans.SCREEN_HEIGHT*21/32, paint);
        canvas.drawLine(Constans.SCREEN_WIDTH/11, Constans.SCREEN_HEIGHT*21/32, Constans.SCREEN_WIDTH*10/11, Constans.SCREEN_HEIGHT*21/32, paint);
        canvas.drawLine(Constans.SCREEN_WIDTH/11, Constans.SCREEN_HEIGHT*11/32, Constans.SCREEN_WIDTH/11, Constans.SCREEN_HEIGHT*21/32, paint);

        for (Button bu : buttons) {
            bu.draw(canvas);
        }
    }

    @Override
    public void terminate() {

    }

    @Override

    public void recieveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (buttons.get(0).onClick(event)) {
                System.out.println("no");
            }

            else if (buttons.get(1).onClick(event)) {
                System.out.println("yes");
            }
        }

    }
}
