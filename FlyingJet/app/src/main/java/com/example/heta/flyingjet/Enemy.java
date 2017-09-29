package com.example.heta.flyingjet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by HETA on 30-Apr-17.
 */
public class Enemy {
    private Bitmap bitmap;
    private int x, y, speed, maxX, minX, maxY, minY;
    private Rect detectCollision;

    public Enemy(Context context, int screenX, int screenY)
    {
        bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy);

        maxX= screenX;
        maxY= screenY;
        minX= 0;
        minY= 0;
        speed= 1;

        Random generator= new Random();
        speed = generator.nextInt(6) + 6;
        x = screenX;
        y = generator.nextInt(maxY) - bitmap.getHeight();

        detectCollision= new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(int playerSpeed)
    {
        x -= playerSpeed;
        x -= speed;

        if (x < minX - bitmap.getWidth())
        {
            //adding the enemy again to the right edge
            Random generator = new Random();
            speed = generator.nextInt(10) + 10;
            x = maxX;
            y = generator.nextInt(maxY) - bitmap.getHeight();
        }

        detectCollision.right= x+ bitmap.getWidth();
        detectCollision.left= x;
        detectCollision.top= y;
        detectCollision.bottom= y+ bitmap.getHeight();
    }

    public void setX(int x) {
        this.x = x;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }
}
