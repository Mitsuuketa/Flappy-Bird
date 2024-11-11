package com.mobdeve.kendy.flappybird;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {
    private Thread gameThread;
    private boolean isPlaying;
    private Bird bird;
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private ArrayList<Obstacle> obstacles;
    private int screenWidth, screenHeight;

    public GameView(Context context) {
        super(context);
        bird = new Bird(); // Initialize bird object
        paint = new Paint();
        surfaceHolder = getHolder();
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        obstacles = new ArrayList<>();
        spawnObstacle();
    }

    private void spawnObstacle() {
        obstacles.add(new Obstacle(screenWidth, screenHeight));
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        bird.update();
        for (Obstacle obstacle : obstacles) {
            obstacle.update();
            if (obstacle.isOffScreen()) {
                obstacles.remove(obstacle);
                spawnObstacle();
            }
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.CYAN);

            bird.draw(canvas, paint);

            for (Obstacle obstacle : obstacles) {
                obstacle.draw(canvas, paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17); // Roughly 60 FPS
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            bird.flap();
        }
        return true;
    }

    public void pause() {
        isPlaying = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
