package com.example.joystickapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.DisplayMetrics;
import android.view.View;


public class JoyStickView extends View {

    private int centerX;
    private int centerY;
    private int baseRadius;
    private int hatRadius;
    private int statusBarHeight;
    private int newX;
    private int newY;

    // colors of the base and the hat joystick circles
    private static final int joystickBaseColor = Color.argb(250, 50, 50, 50);
    private static final int joystickColor = Color.argb(255, 0, 0, 255);

    public JoyStickView(Context context) {
        super(context);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        // ensures that the joystick will always fit inside the surface view even if one
        // dimension is greatly smaller than the other.
        baseRadius = Math.min(dm.widthPixels, dm.heightPixels) / 3;
        hatRadius = Math.min(dm.widthPixels, dm.heightPixels) / 7;
        statusBarHeight = getStatusBarHeight() * (int) dm.density;
        newX = centerX = dm.widthPixels / 2;
        newY = centerY = (dm.heightPixels + statusBarHeight) / 2;
    }

    // Set and Get

    public int getHatRadius() {
        return hatRadius;
    }

    public int getBaseRadius() {
        return baseRadius;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    /**
     * @return height of the bar of the application
     */
    private int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0)
            return getResources().getDimensionPixelSize(resourceId);
        return 0;

    }

    public double distance(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * the function drawing the joystick hat on the canvas according to its position
     *
     * @param myCanvas canvas object
     * @param newX     the new X position of the joystick hat
     * @param newY     the new Y position of the joystick hat
     */
    private void drawJoystick(Canvas myCanvas, float newX, float newY) {
        Paint colors = new Paint();
        myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // Clearing the BG

        // draw the joystick base
        colors.setColor(joystickBaseColor);
        myCanvas.drawCircle(centerX, centerY - this.statusBarHeight, baseRadius, colors);

        // draw the joystick hat
        colors.setColor(joystickColor);
        myCanvas.drawCircle(newX, newY - this.statusBarHeight, hatRadius, colors);
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }

    public void setNewPos(double angle) {
        this.newX = centerX + (int) ((baseRadius - hatRadius) * Math.cos(Math.toRadians(angle)));
        this.newY = centerY + (int) ((baseRadius - hatRadius) * Math.sin(Math.toRadians(angle)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawJoystick(canvas, this.newX, this.newY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.newX = this.centerX = w / 2;
        this.newY = this.centerY = (h + this.statusBarHeight) / 2;
        super.onSizeChanged(w, h, oldw, oldh);
    }

}