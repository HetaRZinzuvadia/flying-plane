package com.example.heta.flyingjet;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by HETA on 29-Apr-17.
 */
public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;
    private Thread gameThread= null;

    private Canvas canvas;
    private Paint paint;
    private SurfaceHolder surfaceHolder;

    public Player player;
    public Enemy[] enemies;
    public int enemyCount= 5;

    int missesCount, screenX;
    private boolean isGameOver;
    boolean flag;

    int score;
    int highScore[]= new int[4];
    SharedPreferences sharedPreferences;

    private ArrayList<Star> stars= new ArrayList<Star>();

    private Boom boom;

    public GameView(Context context, int screenX, int screenY)
    {
        super(context);
        paint= new Paint();
        surfaceHolder= getHolder();

        isGameOver= false;
        missesCount= 0;
        flag= false;
        this.screenX= screenX;


        player= new Player(context, screenX, screenY);

        int starnum= 90;
        for (int i= 0; i< starnum;i++)
        {
            Star s= new Star(screenX, screenY);
            stars.add(s);
        }

        enemies= new Enemy[enemyCount];
        for(int i= 0; i< enemyCount; i++)
        {
            enemies[i]= new Enemy(context, screenX, screenY);
        }

        boom= new Boom(context);
        score= 0;

        sharedPreferences= context.getSharedPreferences("highscore",Context.MODE_PRIVATE);
        highScore[0]= sharedPreferences.getInt("highScore1",0);
        highScore[1]= sharedPreferences.getInt("highScore2",0);
        highScore[2]= sharedPreferences.getInt("highScore3",0);
        highScore[3]= sharedPreferences.getInt("highScore4",0);

    }

    @Override
    public void run() {
      while (playing)
      {
          update();
          draw();
          control();
      }
    }

    private void update()
    {
        score++;
        player.update();

        for (Star s: stars)
        {
            s.update(player.getSpeed());
        }


       for (int i= 0; i< enemyCount; i++)
        {

            if (enemies[i].getX()== screenX)
            {
                flag= true;
            }
            enemies[i].update(player.getSpeed());

            if (Rect.intersects(player.getDetectcollision(), enemies[i].getDetectCollision()))
            {
                boom.setX(enemies[i].getX());
                boom.setY(enemies[i].getY());
                enemies[i].setX(-200);
            }
            else {
                if (flag)
                {
                    if (player.getDetectcollision().exactCenterX() > enemies[i].getDetectCollision().exactCenterX())
                    {
                        missesCount++;
                        flag= false;

                        if (missesCount== 10)
                        {
                            playing= false;
                            isGameOver= true;

                            for (int j= 0; j<4; i++)
                            {
                                if (highScore[i]> score)
                                {
                                    final int finalI= j;
                                    highScore[j]= score;
                                    break;
                                }
                            }
                            SharedPreferences.Editor e= sharedPreferences.edit();
                            for (int a=0;a< 4;a++)
                            {
                                int b= a+ 1;
                                e.putInt("highScore"+ b, highScore[a]);
                            }
                            e.apply();
                        }
                    }
                }
            }

        }
    }

    private void draw()
    {
        if (surfaceHolder.getSurface().isValid())
        {
            canvas= surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);

            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);

            for (Star s : stars) {
                paint.setStrokeWidth(s.setStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }

            for (int i= 0; i< enemyCount; i++)
            {
                canvas.drawBitmap(enemies[i].getBitmap(), enemies[i].getX(), enemies[i].getY(), paint);
            }

            if (isGameOver= true)
            {
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.CYAN);

                int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Game Over",canvas.getWidth()/2,yPos,paint);

                paint.setTextSize(50);
                canvas.drawText("Score"+ score, 100, 80, paint);
            }

            canvas.drawBitmap(boom.getBitmap(), boom.getX(), boom.getY(), paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control()
    {
        try {
            gameThread.sleep(20);

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void pause()
    {
        playing= false;
        try{
            gameThread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    public void resume()
    {
        playing= true;
        gameThread= new Thread(this);
        gameThread.start();
    }

    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        switch (motionEvent.getAction() & motionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_UP:
                player.setBoosting();
                break;

            case MotionEvent.ACTION_DOWN:
                player.stopBoosting();
                break;
        }
        return true;
    }
}
