package com.mobdeve.myflappybird;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bird {

    private int x, y;
    private int width = 100, height = 100;
    private int velocity = 0;
    private int gravity = 1;
    private int flapStrength = -20;

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        velocity += gravity;
        y += velocity;
    }

    public void flap() {
        velocity = flapStrength;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.YELLOW); // Bird color
        canvas.drawRect(x, y, x + width, y + height, paint);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
