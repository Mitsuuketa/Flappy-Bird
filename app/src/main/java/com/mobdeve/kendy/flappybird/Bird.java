package com.mobdeve.kendy.flappybird;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bird {
    private int x, y, radius = 50;
    private int velocity = 0;
    private final int gravity = 2;
    private final int lift = -30;

    public Bird() {
        x = 200;  // Initial horizontal position
        y = 300;  // Initial vertical position
    }

    public void update() {
        velocity += gravity;
        y += velocity;

        if (y < 0) y = 0; // Keep bird within screen bounds
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(x, y, radius, paint);
    }

    public void flap() {
        velocity = lift;
    }
}
