package com.example.heta.flyingjet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by HETA on 01-May-17.
 */
public class Boom {
    private int x, y;
    private Bitmap bitmap;

    protected Boom(Context context)
    {
        bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.boom);
        x= -250;
        y= -250;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
