package com.mobdeve.myflappybird;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Thread gameThread;
    private boolean isPlaying;
    private Paint paint;
    private float birdY;
    private float velocity;
    private float gravity = 1.5f;
    private float lift = -30;
    private final int screenWidth = getResources().getDisplayMetrics().widthPixels;
    private final int screenHeight = getResources().getDisplayMetrics().heightPixels;
    private List<Pipe> pipes;
    private long lastPipeTime;

    private boolean isGameOver = false;
    private int score; // Variable to track the score
    private int currency; // Counter to track pipes passed

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "FlappyBirdPrefs";
    private static final String PREF_CURRENCY = "currency";

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        birdY = screenHeight / 2;
        pipes = new ArrayList<>();
        lastPipeTime = System.nanoTime();
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        currency = sharedPreferences.getInt(PREF_CURRENCY, 0);
        resetGame();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new Thread(this);
        isPlaying = true;
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isPlaying = false;
        boolean retry = true;
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (isPlaying) {
            if (!isGameOver) {
                update();
                draw();
            }
        }
    }

    public void update() {
        velocity += gravity;
        birdY += velocity;

        if (birdY >= screenHeight - 50) {
            birdY = screenHeight - 50;
            velocity = 0;
            gameOver();
        } else if (birdY < 0) {
            birdY = 0;
            velocity = 0;
        }

        // Check for collision with pipes
        for (Pipe pipe : pipes) {
            if (pipe.getTopPipe().contains(screenWidth / 2 - 50, (int) birdY) ||
                    pipe.getBottomPipe().contains(screenWidth / 2 - 50, (int) birdY)) {
//                score--;
//                currency++;
                gameOver();
                break; // Stop checking further pipes once a collision is detected
            }
        }

        // Add new pipes and update existing ones
        if (System.nanoTime() - lastPipeTime > 2000000000) {
            pipes.add(new Pipe(screenHeight));
            lastPipeTime = System.nanoTime();
        }

        // Update and remove off-screen pipes
        Iterator<Pipe> iterator = pipes.iterator();
        while (iterator.hasNext()) {
            Pipe pipe = iterator.next();
            pipe.update();

            if (pipe.getX() < screenWidth / 2 - 50 && !pipe.isScored()) {
                score++;
                pipe.setScored(true); // Mark this pipe as passed
            }

            if (pipe.isOffScreen()) {
                iterator.remove();
            }
        }
    }

    public void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.CYAN); // Background color

            if (!isGameOver) {
                // Draw bird
                canvas.drawRect(screenWidth / 2 - 50, birdY - 50, screenWidth / 2 + 50, birdY + 50, paint);

                // Draw pipes
                for (Pipe pipe : pipes) {
                    pipe.draw(canvas);
                }

                // Draw score and currency
                paint.setColor(Color.BLACK);
                paint.setTextSize(50);
                canvas.drawText("Score: " + score, 50, 100, paint); // Display score for current game
                canvas.drawText("Currency: " + currency, 50, 160, paint); // Display accumulated currency
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void control() {
        try {
            Thread.sleep(17); // ~60 FPS
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isGameOver) {
                resetGame(); // Restart the game when tapped
            } else {
                velocity = lift; // Lift the bird when screen is tapped
            }
        }
        return true;
    }

    // Method to end the game
    // GameView.java
    private void gameOver() {
        isGameOver = true;
        currency += score;

        // Save the accumulated currency to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_CURRENCY, currency);
        editor.apply();

        // Start GameOverActivity and pass the score
        Intent intent = new Intent(getContext(), GameOverActivity.class);
        intent.putExtra("score", score); // Pass final score to GameOverActivity
        getContext().startActivity(intent);
    }

    private void resetGame() {
        isGameOver = false;
        birdY = screenHeight / 2;
        velocity = 0;
        pipes.clear();
        lastPipeTime = System.nanoTime();
        score = 0; // Reset score for a new game
    }
}
