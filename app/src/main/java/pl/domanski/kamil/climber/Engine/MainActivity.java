package pl.domanski.kamil.climber.Engine;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import pl.domanski.kamil.climber.Scenes.SceneManager;


// Klasa glownej aktywnosci
// W tej klasie ustawione jest wyswietlanie klasy GamePanel ktora dziedziczy po SurfaceView


public class MainActivity extends Activity {

    private GamePanel gamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                , WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constans.SCREEN_HEIGHT = dm.heightPixels;
        Constans.SCREEN_WIDTH = dm.widthPixels;

        gamePanel = new GamePanel(this);

        setContentView(gamePanel);
    }

    @Override
    public void onBackPressed() {

        GamePanel.OnBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gamePanel.onPause();
    }
}