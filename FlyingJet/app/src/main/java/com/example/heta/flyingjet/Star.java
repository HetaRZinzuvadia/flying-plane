package com.example.heta.flyingjet;

import java.util.Random;

/**
 * Created by HETA on 29-Apr-17.
 */
public class Star {
    private int x, y, maxX, maxY, minX, minY, speed;

    public Star(int screenX, int screenY)
    {
        maxX= screenX;
        maxY= screenY;
        minX= 0;
        minY= 0;
        Random generator= new Random();
        speed= generator.nextInt(6);

        x= generator.nextInt(maxX);
        y= generator.nextInt(maxY);
    }

    public void update(int playerSpeed)
    {
        x -= playerSpeed;
        x -= speed;

        if (x < 0) {
            x = maxX;
            Random generator = new Random();
            y = generator.nextInt(maxY);
            speed = generator.nextInt(10);
        }
    }

    public float setStarWidth()
    {
        float maxX=4.0f;
        float minX= 1.0f;
        Random random= new Random();
        float finalX= random.nextFloat() * (maxX - minX) + minX;
        return finalX;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
