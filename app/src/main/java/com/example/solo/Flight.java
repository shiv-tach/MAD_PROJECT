package com.example.solo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.solo.GameView.screenRatioX;
import static com.example.solo.GameView.screenRatioY;

public class Flight {

    public boolean isGoingUp = false;
    public int toShoot = 0;
    int x, y, width, height,wingCounter =0 , shootCounter = 1;
    Bitmap flight1,flight2, shoot1, shoot2, shoot3, shoot4, shoot5, dead;
    private GameView gameview;

    Flight(GameView gameview, int screenY, Resources res){

        this.gameview = gameview;


        flight1 = BitmapFactory.decodeResource(res,R.drawable.fly1);//adding the resources for flying object
        flight2 = BitmapFactory.decodeResource(res,R.drawable.fly2);


        width = flight1.getWidth();
        height = flight1.getHeight();

        width/=5;
        height/=5;// reduce the size of the image

        width *=(int) screenRatioX;
        height *=(int) screenRatioY;


        flight1 = Bitmap.createScaledBitmap(flight1,width,height,false);
        flight2 = Bitmap.createScaledBitmap(flight2,width,height,false);

        shoot1 = BitmapFactory.decodeResource(res, R.drawable.shoot1);
        shoot2 = BitmapFactory.decodeResource(res, R.drawable.shoot2);
        shoot3 = BitmapFactory.decodeResource(res, R.drawable.shoot3);
        shoot4 = BitmapFactory.decodeResource(res, R.drawable.shoot4);
        shoot5 = BitmapFactory.decodeResource(res, R.drawable.shoot5);

        //Reduce the size

        shoot1 = Bitmap.createScaledBitmap(shoot1, width, height, false);
        shoot2 = Bitmap.createScaledBitmap(shoot2, width, height, false);
        shoot3 = Bitmap.createScaledBitmap(shoot3, width, height, false);
        shoot4 = Bitmap.createScaledBitmap(shoot4, width, height, false);
        shoot5 = Bitmap.createScaledBitmap(shoot5, width, height, false);


        dead = BitmapFactory.decodeResource(res,R.drawable.dead);
        dead = Bitmap.createScaledBitmap(dead, width, height,false);


        y = screenY/2;//center vertically
        x = (int) (64*screenRatioX);

    }

    Bitmap getFlight(){

        if(toShoot != 0){  //have bullets to shoot

                if(shootCounter == 1){

                      shootCounter++;

                    return shoot1;
                }


                if(shootCounter == 2){

                        shootCounter++;

                     return shoot2;
                }


                 if(shootCounter == 3){

                             shootCounter++;

                        return shoot3;
                 }


                 if(shootCounter == 4){

                            shootCounter++;

                          return shoot4;
                    }

                 //creating support to the loop process
                 shootCounter = 1;
                 toShoot--;
                 gameview.newBullet();//calling new bullet function

                 return shoot5;

        }



        if(wingCounter == 0){

            wingCounter++;
            return flight1;

        }

        wingCounter--;
        return flight2;

    }

    Rect getCollisionsShape(){

        return new Rect(x, y , x+width ,y+height);
    }

    Bitmap getDead(){

        return dead;
    }



}
