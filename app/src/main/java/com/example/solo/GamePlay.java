package com.example.solo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class GamePlay extends AppCompatActivity {

    private GameView gameView;//create the view of the game


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);


        gameView = new GameView(this,point.x,point.y);

        setContentView(gameView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        gameView.rusume();
    }
}