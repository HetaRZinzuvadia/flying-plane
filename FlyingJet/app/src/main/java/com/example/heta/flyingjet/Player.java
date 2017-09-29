package com.example.heta.flyingjet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by HETA on 30-Apr-17.
 */
public class Player {
    private Bitmap bitmap;
    private int x, y, speed=0;

    private boolean boosting;
    private final int GRAVITY= -10;

    private int maxY;
    private int minY;

    private final int MAX_SPEED= 20;
    private final int MIN_SPEED= 1;

    private Rect detectcollision;

    public Player(Context context, int screenX, int screenY)
    {
        x= 175;
        y= 55;
        speed= 1;

        bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        maxY= screenY- bitmap.getHeight();
        minY= 0;
        boosting= false;

        detectcollision= new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void setBoosting()
    {
        boosting= true;
    }

    public void stopBoosting()
    {
        boosting= false;
    }

    public void update()
    {
        //x++;
        if (boosting)
        {
//            speed+= 1;
            y+= 75;
            x-= 7;

        }
        else
        {
//            speed-= 5;
            x+= 15;
            y-= 55;
        }

        if(speed> MAX_SPEED)
        {
            speed= MAX_SPEED-5;
        }

        if (speed< MIN_SPEED)
        {
            speed= MIN_SPEED;
        }

        x++;
        y-= speed+ GRAVITY;
        if (y> maxY)
        {
            y= maxY;
        }
        if (y< minY)
        {
            y= minY;
        }

        detectcollision.left = x;
        detectcollision.top = y;
        detectcollision.right = x + bitmap.getWidth();
        detectcollision.bottom = y + bitmap.getHeight();
    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Rect getDetectcollision() {
        return detectcollision;
    }

    public int getSpeed()
    {
        return speed;
    }
}
