package com.example.joystickapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.DisplayMetrics;
import android.view.View;


public class JoyStickView extends View {

    public int centerX;
    public int centerY;
    public int baseRadius;
    public int hatRadius;
    public int statusBarHeight;
    public int newX;
    public int newY;

    public JoyStickView(Context context) {
        super(context);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        // ensures that the joystick will always fit inside the surface view even if one dimension is greatly smaller than the other.
        baseRadius = Math.min(dm.widthPixels, dm.heightPixels) / 3;
        hatRadius = Math.min(dm.widthPixels, dm.heightPixels) / 7;
        statusBarHeight = getStatusbarHeight() * (int) dm.density;
        newX = centerX = (int) dm.widthPixels / 2; // the width
        newY = centerY = (int) (dm.heightPixels + statusBarHeight) / 2; // the height
    }

    public int getHatRadius() {
        return hatRadius;
    }

    public void setNewPos(int newX, int newY) {
        double distance = this.distance(newX, newY, this.centerX, this.centerY);
        // if the point is inside the region of the joystick
        if (distance <= this.baseRadius) {
            this.newX = newX;
            this.newY = newY;
        } else {
            int dx = newX - centerX;
            int dy = newY - centerY;
            int dR = (int) (distance - baseRadius);

            // using Thales' theorem to calculate the lengths that are outside the joystick
            int deltaX = (int) (dR * dx / distance);
            int deltaY = (int) (dR * dy / distance);

            this.newX = newX - deltaX;
            this.newY = newY - deltaY;
        }
    }

    public double distance(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    private int getStatusbarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0)
            return getResources().getDimensionPixelSize(resourceId);
        return 0;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawJoystick(canvas, this.newX, this.newY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.newX = this.centerX = w / 2;
        this.newY = this.centerY = (h + this.statusBarHeight) / 2;


    }

    // drawing the joystick on the screen
    private void drawJoystick(Canvas myCanvas, float newX, float newY) {
        Paint colors = new Paint();
        myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // Clearing the BG

        // todo : NICE TO HAVE : to move the coloring to constant params

        colors.setColor(Color.argb(250, 50, 50, 50));// color of the joystick base
        myCanvas.drawCircle(centerX, centerY - this.statusBarHeight, baseRadius, colors); // draw the joystick base
        colors.setColor(Color.argb(255, 0, 0, 255));// colo of the joystick itself
        myCanvas.drawCircle(newX, newY - this.statusBarHeight, hatRadius, colors); // draw the joystick hat


    }
}