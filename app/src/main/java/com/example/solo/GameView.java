package com.example.solo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver= false;
    private Paint paint;
    private SharedPreferences prefs;
    private Flight flight;
    private Bird[] birds;
    private SoundPool soundPool;
    private int sound;
    private GamePlay activity;
    private Random random;//to get random speed for birds
    private List<Bullet> bullets;
    public static float screenRatioX,screenRatioY;//setting values and get compatible
    private int screenX,screenY, score;
    private Background background1, background2;


    public GameView(GamePlay activity,int screenX,int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game",Context.MODE_PRIVATE);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();

            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).build();

        }else{

            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);



        }


        sound = soundPool.load(activity,R.raw.shoot,1);







        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;


        background1 = new Background(screenX,screenY,getResources());
        background2 = new Background(screenX,screenY,getResources());

        flight = new Flight(this,screenY,getResources());

        bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);




        paint = new Paint();

        birds = new Bird[4];


        for (int i = 0; i <4 ; i++) {


            Bird bird = new Bird(getResources());
            birds[i] = bird;

        }

        random = new Random();


    }

    @Override
    public void run() {

        while(isPlaying){

            update();
            draw();
            sleep();


        }



    }


    private void update(){

        background1.x -=10 * screenRatioX;// background is moving left to right
        background2.x -=10 * screenRatioX;

        if(background1.x + background1.background.getWidth()<0){
            background1.x = screenX;
        }

        if(background2.x + background2.background.getWidth()<0){
            background2.x = screenX;
        }

        //checking flight moving up and down
        if(flight.isGoingUp){
            flight.y -= 50 * screenRatioY;
        }
        else{
            flight.y += 10 * screenRatioY;
        }

        if(flight.y <0){    //flight in the top then we can't move up
            flight.y = 0;
        }

        if(flight.y > screenY - flight.height) {// this will be true when the flight go out the screeen


            flight.y = screenY - flight.height;

        }

        List<Bullet> trash = new ArrayList<>();// adding bullets which are crashed.



        for(Bullet bullet : bullets){

            if(bullet.x > screenX)
                trash.add(bullet);

            bullet.x += 50* screenRatioX;


            for(Bird bird : birds){

                if(Rect.intersects(bird.getCollisionsShape(),bullet.getCollisionsShape())){//bird hits the bullet

                    score++;
                    bird.x = -500;
                    bullet.x = screenX + 500;
                    bird.wasShot = true;

                }


            }

        }

        for(Bullet bullet : trash){

            bullets.remove(bullet);
        }

        for(Bird bird : birds){

            bird.x  -= bird.speed;

            if(bird.x + bird.width < 0) {//bird is off the screen


               /*if(!bird.wasShot) {

                    isGameOver = true;
                    return;

                }*/

                int bound = (int) (30 * screenRatioX); //bound limit speed is 30

                bird.speed = random.nextInt(bound);

                if(bird.speed < 10 * screenRatioX){
                    bird.speed = (int) (10*screenRatioX);
                }


                bird.x = screenX;
                bird.y = random.nextInt(screenX- bird.height);


                bird.wasShot = false;

            }

            if(Rect.intersects(bird.getCollisionsShape(), flight.getCollisionsShape())){

                isGameOver = true;

                return;

            }

        }






    }

    private void draw(){

        if(getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            //first background
            canvas.drawBitmap(background1.background,background1.x,background1.y,paint);
            //second background
            canvas.drawBitmap(background2.background,background2.x,background2.y,paint);

            for (Bird bird : birds){

                canvas.drawBitmap(bird.getBird(),bird.x,bird.y, paint);
            }

            canvas.drawText(score + "",screenX/2f,164,paint);



            //check flight is  crash and set crash img of the flight
            if(isGameOver){

                isPlaying =false;
                canvas.drawBitmap(flight.getDead(), flight.x,flight.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting();


                return;
            }




            //this is main character of the game
            canvas.drawBitmap(flight.getFlight(),flight.x,flight.y, paint);


            //bullets of the gun
            for(Bullet bullet : bullets){

                canvas.drawBitmap(bullet.bullet,bullet.x,bullet.y,paint);

            }



            //showing the canvas on the screen

            getHolder().unlockCanvasAndPost(canvas);

        }



    }

    private void waitBeforeExiting() {

        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity,MainActivity.class));
            activity.finish();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //high score function.
    private void saveIfHighScore() {

        if(prefs.getInt("highscore",0)<score){

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore",score);

            editor.apply();
        }

    }

    private void sleep(){

        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }






    public void rusume(){

        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause(){

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()){

            case MotionEvent.ACTION_DOWN:

            if(event.getX() < screenX /2){

                flight.isGoingUp = true;

            }
            break;


            case MotionEvent.ACTION_UP:
                flight.isGoingUp = false;
                if(event.getX() > screenX / 2)//its true touching is come from right side of the screen
                    flight.toShoot++;
            break;

        }



        return true;//first time we need to true



    }


    public void newBullet() {


        if(!prefs.getBoolean("isMute",false)){
            soundPool.play(sound,1,1,0,0,1);
        }

        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.height /2); //gun is located center of the flight
        bullets.add(bullet);



    }


























}
