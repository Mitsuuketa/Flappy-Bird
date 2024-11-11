package com.mobdeve.kendy.flappybird;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.Random;

public class Obstacle {
    private int x, height, width = 100;
    private int gap = 300;
    private int screenHeight;

    public Obstacle(int screenWidth, int screenHeight) {
        this.screenHeight = screenHeight;
        x = screenWidth;
        height = new Random().nextInt(screenHeight - gap);
    }

    public void update() {
        x -= 10;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.GREEN);
        canvas.drawRect(x, 0, x + width, height, paint);
        canvas.drawRect(x, height + gap, x + width, screenHeight, paint);
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }
}
